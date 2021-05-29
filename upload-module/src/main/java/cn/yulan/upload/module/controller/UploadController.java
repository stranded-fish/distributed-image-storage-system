package cn.yulan.upload.module.controller;

import cn.yulan.upload.module.result.BaseResult;
import cn.yulan.upload.module.result.UploadResult;
import cn.yulan.upload.module.service.ImageService;
import cn.yulan.upload.module.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/**
 * 图片上传模块控制层
 *
 * @author Yulan Zhou
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadController {

    private final ImageService imageService;

    /**
     * 保存用户上传的图片
     *
     * @param uploadImg 用户上传的图片
     * @return com.yulan.distributedimageserver.result.BaseResult<java.lang.Boolean>
     * @author Yulan Zhou
     */
    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<UploadResult> uploadImage(@RequestParam("uploadImg") MultipartFile uploadImg) {
        BaseResult<UploadResult> result = new BaseResult<>();

        // 验证上传文件是否合法
        if (ValidationUtil.validate(uploadImg, result)) {
            imageService.upload(uploadImg, result);
        }

        return result;
    }
}