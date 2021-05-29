package cn.yulan.upload.module.service;

import cn.yulan.upload.module.result.BaseResult;
import cn.yulan.upload.module.result.UploadResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传模块服务层接口
 *
 * @author Yulan Zhou
 */
public interface ImageService {

    void upload(MultipartFile uploadImg, BaseResult<UploadResult> result);

}
