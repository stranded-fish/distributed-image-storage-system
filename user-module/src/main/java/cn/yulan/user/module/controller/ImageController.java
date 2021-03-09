package cn.yulan.user.module.controller;

import cn.yulan.user.module.result.BaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");

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

        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");
        System.out.println("hello world");

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