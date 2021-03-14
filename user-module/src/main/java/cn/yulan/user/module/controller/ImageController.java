package cn.yulan.user.module.controller;

import cn.yulan.user.module.result.BaseResult;
import cn.yulan.user.module.service.ImageService;
import cn.yulan.user.module.util.ValidationUtil;
import com.google.protobuf.ByteString;
import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

import static cn.yulan.user.module.util.ConstUtil.*;


/**
 * 图片相关操作控制层
 *
 * @author Yulan Zhou
 * @date 2021-02-27
 */
@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ImageController {

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
            imageService.set(uploadImg, result);
        }

        return result;
    }


    @RequestMapping(value = GET_IMAGE, method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<?> getImage(@RequestParam("imgName") String imgName) {
        BaseResult<Boolean> result = new BaseResult<>();

        return result;
    }

    @RequestMapping(value = DELETE_IMAGE, method = RequestMethod.GET)
    @ResponseBody
    public BaseResult<Boolean> deleteImage(@RequestParam("imgName") String imgName) {
        BaseResult<Boolean> result = new BaseResult<>();
        String dir = "/root/pic/" + imgName;
        boolean success = (new File(dir)).delete();
        if (success) {
            System.out.println("Successfully deleted empty directory: " + dir);
        } else {
            System.out.println("Failed to delete empty directory: " + dir);
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