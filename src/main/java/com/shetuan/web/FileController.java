package com.shetuan.web;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * use for :文件处理
 *
 * @author zoukh
 * Created in:  2020/4/11 23:53
 * @version 1.0
 * @Modified By:
 * @used in: community-management-system
 */

@Controller
@RequestMapping("/file")
public class FileController extends BaseController {
    @Value("${file.upload.path}")
    private String fileUploadPath;
    @RequestMapping("/upload")
    public @ResponseBody
    String fileUpload(@RequestParam("file") MultipartFile file, ModelMap modelMap, HttpServletRequest request){
        if(file.isEmpty()){
            System.out.println("文件名为空");
        }
        String fileName=file.getOriginalFilename();
        String suffixName= fileName.substring(fileName.indexOf("."));
        String newFileName= UUID.randomUUID()+suffixName;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String format = sdf.format(new Date());

        File dest= new File(fileUploadPath+format);
        if(!dest.isDirectory()){
            dest.mkdirs();
        }

        try {
            File newFile = new File(dest.getAbsolutePath()+File.separator+newFileName);
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回文件路径和文件名
        return "/upload/"+format+File.separator+newFileName;
    }



    /**
     * use for : 批量上传,暂无应用,未实现
     *@author zoukh
     *@Created in:  2020/4/12 1:30
     *@Modified By:
     *@version 1.0
     *@used in: FileController
     */
    @RequestMapping("/batch")
    public @ResponseBody
    String fileUploadBatch(@RequestParam("file") MultipartFile file, ModelMap modelMap, HttpServletRequest request){
        if(file.isEmpty()){
            System.out.println("文件名为空");
        }
        String fileName=file.getOriginalFilename();
        String suffixName= fileName.substring(fileName.indexOf("."));
        String newFileName= UUID.randomUUID()+suffixName;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
        String format = sdf.format(new Date());

        File dest= new File(fileUploadPath+format);
        if(!dest.isDirectory()){
            dest.mkdirs();
        }

        try {
            File newFile = new File(dest.getAbsolutePath()+File.separator+newFileName);
            file.transferTo(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回文件路径和文件名
        return "/upload/"+format+File.separator+newFileName;
    }

    /**
     * use for : 未实现
     *@author zoukh
     *@Created in:  2020/4/12 1:33
     *@Modified By:
     *@version 1.0
     *@used in: FileController
     */
    @RequestMapping("/download")
    public @ResponseBody
    String fileDownload( ModelMap modelMap, HttpServletRequest request) {
        return "";
    }

}