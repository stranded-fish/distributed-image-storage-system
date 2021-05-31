package cn.yulan.upload.module.util;

import cn.yulan.upload.module.result.BaseResult;
import cn.yulan.upload.module.result.UploadResult;
import org.apache.tika.Tika;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;

import static cn.yulan.upload.module.result.ResultCode.*;


/**
 * 上传文件效验工具
 *
 * @author Yulan Zhou
 */
@Component
public class ValidationUtil {

    @Value("${multipartFile.maxFileSize}")
    private String maxFileSize;

    private static long MAXFILESIZEINBYTE;

    /**
     * 验证上传文件合法性
     *
     * @param uploadImg 待验证文件
     * @param result 返回结果                
     * @return boolean
     * @author Yulan Zhou
     */
    public static boolean validate(MultipartFile uploadImg, BaseResult<UploadResult> result) {

        UploadResult uploadResult = new UploadResult();

        // 判断上传文件是否为空
        if (uploadImg.isEmpty()) {
            result.construct(FILE_NOT_EXIST_ERROR, null);
            return false;
        }

        // 判断上传文件是否为规定图片格式
        if (!isImage(uploadImg)) {
            result.construct(FORMAT_NOT_SUPPORTED_ERROR, null);
            return false;
        }

        // 判断图片大小是否符合要求
        if (!whetherExceedSizeLimit(uploadImg)) {
            result.construct(EXCEED_MAX_SILE_LIMIT_ERROR, null);
            return false;
        }

        // 上传文件符合要求，可正常保存
        return true;
    }

    /**
     * 判断上传文件是否为图片
     *
     * @param uploadImg 待验证文件
     * @return boolean
     * @author Yulan Zhou
     */
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

    /**
     * 判断图片规格是否符合自定义要求
     *
     * @param uploadImg 待验证文件
     * @return boolean
     * @author Yulan Zhou
     */
    private static boolean whetherExceedSizeLimit(MultipartFile uploadImg) {

        // 检测图片大小是否符合要求
        if (uploadImg.getSize() > MAXFILESIZEINBYTE) {
           return false;
        }

        return true;
    }

    /**
     * 获取配置文件中上传文件的大小限制，并将其统一转化为字节数
     *
     * @author Yulan Zhou
     */
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
