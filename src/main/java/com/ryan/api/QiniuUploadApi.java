package com.ryan.api;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.stereotype.Service;

@Service
public class QiniuUploadApi {


    private String accessKey = "gcZiLjZh8vM2gMxD62PqABqXyud2d77ePG30dqme";      //AccessKey的值
    private String secretKey = "C-QknotS1oGyNOTXpQd-1gtMwBtnQfSjLwgbL4ap";      //SecretKey的值
    private String bucket = "test";                                          //存储空间名
    private String domainName = "http://pt845ygpx.bkt.clouddn.com/";//存储测试域名

    /**
     * @param FilePath 图片上传的路径
     * @param fileName 在七牛云中图片的命名
     */
    public void upload(String FilePath, String fileName) {
        Configuration cfg = new Configuration(Zone.zone0());                //zong0() 代表华东地区
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            Response response = uploadManager.put(FilePath, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //    System.out.println(putRet.key);
            //  System.out.println(putRet.hash);
        } catch (QiniuException ex) {
            Response r = ex.response;
            //  System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
    }


    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public String getBucket() {
        return bucket;
    }

    public void setBucket(String bucket) {
        this.bucket = bucket;
    }

    public String getDomainName() {
        return domainName;
    }

    public void setDomainName(String domainName) {
        this.domainName = domainName;
    }
}
