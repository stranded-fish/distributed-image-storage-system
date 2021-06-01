package cn.yulan.upload.module.result;

import lombok.Getter;

import static cn.yulan.upload.module.util.ConstUtil.*;

/**
 * 响应码枚举类
 *
 * @author Yulan Zhou
 */
@Getter
public enum ResultCode {

    SUCCESS(1000, UPLOAD_SUCCESS),

    FILE_NOT_EXIST_ERROR(1001, FILE_NOT_EXIST),

    FORMAT_NOT_SUPPORTED_ERROR(1002, FORMAT_NOT_SUPPORTED),

    EXCEED_MAX_SILE_LIMIT_ERROR(1003, EXCEED_MAX_SILE_LIMIT);

    private int code;
    private String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

}