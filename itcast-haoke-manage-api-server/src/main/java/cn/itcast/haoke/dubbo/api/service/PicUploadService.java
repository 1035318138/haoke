package cn.itcast.haoke.dubbo.api.service;

import cn.itcast.haoke.dubbo.api.config.AliyunConfig;
import cn.itcast.haoke.dubbo.api.vo.PicUploadResult;
import com.aliyun.oss.OSS;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Service
public class PicUploadService {
    private  static final String[] IMAGE_TYPE={".bmp",".jpg",".png",".gif"};

    @Autowired
    private AliyunConfig aliyunConfig;
    @Autowired
    private OSS oss;

    public PicUploadResult imagesUpload(MultipartFile multipartFile){
        boolean b=false;
        for(String i:IMAGE_TYPE){
            if(StringUtils.endsWithIgnoreCase(multipartFile.getOriginalFilename(),i)){
                b=true;
                break;
            }
        }
        PicUploadResult result = new PicUploadResult();
        if(!b){
            result.setStatus("error");
            return result;
        }
        String filename = multipartFile.getOriginalFilename();
        String filePath = getFilePath(filename);
        try {
            byte[] fileBytes = multipartFile.getBytes();
            oss.putObject(aliyunConfig.getBucketName(),filePath,new ByteArrayInputStream(fileBytes));
        }catch (Exception e){
            result.setStatus("error");
            return result;
        }
        result.setName(aliyunConfig.getUrlPrefix()+filePath);
        result.setStatus("done");
        result.setUid(String.valueOf(System.currentTimeMillis()));
        return result;


    }


    private String getFilePath(String sourceFileName) {
        DateTime dateTime = new DateTime();
        return "1612b/" + dateTime.toString("yyyy")
                + "/" + dateTime.toString("MM") + "/"
                + dateTime.toString("dd") + "/" + System.currentTimeMillis() +
                RandomUtils.nextInt(100, 9999) + "." +
                StringUtils.substringAfterLast(sourceFileName, ".");
    }
}
