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
    생성자는 생성시에 호출이 가능하다. 한번 호출하고 끝
 */
public class MyFileUtils {
    private final String uploadPath;//file.directory값을 여기다가 저장하게끔

    public MyFileUtils(@Value("${file.directory}") String uploadPath) {
        //yaml 방식으로 적음 ${여기에 저장되어 있는 데이터.}
        log.info("생성자: {}", uploadPath);
        this.uploadPath = uploadPath; // 파일위치를 알려줘서 빨간줄 안뜸
    }

    /*
        parh = "ddd/aaa"
        D:/download/greengram_ver1/ddd/aaa
        디렉토리 생성

     */
    public String makeFolders(String path) {
        // feed/pk, 이런 형식으로다가
        File file = new File(uploadPath, path); // 타입은 둘다 스트링
        /*
        객체화를 하고 나서 메소드를 소환하니까 스태틱이 아님. 인스턴스 메소드
        리턴타입은 boolean > > if() 안에서 호출했기 때문
        파라미터는 없다 >> 호출시 인자를 보내지 않음
        메소드명은 exists 이다.
         */

//        file.mkdirs(); 폴더를 생성해줌
        if (!file.exists()){
            //       if (!file.exists()) 거짓일때 실
            file.mkdirs();// 존재한다면
        }
        return file.getAbsolutePath();
    }

    //파일명에서 확장자 추출
    public String getExt(String fileName) {
        int lastIdx = fileName.lastIndexOf(".");//마지막.의 정수값을 보낸다
        return fileName.substring(lastIdx);//특정정수값부터 끝까지 리턴
    }

    //랜덤파일명 생성
    public String makeRandomFileName() {
        return UUID.randomUUID().toString();
    }

    //랜덤파일명 + 확장자 생성
    public String makeRandomFileName(String originalFileName) {

        return makeRandomFileName() + getExt(originalFileName); // 오리지날 파일네임을 보내줌
        // 둘중 하나는 무조건 스트링
        // +가 있으면 리턴이 될 수 있다
        // 오버로딩 되어있어서 별개의 메소드이다
        // UUID 란 랜덤하게 만든 파일에서 그 찐 파일의 확장자를 추출
    }
    public  String makeRandomFileName(MultipartFile file) {
        return makeRandomFileName(file.getOriginalFilename());
        //홧장자를 알고싶어서
        // file.getOriginalFilename()는 리턴메소드
        //보이드 메소드인경우
    }

    //파일을 원하는 경로에 저장
    public void transferTo(MultipartFile mf, String path) throws IOException {
        File file = new File(uploadPath, path);
        //에러를 받을 누군가가 있어야함 try-catch가 있어야함
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
//192.168.0.101
//192.168.0.111
//192.168.0.121