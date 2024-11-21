package com.green.greengramver1.user;

import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.user.model.UserSignInReq;
import com.green.greengramver1.user.model.UserSignInRes;
import com.green.greengramver1.user.model.UserSignUpReq;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @Operation(summary = "회원 가입 ")
    public ResultResponse<Integer> signUp(@RequestPart UserSignUpReq p // 데이터 받기 위함
            , @RequestPart(required = false) MultipartFile pic //파일 받기 위함
                                          //List<MultipartFile> 이건 파일 여러개 받는 경우
    ) {
        /*

        log.info("UserInsReq : {} , file ; {} ",p,pic != null? pic.getOriginalFilename() : null) ;
        3항식을 왜 썼을까 ? 레퍼런스 변수이니까 null 아닌 경우에 호출하자. pic이 null 인 경우에는 null들어가고 아니면 말고
        에러 안나게 하려고

         */
        log.info("UserInsReq : {} , file ; {} ", p, pic != null ? pic.getOriginalFilename() : null);
        int result = service.postSignUp(pic, p); //메시지 전달 및 교환
        return ResultResponse.<Integer>builder()
                .resultMessage("회원가입 완")
                .resultData(result)
                .build();
    }

    @PostMapping("sign-in")
    @Operation(summary = "로그인")
    public ResultResponse<UserSignInRes> signIn(@RequestBody UserSignInReq p) {
                   //@RequestBody: 제이슨으로 받아야 하기 때문
        UserSignInRes res = service.postSignIn(p);
        return ResultResponse.<UserSignInRes>builder()
                .resultMessage(res.getMessage())
                .resultData(res)
                .build();
    }
}
