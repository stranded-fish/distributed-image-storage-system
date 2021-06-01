package cn.yulan.upload.module.result;

import lombok.*;

/**
 * 上传图片返回结果
 *
 * @author Yulan Zhou
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadResult {
    private String url;

    /**
     * 将访问图片的根地址与相对路径组合构造完整访问链接
     *
     * @author Yulan Zhou
     */
    public void construct(String imageRootPath, String relativePath) {
        this.url = imageRootPath + relativePath;
    }
}