package cn.yulan.upload.module.result;

import lombok.*;

/**
 * 基础返回结果
 *
 * @author Yulan Zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResult<T> {
    private String message;
    private boolean success;
    private T data;

    public void construct(String message, boolean success, T data) {
        this.message = message;
        this.success = success;
        this.data = data;
    }
}