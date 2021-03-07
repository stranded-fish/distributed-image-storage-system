package cn.yulan.distributedimageserver.controller;

import cn.yulan.distributedimageserver.result.BaseResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static cn.yulan.distributedimageserver.util.ConstUtil.*;

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

        String path = "C:\\Users\\54738\\Desktop";
        String fileName = uploadImg.getOriginalFilename();
        File dest = new File(new File(path).getAbsolutePath() + "/" + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            System.out.println("bbb");
            uploadImg.transferTo(dest); // 保存文件
            System.out.println("aaa");
        } catch (Exception e) {
            e.printStackTrace();
        }

//        editHomePageService.saveTotalLayoutInfo(totalLayoutInfoBean, result);
        return result;
    }

    @RequestMapping(value = DELETE_IMAGE, method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<Boolean> deleteImage(@RequestParam("imgName") String imgName) {
        BaseResult<Boolean> result = new BaseResult<>();
        String dir = "C:\\Users\\54738\\Desktop\\" + imgName;
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