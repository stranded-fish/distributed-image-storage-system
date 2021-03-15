package cn.yulan.user.module.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.tools.ant.taskdefs.Sleep;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 文件工具类
 *
 * @author Yulan Zhou
 * @date 2021/03/12
 */
@Component
public class FileUtil {

    @Value("${multipartFile.base-dir}")
    private String preSetBaseDir;

    private static String baseDir;


    /**
     * 利用 MD5 算法获得上传图片唯一标识
     *
     * @param multipartFile 上传图片
     * @return java.lang.String
     * @author Yulan Zhou
     * @date 2021/3/15
     */
    public static String getMD5(MultipartFile multipartFile) {
        String md5 = null;
        try {
            md5 = DigestUtils.md5Hex(multipartFile.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return md5;
    }


    public static String getDateDir() {
        // 生成 value - 保存路径
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        return simpleDateFormat.format(date) + File.separator;
    }

    public static String getBaseDir() {
        return baseDir;
    }

    public static void save(MultipartFile uploadImg, String savePath) {
        File file = new File(savePath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            uploadImg.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @PostConstruct
    private void setBaseDir() {
        baseDir = preSetBaseDir;
    }
}