package com.green.greengramver1.user;

import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.user.model.UserInsReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("user")
@Tag(name = "유저", description = "회원가입, 로그인")
public class UserController {
    private final UserService service;

    /*
        파일 + data
        파일 업로드 시에는 RequestBody 사용 불가
        RequestPart 애노테이션 사용해야함
     */
    @PostMapping("sign-up")
    @Operation(summary = "회원 가입")
    public ResultResponse<Integer> signUp(@RequestPart UserInsReq p // 데이터 받기 위함
                                        ,@RequestPart MultipartFile pic //파일 받기 위함
                                          //List<MultipartFile> 이건 파일 여러개 받는 경우
    ){
        log.info("UserInsReq : {} , file ; {} ",p,pic.getOriginalFilename());
        int result = service.postSignUp(pic,p);
        return ResultResponse.<Integer>builder()
                .resultMassage("회원가입완")
                .resultData(result)
                .build();
    }
}
