package cn.yulan.user.module.service;


import cn.yulan.user.module.result.BaseResult;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片相关操作服务层接口
 *
 * @author Yulan Zhou
 * @date 2021-03-02
 */
public interface ImageService {

    void set(MultipartFile uploadImg, BaseResult<String> result);


}
