package cn.yulan.access.module.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 下载图片服务层接口
 *
 * @author Yulan Zhou
 */
public interface CloneService {
    void cloneImages(HttpServletResponse httpServletResponse);
}