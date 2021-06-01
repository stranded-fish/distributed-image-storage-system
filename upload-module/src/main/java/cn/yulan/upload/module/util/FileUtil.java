package cn.yulan.upload.module.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 文件操作工具
 *
 * @author Yulan Zhou
 */
@Component
@Slf4j
public class FileUtil {

    @Value("${multipartFile.base-dir}")
    private String preSetBaseDir;

    private static String baseDir;

    @PostConstruct
    private void setBaseDir() {
        baseDir = preSetBaseDir;
    }

    public static String getBaseDir() {
        return baseDir;
    }

    /**
     * 利用 MD5 算法获得上传图片唯一标识
     *
     * @param multipartFile 上传图片
     * @return java.lang.String
     * @author Yulan Zhou
     */
    public static String getMD5(MultipartFile multipartFile) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(multipartFile.getBytes());
        } catch (IOException e) {
            log.error("Error calculating the MD5 of image: [{}], exception: {}, message: {}, stackTrace: {}",
                    multipartFile.getName(), e.getCause(), e.getMessage(), e.getStackTrace());
            e.printStackTrace();
        }
        return md5;
    }

    /**
     * 获取当前日期的指定格式字符串
     *
     * @return java.lang.String
     * @author Yulan Zhou
     */
    public static String getDateDir() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(date) + File.separator;
    }

    /**
     * 将图片保存到本地文件系统的指定路径
     *
     * @param uploadImg 待保存图片
     * @param savePath  保存路径
     * @author Yulan Zhou
     */
    public static void save(MultipartFile uploadImg, String savePath) {
        File file = new File(savePath);

        if (!file.getParentFile().exists()) {
            if (!file.getParentFile().mkdirs()) {
                log.error("Error creating directory [{}], check path and permissions", file.getParentFile().getName());
            }
        }

        try {
            uploadImg.transferTo(file);
            log.info("Save image: [{}] to [{}]", uploadImg.getOriginalFilename(), file.getPath());
        } catch (IOException e) {
            log.error("Error saving image: [{}] to local file system: [{}], check path and permissions, exception: {}, message: {}, stackTrace: {}",
                    uploadImg.getOriginalFilename(), savePath, e.getCause(), e.getMessage(), e.getStackTrace());
        }
    }
}