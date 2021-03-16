package cn.yulan.access.module.controller;

import cn.yulan.access.module.result.BaseResult;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import static cn.yulan.access.module.util.ConstUtil.*;

/**
 * 图片访问控制层
 *
 * @author Yulan Zhou
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AccessController {

    @RequestMapping(value = CLONE_IMAGE, method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<?> cloneImage() {
        BaseResult<Boolean> result = new BaseResult<>();

        return result;
    }


}