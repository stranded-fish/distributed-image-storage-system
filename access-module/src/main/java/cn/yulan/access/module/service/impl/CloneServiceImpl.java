package cn.yulan.access.module.service.impl;

import cn.yulan.access.module.service.CloneService;
import cn.yulan.access.module.util.FileUtil;
import cn.yulan.access.module.util.IOtools;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.zip.ZipOutputStream;

/**
 * 下载图片服务层接口实现类
 *
 * @author Yulan Zhou
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CloneServiceImpl implements CloneService {

    @Value("${multipartFile.base-dir}")
    String baseDir;

    @Override
    public void cloneImages(HttpServletResponse httpServletResponse) {
        String zipName = FileUtil.getDateStr() + "-images-backup.zip";
        File fileZip = new File(zipName);
        try {
            ArrayList<File> fileList = new ArrayList<>();
            FileUtil.getFiles(baseDir, fileList);
            FileOutputStream outStream = new FileOutputStream(fileZip);
            ZipOutputStream toClient = new ZipOutputStream(outStream);
            IOtools.zipFile(fileList, toClient);
            toClient.close();
            outStream.close();
            IOtools.downloadFile(fileZip, httpServletResponse, true);
        } catch (Exception e) {
//                log.info("系统异常,请从新录入!");
            e.printStackTrace();
        }

    }
}