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

import java.io.File;

/**
 * 图片相关操作服务层实现类
 *
 * @author Yulan Zhou
 * @date 2021/03/12
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService {

    private final ImageKVDAO imageKVDAO;

    @Value("${raft.ipPorts}")
    private String ipPorts;

    @Override
    public void upload(MultipartFile uploadImg, BaseResult<String> result) {

        // 生成图片唯一标识 key
        String key = FileUtil.getMD5(uploadImg) + "." + FilenameUtils.getExtension(uploadImg.getOriginalFilename());

        String accessURL = null;

        // 判断图片是否重复
        if (imageKVDAO.keyMayExist(key.getBytes())) {
            byte[] bytes = imageKVDAO.get(key.getBytes());
            if (bytes != null) {
                accessURL = new String(bytes);
                System.out.println("repeat");
                System.out.println("accessURL: " + accessURL);
                return;
            } else {
                System.out.println("中招了！！");
                save(key, uploadImg);
            }
        } else {
            save(key, uploadImg);
        }

        // 返回图片地址
        result.construct(null, false, null);
    }

    private void save(String key, MultipartFile uploadImg) {

        // 保存 图片文件 至 本地文件系统
        String relativePath = FileUtil.getDateDir() + key;
        System.out.println("relativePath: " + relativePath);
        String savePath = FileUtil.getBaseDir() + relativePath;
        System.out.println("savePath: " + savePath);

        FileUtil.save(uploadImg, savePath);

        // 保存 图片 key - value 到 本地数据库
        imageKVDAO.put(key.getBytes(), relativePath.getBytes());

        // 保存 图片 key - value 到 Raft 集群

        // 初始化 rpc client
//        RpcClient rpcClient = new RpcClient(ipPorts);
//        ExampleService exampleService = BrpcProxy.getProxy(rpcClient, ExampleService.class);
//        final JsonFormat jsonFormat = new JsonFormat();
//
//        // set
//        ExampleProto.SetRequest setRequest = ExampleProto.SetRequest.newBuilder()
//                .setKey(key).setValue(relativePath).build();
//        ExampleProto.SetResponse setResponse = exampleService.set(setRequest);
//        System.out.printf("set request, key=%s value=%s response=%s\n",
//                key, relativePath, jsonFormat.printToString(setResponse));
    }

}