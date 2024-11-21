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
    private List<String> pics;

}
