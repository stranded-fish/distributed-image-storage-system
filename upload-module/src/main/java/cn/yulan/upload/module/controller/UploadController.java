package cn.yulan.upload.module.controller;

import cn.yulan.upload.module.result.BaseResult;
import cn.yulan.upload.module.service.ImageService;
import cn.yulan.upload.module.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import static cn.yulan.upload.module.util.ConstUtil.*;


/**
 * 图片相关操作控制层
 *
 * @author Yulan Zhou
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UploadController {

    private final ImageService imageService;


    /**
     * 保存用户上传的图片
     *
     * @param uploadImg 用户上传的图片
     * @return com.yulan.distributedimageserver.result.BaseResult<java.lang.Boolean>
     * @author Yulan Zhou
     * @date 2021/2/27
     */
    @RequestMapping(value = UPLOAD_IMAGE, method = RequestMethod.POST)
    @ResponseBody
    public BaseResult<String> uploadImage(@RequestParam("uploadImg") MultipartFile uploadImg) {
        BaseResult<String> result = new BaseResult<>();

        // 验证上传文件是否合法
        if (ValidationUtil.validate(uploadImg, result)) {
            imageService.upload(uploadImg, result);
        }

        return result;
    }

    @RequestMapping(value = "/test/{key}")
    @ResponseBody
    public ResponseEntity<?> test(@PathVariable String key) {
//        ProxyMessage.GetRequest request = ProxyMessage.GetRequest.newBuilder()
//                .setKey(ByteString.copyFrom(key.getBytes())).build();
//        ProxyMessage.GetResponse response = proxyAPI.get(request);
//        if (response == null
//                || response.getBaseRes().getResCode()
//                != CommonMessage.ResCode.RES_CODE_SUCCESS) {
//            LOG.warn("request proxy failed, fileName={}", key);
//            return new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
//        }
//        LOG.info("/download success, key={}", key);
//        String fileExt = ExampleUtils.getFileExtension(key);
//        String contentType = ExampleUtils.convertExtToContentType(fileExt);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.valueOf(contentType));


//        String fileExt = FilenameUtils.getExtension(uploadImg.getOriginalFilename());
//        String contentType = ExampleUtils.convertExtToContentType(fileExt);
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.valueOf(contentType));
        System.out.println("aaa");
        System.out.println(key);
        System.out.println("bbb");
        File file = new File("E:\\images\\2021\\03\\14\\4A0E4021F2AC7A03.jpg");

        String fileExt = FilenameUtils.getExtension(key);
        String contentType = "image/" + fileExt;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", contentType);
        ResponseEntity<byte[]> responseEntity = new ResponseEntity<>(new byte[1], headers, HttpStatus.CREATED);
        return responseEntity;


    }




}