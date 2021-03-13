package cn.yulan.user.module.service.impl;

import cn.yulan.user.module.result.BaseResult;
import cn.yulan.user.module.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 图片相关操作服务层实现类
 *
 * @author Yulan Zhou
 * @date 2021/03/12
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService {

    @Override
    public void set(MultipartFile uploadImg, BaseResult<String> result) {

        // 利用 MD5 算法计算上传图片唯一标识

        // 判断图片是否重复

        // 若重复则返回已保存 value

        // 保存图片

        String path = "E:/images/";

        // eg 1 直接用文件名
        // String fileName = uploadImg.getOriginalFilename();  //获取文件名

        // eg 2 用 md5 算法计算得出文件名
        String myHash = null;
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(uploadImg.getBytes());
            byte[] digest = messageDigest.digest();
            myHash = DatatypeConverter.printHexBinary(digest).toUpperCase();
            System.out.println("myHash:" + myHash);
        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }


        String fileName = myHash + "." + FilenameUtils.getExtension(uploadImg.getOriginalFilename());

        String savePath = path + fileName;  //图片保存路径

        File saveFile = new File(savePath);
        if (!saveFile.exists()) {
            saveFile.mkdirs();
        }
        try {
            uploadImg.transferTo(saveFile);  //将临时存储的文件移动到真实存储路径下
        } catch (IOException e) {
            e.printStackTrace();
        }

        //返回图片访问地址


//        String ipPorts = "list://127.0.0.1:8051,127.0.0.1:8052,127.0.0.1:8053";
//        String key = fileName;
//        String value = path + "/" + fileName;
//
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