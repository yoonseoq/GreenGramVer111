package com.green.greengramver1.user.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(title="로그인")
public class UserSignInReq { // 받는데이터
    @Schema(title="아이디",example = "id",requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(title="비밀번호",example = "1234",requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
}
