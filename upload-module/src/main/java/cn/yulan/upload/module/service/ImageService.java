package cn.yulan.upload.module.service;

import cn.yulan.upload.module.result.BaseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片上传模块服务层接口
 *
 * @author Yulan Zhou
 */
public interface ImageService {

    void upload(MultipartFile uploadImg, BaseResult<String> result);

}
