package cn.yulan.user.module.controller;

import cn.yulan.user.module.result.BaseResult;
import cn.yulan.user.module.service.ImageService;
import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.yulan.storage.module.server.service.ExampleProto;
import cn.yulan.storage.module.server.service.ExampleService;

import java.io.File;
import java.io.IOException;

import static cn.yulan.user.module.util.ConstUtil.*;


/**
 * 图片相关操作控制层
 *
 * @author Yulan Zhou
 * @date 2021-02-27
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

    private final ImageService imageService;


    /**
     * 保存用户上传的图片
     *
     * @param uploadImg 用户上传的图片
     * @return com.yulan.distributedimageserver.result.BaseResult<java.lang.Boolean>
     * @author Yulan Zhou
     * @date 2021/2/27
     */
    @RequestMapping(value = UPLOAD_IMAGE, method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> uploadImage(@RequestParam("uploadImg") MultipartFile uploadImg) {
        BaseResult<String> result = new BaseResult<>();

        String path = "E:/images/";

        //获取项目classes/static的地址
        String fileName = uploadImg.getOriginalFilename();  //获取文件名

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

        return result;
    }


    @RequestMapping(value = GET_IMAGE, method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<?> getImage(@RequestParam("imgName") String imgName) {
        BaseResult<Boolean> result = new BaseResult<>();

        return result;
    }

    @RequestMapping(value = DELETE_IMAGE, method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<Boolean> deleteImage(@RequestParam("imgName") String imgName) {
        BaseResult<Boolean> result = new BaseResult<>();
        String dir = "/root/pic/" + imgName;
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
        }
        return result;
    }


}