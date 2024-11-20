package com.green.greengramver1.user;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.user.model.UserInsReq;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserMapper mapper;
    private final MyFileUtils myFileUtils;
    public int insUser(UserInsReq p) {
        return mapper.insUser(p);
    }
    public int postSignUp(MultipartFile pic, UserInsReq p){
        //프로필 이미지 파일 처리
        String savedPicName = myFileUtils.makeRandomFileName(pic);

        int userId = 1; // user를 insert 후에 얻을 수 있다
        //user/${userId}/${savedName}

        String path = myFileUtils.makeFolders(String.format("user/%d", userId));
        log.info("path: {}", path);

        return 1;//mapper.insUser(p);
    }
}
