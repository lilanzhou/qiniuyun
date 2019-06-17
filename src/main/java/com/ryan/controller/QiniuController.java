package com.ryan.controller;

import com.ryan.api.QiniuUploadApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created by Administrator on 2019:06:16
 *
 * @Author : Lilanzhou
 * 功能 :
 */
@Controller
public class QiniuController {
    @Autowired
    QiniuUploadApi qiniuUploadApi;

    @RequestMapping("/")
    public String index(){
        return "index";
    }

    @RequestMapping("/fileUpload")
    public String show(@RequestParam(value = "file") MultipartFile file, Model model) {
        if (file.isEmpty()) {
            System.out.println("文件为空");
        }
        String fileName = file.getOriginalFilename();  // 文件名
        String suffixName = fileName.substring(fileName.lastIndexOf("."));  // 后缀名
        String filePath = "D://upload-images//"; // 上传后的路径
        fileName = UUID.randomUUID() + suffixName; // 新文件名
        File dest = new File(filePath + fileName);
        if (!dest.getParentFile().exists()) {
            dest.getParentFile().mkdirs();
        }
        try {
            file.transferTo(dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String filePath1=filePath+fileName;
        qiniuUploadApi.upload(filePath1,fileName);//存到七牛云
        //从七牛云上读取图片
        String filename =qiniuUploadApi.getDomainName()+fileName;
        model.addAttribute("filename", filename);//显示到前端
        return "index";
    }

}
