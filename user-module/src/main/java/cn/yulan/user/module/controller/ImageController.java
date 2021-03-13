package cn.yulan.user.module.controller;

import cn.yulan.user.module.result.BaseResult;
import cn.yulan.user.module.service.ImageService;
import cn.yulan.user.module.util.ConstUtil;
import cn.yulan.user.module.util.ValidationUtil;
import com.baidu.brpc.client.BrpcProxy;
import com.baidu.brpc.client.RpcClient;
import com.googlecode.protobuf.format.JsonFormat;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import cn.yulan.storage.module.server.service.ExampleProto;
import cn.yulan.storage.module.server.service.ExampleService;

import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

        // 验证上传文件是否合法
        if (ValidationUtil.validate(uploadImg, result)) {
//            imageService.set(uploadImg, result);
        }

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