import org.junit.Test;
import org.slf4j.Logger;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * test
 *
 * @author Yulan Zhou
 */
public class ImageUtil {
    private static final String uploadServerAddress = "http://yulan.space:8080/";
    private static final String baseDir = "/root/pic/";

    public void saveImage(String relativePath, String method) {
//        LOG.warn("aaaaaaaaaaaaaaaaaaaaaa");

        String imageURL = uploadServerAddress + "images" + "/" + relativePath;
        String savePath = baseDir + relativePath;

        File file = new File(savePath);

//        LOG.warn("bbbbbbbbbbbbbbbbbbbbbbbbbbb");

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

//        LOG.warn("cccccccccccccccccccccccccccc");

        // 获取上传服务器的图片文件，并保存到本地文件系统
        try {
            // 建立链接
            URL httpUrl = new URL(imageURL);
            HttpURLConnection conn = (HttpURLConnection) httpUrl.openConnection();
//            LOG.warn("ddddddddddddddddddddddddddddd");

            // 发送 Get 请求
            conn.setRequestMethod(method);

//            LOG.warn("eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee");

            // 连接指定的资源
            conn.connect();

//            LOG.warn("fffffffffffffffffffffffffffffffffffff");

            // 获取网络输入流
            InputStream inputStream = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(inputStream);

//            LOG.warn("gggggggggggggggggggggggggggggggggggggggg");

            // 写入到文件（注意文件保存路径的后面一定要加上文件的名称）
            FileOutputStream fileOut = new FileOutputStream(savePath);
            BufferedOutputStream bos = new BufferedOutputStream(fileOut);

//            LOG.warn("hhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh");

            byte[] buf = new byte[4096];
            int length = bis.read(buf);

            // 保存文件
            while (length != -1) {
                bos.write(buf, 0, length);
                length = bis.read(buf);
            }

//            LOG.warn("iiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
            bos.close();
            bis.close();
            conn.disconnect();

//            LOG.warn("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj");
        } catch (Exception e) {
//            LOG.warn(e.getMessage());

        }

//        LOG.warn("kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk");
    }

    @Test
    public void test() {
        new ImageUtil().saveImage("2021/05/30/bff588aaa13b58e56af61a36fd7b27f2.png", "GET");
    }
}