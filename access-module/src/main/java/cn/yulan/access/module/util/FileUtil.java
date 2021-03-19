package cn.yulan.access.module.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * 文件操作工具类
 *
 * @author Yulan Zhou
 */
public class FileUtil {

    public static void getFiles(String path, ArrayList<File> fileList) throws Exception {
        //目标集合fileList
        File file = new File(path);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File fileIndex : files) {
                //如果这个文件是目录，则进行递归搜索
                if (fileIndex.isDirectory()) {
                    getFiles(fileIndex.getPath(), fileList);
                } else {
                    //如果文件是普通文件，则将文件句柄放入集合中
                    fileList.add(fileIndex);
                }
            }
        }
    }

    /**
     * 获取当前日期的指定格式字符串
     *
     * @return java.lang.String
     * @author Yulan Zhou
     */
    public static String getDateStr() {
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);
    }
}