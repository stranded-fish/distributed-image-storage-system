package cn.yulan.upload.module.util;

import cn.yulan.upload.module.result.BaseResult;
import org.apache.tika.Tika;
import org.springframework.beans.ConversionNotSupportedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;


/**
 * 上传文件效验工具
 *
 * @author Yulan Zhou
 * @date 2021/03/13
 */
@Component
public class ValidationUtil {

    @Value("${multipartFile.maxFileSize}")
    private String maxFileSize;

    private static long MAXFILESIZEINBYTE;


    public static boolean validate(MultipartFile uploadImg, BaseResult<?> result) {

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
        if (!whetherMeetsRequirements(uploadImg)) {
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
            if (!mimeType.startsWith("image")) return false;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }

    private static boolean whetherMeetsRequirements(MultipartFile uploadImg) {

        // 检测图片大小是否符合要求
        if (uploadImg.getSize() > MAXFILESIZEINBYTE) {
           return false;
        }

        return true;
    }

    @PostConstruct
    private void getMaxFileSizeInByte() {
        try {
        String unit = maxFileSize.substring(maxFileSize.length() - 2).toUpperCase();
        long size = Integer.parseInt(maxFileSize.substring(0, maxFileSize.length() - 2).trim());
            switch (unit) {
                case "MB":
                    MAXFILESIZEINBYTE = size * 1024 * 1024;
                    break;
                case "KB":
                    MAXFILESIZEINBYTE = size * 1024;
                    break;
                case "B":
                    MAXFILESIZEINBYTE = size;
                    break;
                default:
                    throw new Exception();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
