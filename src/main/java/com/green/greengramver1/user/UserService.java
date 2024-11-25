package com.green.greengramver1.user;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import com.green.greengramver1.user.model.UserSignUpReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;

    public int postSignUp(MultipartFile pic, UserSignUpReq p){
        //프로필 이미지 파일 처리
        String savedPicName = (pic != null ? myFileUtils.makeRandomFileName(pic) : null);
        //랜덤한 파일명에다가 확장자 붙여주는 메소드가 이거에요
        //이 메소드에서 필요한것은 확장자
        // 사진이 null이 아닐 경우에만 들어가게
        // 확장자란 .png .jpg 등등 앞으로 데이터베이스에 저장할 파일명

        String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        // 데이터베이스에 저장 할 떄 보면 $2a$10$ 어쩌구
        // 비밀번호 암호화 비밀번호를 잃구면 아무도 모른다
        // 비밀번호 얻고 salt화 해서 $2a$10$ 이런식으로 파일이 나옴
        log.info("hashedPassword: {}", hashedPassword);
        p.setUpw(hashedPassword);
        p.setPic(savedPicName);
        //UserInsReq p2 = new UserInsReq(p.getUid(), hashedPassword, savedPicName, p.getNickName());

        int result = mapper.insUser(p);
        //메소드 호출하고 영향받은 행값 넣음

        //user/${userId}/${savedName}
        if (pic == null){
            return result;
        }
        // 여기 지나친 순간부터 pic 은 null 이 아님
        // 저장위치 만든다 user/${userId}/${savedPicName}
        // middlePath =user/${userid}
        long userId = p.getUserId(); //호출 후 pk가져옴 user를 insert 후에 얻을 수 있다
        String middlePath=String.format("user/%d", userId);
                              //유저폴더아래에 n 번 폴더 만듦
        myFileUtils.makeFolders(middlePath);
        log.info("middlePath: {}", middlePath);
        String filePath = String.format("%s/%s", middlePath, savedPicName);
        // 미들패스 + 저장할때 쓴 파일명
        try {
            myFileUtils.transferTo(pic, filePath); // 실제호 파일을 전달시킴
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;//mapper.insUser(p); 성공시 1을 리턴
    }

    public UserSignInRes postSignIn(UserSignInReq p){
        UserSignInRes res = mapper.selUserForSignIn(p);
        if (res == null){// 아이디 없음
            res = new UserSignInRes();
            res.setMessage("아이디 확인좀요");
            return res;
        }
        if ( !BCrypt.checkpw(p.getUpw(), res.getUpw())){ // 비밀번호 다를 시
        // 비밀번호 암호화 해야함
            res = new UserSignInRes();
            res.setMessage("비번 확인좀요");
            return res;
        }
        res.setMessage("로그인성공 ㅊㅊ");
        return res;
    }
}
