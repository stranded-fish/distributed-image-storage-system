package cn.yulan.user.module.service.impl;

import cn.yulan.user.module.dao.ImageKVDAO;
import cn.yulan.user.module.result.BaseResult;
import cn.yulan.user.module.service.ImageService;
import cn.yulan.user.module.util.FileUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
        result.construct(null,false, null);
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
        // TODO

//            String ipPorts = "list://127.0.0.1:8051,127.0.0.1:8052,127.0.0.1:8053";
//            String key = fileName;
//            String value = path + "/" + fileName;

//        // init rpc client
//        RpcClient rpcClient = new RpcClient(ipPorts);
//        ExampleService exampleService = BrpcProxy.getProxy(rpcClient, ExampleService.class);
//        final JsonFormat jsonFormat = new JsonFormat();
//
//        // set
//        if (value != null) {
//            ExampleProto.SetRequest setRequest = ExampleProto.SetRequest.newBuilder()
//                    .setKey(key).setValue(value).build();
//            ExampleProto.SetResponse setResponse = exampleService.set(setRequest);
//            System.out.printf("set request, key=%s value=%s response=%s\n",
//                    key, value, jsonFormat.printToString(setResponse));
//        } else {
//            // get
//            ExampleProto.GetRequest getRequest = ExampleProto.GetRequest.newBuilder()
//                    .setKey(key).build();
//            ExampleProto.GetResponse getResponse = exampleService.get(getRequest);
//            System.out.printf("get request, key=%s, response=%s\n",
//                    key, jsonFormat.printToString(getResponse));
//        }
    }

}