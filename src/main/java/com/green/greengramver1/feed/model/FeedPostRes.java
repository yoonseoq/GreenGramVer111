package com.green.greengramver1.feed.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigInteger;
import java.util.List;

@Getter
@Setter
public class FeedPostRes {
    private long feedId;
    //bigint 가 여기선 long
    private List<String> pics;//선언만되어ㅗ있음 초기화안되어잇음 이거를 서비스클라스에서 초기화햊둠

}
