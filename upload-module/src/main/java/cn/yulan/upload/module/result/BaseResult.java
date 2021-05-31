package cn.yulan.upload.module.result;

import lombok.*;

import static cn.yulan.upload.module.result.ResultCode.SUCCESS;

/**
 * 基础返回结果
 *
 * @author Yulan Zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult<T> {
    private int status;
    private String message;
    private T data;

    public void construct(ResultCode resultCode, T data) {
        this.status = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.data = data;
    }
}