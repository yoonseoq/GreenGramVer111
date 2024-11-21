package com.green.greengramver1.feed;

import com.green.greengramver1.common.MyFileUtils;
import com.green.greengramver1.feed.model.FeedPicDto;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
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
        // int result = mapper.insFeed(p);


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
        res.setPics(new ArrayList<>()); //사진배열 정의
       // ArrayList<String> list = new ArrayList<>();//수영
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
}
