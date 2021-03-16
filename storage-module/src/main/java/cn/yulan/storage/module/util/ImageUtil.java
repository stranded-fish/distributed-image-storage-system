package cn.yulan.storage.module.util;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片操作工具类
 *
 * @author Yulan Zhou
 * @date 2021/03/16
 */
public class ImageUtil {
    private static final String uploadServerAddress = "http://localhost:8080/";
    private static final String baseDir = "/root/pic/";

    public static File saveImage(String relativePath, String method) {

        String imageURL = uploadServerAddress + "images" + "/" + relativePath;
        String savePath = baseDir + relativePath;

        File file = new File(savePath);

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        // 获取上传服务器的图片文件，并保存到本地文件系统
        try {
            // 建立链接
            URL httpUrl = new URL(imageURL);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();

            // 以 Post 方式提交表单，默认get方式
            conn.setRequestMethod(method);

            // 连接指定的资源
            conn.connect();

            // 获取网络输入流
            InputStream inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);

            // 写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            FileOutputStream fileOut = new FileOutputStream(savePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

            byte[] buf = new byte[4096];
            int length = bis.read(buf);

            // 保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }
            bos.close();
            bis.close();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }
}