package cn.yulan.upload.module.service.impl;

import cn.yulan.storage.module.server.service.ExampleProto;
import cn.yulan.storage.module.server.service.ExampleService;
import cn.yulan.upload.module.dao.ImageKVDAO;
import cn.yulan.upload.module.result.BaseResult;
import cn.yulan.upload.module.service.ImageService;
import cn.yulan.upload.module.util.FileUtil;
import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import static cn.yulan.upload.module.util.ConstUtil.UPLOAD_SUCCESS;

/**
 * 图片上传模块服务层实现类
 *
 * @author Yulan Zhou
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService {

    private final ImageKVDAO imageKVDAO;

    @Value("${raft.ipPorts}")
    private String ipPorts;

    private String relativePath;

    /**
     * 上传图片（保存预处理）
     *
     * @author Yulan Zhou
     */
    @Override
    public void upload(MultipartFile uploadImg, BaseResult<String> result) {

        // 生成图片唯一标识 key MD5.jpg/png...
        String key = FileUtil.getMD5(uploadImg) + "." + FilenameUtils.getExtension(uploadImg.getOriginalFilename());

        // 判断图片是否重复
        if (imageKVDAO.keyMayExist(key.getBytes())) {
            byte[] bytes = imageKVDAO.get(key.getBytes());
            if (bytes != null) {
                relativePath = new String(bytes);
            } else {
                save(key, uploadImg);
            }
        } else {
            save(key, uploadImg);
        }

        // 返回图片相对路径
        result.construct(UPLOAD_SUCCESS, true, relativePath);
    }

    /**
     * 保存图片
     *
     * @author Yulan Zhou
     */
    private void save(String key, MultipartFile uploadImg) {

        // Step 1. 保存 图片文件 至 本地文件系统
        relativePath = FileUtil.getDateDir() + key;
        FileUtil.save(uploadImg, FileUtil.getBaseDir() + relativePath);

        // Step 2. 保存 图片 key - value 到 本地数据库
        imageKVDAO.put(key.getBytes(), relativePath.getBytes());

        // Step 3. 保存 图片 key - value 到 Raft 集群
        // 初始化 rpc client
        RpcClient rpcClient = new RpcClient(ipPorts);
        ExampleService exampleService = BrpcProxy.getProxy(rpcClient, ExampleService.class);
        final JsonFormat jsonFormat = new JsonFormat();

        // set key - value
        ExampleProto.SetRequest setRequest = ExampleProto.SetRequest.newBuilder()
                .setKey(key).setValue(relativePath).build();
        ExampleProto.SetResponse setResponse = exampleService.set(setRequest);
        System.out.printf("set request, key=%s value=%s response=%s\n",
                key, relativePath, jsonFormat.printToString(setResponse));

        // 关闭 rpc client
        rpcClient.stop();
    }
}