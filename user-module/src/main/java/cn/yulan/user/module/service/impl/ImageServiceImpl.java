package cn.yulan.user.module.service.impl;

import cn.yulan.user.module.result.BaseResult;
import cn.yulan.user.module.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * 图片相关操作服务层实现类
 *
 * @author Yulan Zhou
 * @date 2021/03/12
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageServiceImpl implements ImageService {

    @Override
    public void set(MultipartFile uploadImg, BaseResult<String> result) {


    }

}