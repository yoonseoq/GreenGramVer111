package com.green.greengramver1.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class UserSignUpReq {

    @JsonIgnore
    private long userId;
    @Schema(name="유저 아이디",example = "mic", requiredMode = Schema.RequiredMode.REQUIRED)
    private String uid;
    @Schema(name="유저 비번",example = "1234", requiredMode = Schema.RequiredMode.REQUIRED)
    private String upw;
    @JsonIgnore
    private String pic;
    @Schema(description = "유저 닉",example = "ya", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nickName;
}
