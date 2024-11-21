package com.green.greengramver1.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserSignInRes { //주는데이터
    private long userId;
    private String nickName;
    private String uid;
    @JsonIgnore // 응답할때 비밀번호는 빠지도록
    private String upw;
    @JsonIgnore
    private String message;
    private String pic;
}
