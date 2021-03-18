package cn.yulan.storage.module.server.service.impl;

import cn.yulan.storage.module.util.ImageUtil;
import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.baidu.brpc.client.RpcClientOptions;
import com.baidu.brpc.client.instance.Endpoint;
import com.github.wenweihu86.raft.Peer;
import cn.yulan.storage.module.server.ExampleStateMachine;
import cn.yulan.storage.module.server.service.ExampleProto;
import cn.yulan.storage.module.server.service.ExampleService;
import com.github.wenweihu86.raft.RaftNode;
import com.github.wenweihu86.raft.RaftOptions;
import com.github.wenweihu86.raft.proto.RaftProto;
import com.googlecode.protobuf.format.JsonFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wenweihu86 on 2017/5/9.
 */
public class ExampleServiceImpl implements ExampleService {

    private static final Logger LOG = LoggerFactory.getLogger(ExampleServiceImpl.class);
    private static JsonFormat jsonFormat = new JsonFormat();

    private RaftNode raftNode;
    private ExampleStateMachine stateMachine;
    private int leaderId = -1;
    private RpcClient leaderRpcClient = null;
    private Lock leaderLock = new ReentrantLock();
    private Semaphore semaphore;
    private ExampleService exampleService;

    public ExampleServiceImpl(RaftNode raftNode, ExampleStateMachine stateMachine, RaftOptions raftOptions) {
        this.raftNode = raftNode;
        this.stateMachine = stateMachine;
        this.semaphore = new Semaphore(raftOptions.getConcurrentWindow());
    }

    private void onLeaderChangeEvent() {
        if (raftNode.getLeaderId() != -1
                && raftNode.getLeaderId() != raftNode.getLocalServer().getServerId()
                && leaderId != raftNode.getLeaderId()) {
            leaderLock.lock();
            if (leaderId != -1 && leaderRpcClient != null) {
                leaderRpcClient.stop();
                leaderRpcClient = null;
                leaderId = -1;
            }
            leaderId = raftNode.getLeaderId();
            Peer peer = raftNode.getPeerMap().get(leaderId);
            Endpoint endpoint = new Endpoint(peer.getServer().getEndpoint().getHost(),
                    peer.getServer().getEndpoint().getPort());
            RpcClientOptions rpcClientOptions = new RpcClientOptions();
            rpcClientOptions.setGlobalThreadPoolSharing(true);
            leaderRpcClient = new RpcClient(endpoint, rpcClientOptions);
            leaderLock.unlock();
        }
    }

    @Override
    public ExampleProto.SetResponse set(ExampleProto.SetRequest request) {
        ExampleProto.SetResponse.Builder responseBuilder = ExampleProto.SetResponse.newBuilder();

        try {
            semaphore.acquire();

            // 如果自己不是leader，将写请求转发给leader
            if (raftNode.getLeaderId() <= 0) {
                responseBuilder.setSuccess(false);
            } else if (raftNode.getLeaderId() != raftNode.getLocalServer().getServerId()) {
//                onLeaderChangeEvent();
//                ExampleService exampleService = BrpcProxy.getProxy(leaderRpcClient, ExampleService.class);
//                ExampleProto.SetResponse responseFromLeader = exampleService.set(request);
//                responseBuilder.mergeFrom(responseFromLeader);

                LOG.info("receive set request, I'm not leader, dispatcher this request");
                if (this.exampleService == null) {
                    RpcClient rpcClient = raftNode.getPeerMap().get(raftNode.getLeaderId()).createClient();
                    exampleService = BrpcProxy.getProxy(rpcClient, ExampleService.class);    // 得到对应的代理类
                }
                ExampleProto.SetResponse responseFromLeader = exampleService.set(request);  // 向 Leader 节点发起请求，得到请求结果
                responseBuilder.mergeFrom(responseFromLeader);  // 将两个结果合并
            } else {
                // 数据同步写入raft集群
                byte[] data = request.toByteArray();
                boolean success = raftNode.replicate(data, RaftProto.EntryType.ENTRY_TYPE_DATA);
                responseBuilder.setSuccess(success);
            }

            ExampleProto.SetResponse response = responseBuilder.build();
            LOG.info("set request, request={}, response={}", jsonFormat.printToString(request),
                    jsonFormat.printToString(response));
            return response;


        } catch (InterruptedException e) {
            responseBuilder.setSuccess(false);
            return responseBuilder.build();
        } finally {
            semaphore.release();
        }

    }

    @Override
    public ExampleProto.GetResponse get(ExampleProto.GetRequest request) {
        ExampleProto.GetResponse response = stateMachine.get(request);
        LOG.info("get request, request={}, response={}", jsonFormat.printToString(request),
                jsonFormat.printToString(response));
        return response;
    }

}
