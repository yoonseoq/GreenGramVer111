package com.green.greengramver1.user;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.user.model.UserInsReq;
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

    public int postSignUp(MultipartFile pic, UserInsReq p){
        //프로필 이미지 파일 처리
        String savedPicName = pic != null ? myFileUtils.makeRandomFileName(pic) : null;

        String hashedPassword = BCrypt.hashpw(p.getUpw(), BCrypt.gensalt());
        log.info("hashedPassword: {}", hashedPassword);
        p.setUpw(hashedPassword);
        p.setPic(savedPicName);
        //UserInsReq p2 = new UserInsReq(p.getUid(), hashedPassword, savedPicName, p.getNickName());

        int result = mapper.insUser(p);

        //user/${userId}/${savedName}
        if (pic == null){
            return result;
        }

        long userId = p.getUserId(); // user를 insert 후에 얻을 수 있다
        String middlePath=String.format("user/%d", userId);
        myFileUtils.makeFolders(middlePath);
        log.info("middlePath: {}", middlePath);
        String filePath = String.format("%s/%s", middlePath, savedPicName);

        try {
            myFileUtils.transferTo(pic, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;//mapper.insUser(p);
    }
}
