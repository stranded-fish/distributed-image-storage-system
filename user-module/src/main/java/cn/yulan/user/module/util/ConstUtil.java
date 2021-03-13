package cn.yulan.user.module.util;

import java.util.HashSet;
import java.util.Set;

/**
 * 常量字典
 *
 * @author Yulan Zhou
 * @date 2021/02/27
 */
public class ConstUtil {
    // 图片相关操作接口映射
    public static final String UPLOAD_IMAGE = "/upload";
    public static final String DELETE_IMAGE = "/delete";
    public static final String GET_IMAGE = "/get";

    // 上传文件验证结果
    public static final String FILE_NOT_EXIST = "Upload file does not exist.";
    public static final String FORMAT_NOT_SUPPORTED = "Upload file format is not supported.";
    public static final String NOT_MEET_REQUIREMENTS = "Upload file does not meet the requirements.";

}