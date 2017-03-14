package com.co2lm.botrest.aws;

import org.springframework.stereotype.Component;

/**
 * @author Myouhgwhan Lee on 2017. 3. 14..
 */
@Component
public interface AwsService {

    /**
     * 사용자가 카카오톡으로 올린 이미지의 경로를 S3로 업로드 하게 만들어 S3의 경로를 반환
     * @param picUrl(카카오톡으로 올린 사용자의 사진 경로)
     * @return rtnS3Url(사용자의 사진의 S3 경로
     */
    public String uploadUserPicToS3(String picUrl);


}
