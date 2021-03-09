package cn.yulan.user.module.controller;

import cn.yulan.user.module.result.BaseResult;
import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.googlecode.protobuf.format.JsonFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.yulan.storage.module.server.service.ExampleProto;
import cn.yulan.storage.module.server.service.ExampleService;

import java.io.File;

import static cn.yulan.user.module.util.ConstUtil.*;


/**
 * 图片相关操作控制层
 *
 * @author Yulan Zhou
 * @date 2021-02-27
 */
@Controller
public class ImageController {

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
    public BaseResult<Boolean> uploadImage(@RequestParam("uploadImg") MultipartFile uploadImg) {
        BaseResult<Boolean> result = new BaseResult<>();

        System.out.println("start save image in local file system");
        System.out.println("...");
        System.out.println("...");

        String path = "/root/pic";
        String fileName = uploadImg.getOriginalFilename();
        File dest = new File(new File(path).getAbsolutePath() + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            uploadImg.transferTo(dest); // 保存文件
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("end save image");
        System.out.println("...");
        System.out.println("...");

        System.out.println();

        System.out.println("start save path in raft clusters");
        System.out.println("...");
        System.out.println("...");

        String ipPorts = "list://127.0.0.1:8051,127.0.0.1:8052,127.0.0.1:8053";
        String key = fileName;
        String value = path + "/" + fileName;

        // init rpc client
        RpcClient rpcClient = new RpcClient(ipPorts);
        ExampleService exampleService = BrpcProxy.getProxy(rpcClient, ExampleService.class);
        final JsonFormat jsonFormat = new JsonFormat();

        // set
        if (value != null) {
            ExampleProto.SetRequest setRequest = ExampleProto.SetRequest.newBuilder()
                    .setKey(key).setValue(value).build();
            ExampleProto.SetResponse setResponse = exampleService.set(setRequest);
            System.out.printf("set request, key=%s value=%s response=%s\n",
                    key, value, jsonFormat.printToString(setResponse));
        } else {
            // get
            ExampleProto.GetRequest getRequest = ExampleProto.GetRequest.newBuilder()
                    .setKey(key).build();
            ExampleProto.GetResponse getResponse = exampleService.get(getRequest);
            System.out.printf("get request, key=%s, response=%s\n",
                    key, jsonFormat.printToString(getResponse));
        }


        System.out.println("end save path");
        System.out.println("...");
        System.out.println("...");


//        editHomePageService.saveTotalLayoutInfo(totalLayoutInfoBean, result);
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

    @RequestMapping(value = GET_IMAGE, method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<?> getImage() {
        BaseResult<Boolean> result = new BaseResult<>();

        return result;
    }

}