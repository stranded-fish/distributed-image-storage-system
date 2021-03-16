package cn.yulan.upload.module.service;


import cn.yulan.upload.module.result.BaseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片相关操作服务层接口
 *
 * @author Yulan Zhou
 * @date 2021-03-02
 */
public interface ImageService {

    void upload(MultipartFile uploadImg, BaseResult<String> result);


}
