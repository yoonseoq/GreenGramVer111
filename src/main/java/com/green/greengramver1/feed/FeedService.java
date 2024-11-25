package com.green.greengramver1.feed;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.feed.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FeedService {
    private final FeedMapper mapper;
    private final MyFileUtils myFileUtils;

    public FeedPostRes postFeed(List<MultipartFile> pics, FeedPostReq p) {
         int result = mapper.insFeed(p);


        //파일저장
        //middlePath: feed/${feedId}

        long feedId = p.getFeedId();
        String middlePath = String.format("feed/%d", feedId);
        //폴더 만들어보기
        myFileUtils.makeFolders(middlePath); // 폴더만들기
       /*
        while (!pics.isEmpty()) {
           //비지 않았다는 가정하에
            pics.remove(0);
            // 가장 처음 인덱스 번호부터 지워버리면 그 다음 인덱스 번호가 당겨짐. 그럼 그것도 0이기 때문 다 없어질때까지 만복

        }

        */

        FeedPostRes res = new FeedPostRes(); // 일단 보내줄 가뵤(피드에 올릴 값들)
        res.setFeedId(p.getFeedId()); // 아이디 세팅
        res.setPics(new ArrayList<>()); //사진배열 초기화시킴 준영
        //리스트는 인
       // List<String> list = new ArrayList<>();//수영 arrayList를 할 필요가 없는게 사진으,ㄴ 그냥 리스트가 아니라 걍 스트링이기 때문이다
        FeedPicDto feedPicDto = new FeedPicDto();
        feedPicDto.setFeedId(feedId);
        res.setFeedId(feedId);


        for (MultipartFile pic : pics) {
            String savedPicName = myFileUtils.makeRandomFileName(pic); // 사진 한장씩 반복해서 올려야함
            String filePath = String.format("%s/%s", middlePath, savedPicName);
            //feedPicDto.setFeedId(feedId);

            //정상적으로 값이 들어가야 feed 아이디가 들어간다.
            try {
                myFileUtils.transferTo(pic, filePath);
            } catch (IOException e) {
                log.error(e.getMessage());//무슨에런지 로그 찍힘. 수동으로 끄게끔
                e.printStackTrace();// 이렇게 하면 서버 꺼져버림
            }


            feedPicDto.setPic(savedPicName);
         //   list.add(savedPicName);//수영
            res.getPics().add(savedPicName);
            mapper.insFeedPic(feedPicDto);


        }

        return res;
    }


    public List<FeedGetRes> getFeedList(FeedGetReq p){
       List<FeedGetRes> list= mapper.selFeedList(p);
       // 피드 하나당 사진 여러개 들어가야함 리스트
       //사진
        for(FeedGetRes res:list){
            //db에서 각 피드에 맞는 사진정보를 가져온다.
            List<String> picList = mapper.selFeedPics(res.getFeedId());
            //피드 아이디 하나당 사진 여러장 가져오는 쿼리문 소환
            res.setPics(picList);
        }
        return list;
    }




}
