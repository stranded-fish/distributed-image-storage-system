package cn.yulan.user.module.util;

import cn.yulan.user.module.result.BaseResult;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * 上传文件效验工具
 *
 * @author Yulan Zhou
 * @date 2021/03/13
 */
public class ValidationUtil {

    private static PropertiesExtractionUtil propertiesExtractionUtil = new PropertiesExtractionUtil();

    public static boolean validate(MultipartFile uploadImg, BaseResult<String> result) {

        // 判断上传文件是否为空
        if (uploadImg.isEmpty()) {
            result.construct(ConstUtil.FILE_NOT_EXIST, false, null);
            return false;
        }

        // 判断上传文件是否为规定图片格式
        if (!isImage(uploadImg)) {
            result.construct(ConstUtil.FORMAT_NOT_SUPPORTED, false, null);
            return false;
        }

        // 判断图片规格是否符合要求
        if (!whetherMeetsRequirement(uploadImg)) {
            result.construct(ConstUtil.NOT_MEET_REQUIREMENTS, false, null);
            return false;
        }

        // 上传文件符合要求，可正常保存
        return true;
    }

    private static boolean isImage(MultipartFile uploadImg) {
        Tika tika = new Tika();

        try {
            // 检测文件的 MIME 类型
            String mimeType = tika.detect(uploadImg.getInputStream());
            if (mimeType.startsWith("image")) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    private static boolean whetherMeetsRequirement(MultipartFile uploadImg) {
        System.out.println(propertiesExtractionUtil.getProperty("maxFileSize"));

        System.out.println(uploadImg.getSize());
        System.out.println("bbbb");

        // TODO
        return false;
    }


}