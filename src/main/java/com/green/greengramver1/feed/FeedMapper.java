package com.green.greengramver1.feed;

import com.green.greengramver1.feed.model.FeedGetReq;
import com.green.greengramver1.feed.model.FeedGetRes;
import com.green.greengramver1.feed.model.FeedPicDto;
import com.green.greengramver1.feed.model.FeedPostReq;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface FeedMapper {
    int insFeed(FeedPostReq p);
    List<FeedGetRes> selFeedList(FeedGetReq p);

    int insFeedPic(FeedPicDto p);
    List<String> selFeedPics(long feedId);//피드 아이디 하나당 가져와야 하는 사진수
    // 이거 xml 문에서 가져와야 하는 아이디
}
