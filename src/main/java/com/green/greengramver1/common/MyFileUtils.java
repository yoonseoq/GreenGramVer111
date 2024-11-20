package com.green.greengramver1.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Component
/*
    빈등록이란?
    스프링 컨테이너에게 파일관리를 해달라고 하는 것이다
    스프링은
    "${file.directory}"은 yaml파일에 있는 file.directory속성에 저장된 값을 생성자 호출할 때 값을 넣어준다

 */
public class MyFileUtils {
    private final String uploadPath;

    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        log.info("생성자: {}", uploadPath);
        this.uploadPath = uploadPath; // 파일위치를 알려줘서 빨간줄 안뜸
    }

    /*
        parh = "ddd/aaa"
        D:/download/greengram_ver1/ddd/aaa
        디렉토리 생성

     */
    public String makeFolders(String path) {
        File file = new File(uploadPath, path);
        file.mkdirs();
        return file.getAbsolutePath();
    }

    //파일명에서 확장자 추출
    public String getExt(String fileName) {
        int lastIdx = fileName.lastIndexOf(".");
        return fileName.substring(lastIdx);
    }

    //랜덤파일명 생성
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }

    //랜덤파일명 + 확장자 생성
    public String makeRandomFileName(String originalFileName) {

        return makeRandomFileName() ;//+ getExt(originalFileName);

        // 오버로딩 되어있어서 별개의 메소드이다
        // UUID 란 랜덤하게 만든 파일에서 그 찐 파일의 확장자를 추출
    }
    public  String makeRandomFileName(MultipartFile file) {
        return makeRandomFileName(file.getOriginalFilename());
    }

    //파일을 원하는 경로에 저장
    public void transferTo(MultipartFile mf, String path) throws IOException {
        File file = new File(uploadPath, path);

        mf.transferTo(file);
    }






    class Test {
        public static void main(String[] args) {
            MyFileUtils myFileUtils = new MyFileUtils("c;/temp");
            String randomFileName = myFileUtils.makeRandomFileName("5971829047_d29227ce0c_b.png");
            System.out.println(randomFileName); // 오리지날 이름에서 그냥 확장자만 더해줌
        }

    }
}