package cn.yulan.access.module.controller;

import cn.yulan.access.module.service.CloneService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;

/**
 * 图片访问控制层
 *
 * @author Yulan Zhou
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessController {

    private final CloneService cloneService;

    @RequestMapping(value = "/clone", method = RequestMethod.GET)
    public void cloneImages(HttpServletResponse httpServletResponse) {
        cloneService.cloneImages(httpServletResponse);
    }

}