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
import lombok.extern.slf4j.Slf4j;


/**
 * 图片上传模块控制层
 *
 * @author Yulan Zhou
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
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

        log.info("Image: [{}] upload request received", uploadImg.getOriginalFilename());

        BaseResult<UploadResult> result = new BaseResult<>();

        // 验证上传文件是否合法
        if (ValidationUtil.validate(uploadImg, result)) {
            log.info("Image: [{}] passed the validation, start uploading image......", uploadImg.getOriginalFilename());
            imageService.upload(uploadImg, result);
        }

        log.info("Image: [{}] upload request completed", uploadImg.getOriginalFilename());

        return result;
    }
}