package com.green.greengramver1.feed;

import com.green.greengramver1.common.model.ResultResponse;
import com.green.greengramver1.feed.model.FeedGetReq;
import com.green.greengramver1.feed.model.FeedGetRes;
import com.green.greengramver1.feed.model.FeedPostReq;
import com.green.greengramver1.feed.model.FeedPostRes;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("feed")
@Tag(name="2.피드", description = "피드 관리")
public class FeedController {
    private final FeedService service;


    @PostMapping
    //ResultResponse<FeedPostRes>는 젤 마지막에만 사용
    public ResultResponse<FeedPostRes> postFeed(@RequestPart List<MultipartFile> pics
                                                , @RequestPart FeedPostReq p){
        FeedPostRes res = service.postFeed(pics, p);
        return ResultResponse.<FeedPostRes>builder()
                .resultData(res).build();
    }
        /*
            get방식은 쿼리스트링??
            url 에 key value 값을 포함한다
            주소값애 데이터를 실어서 나타내는것
            http://example.com?key1=value1&key2=value2
            html의 핵심은 클릭하면 들어가지는것
         */
    @GetMapping
    public ResultResponse<List<FeedGetRes>> getFeedList(@ParameterObject // 스웨거 문서 때문에 넣어주는것 뿐
                                                   @ModelAttribute FeedGetReq p){
        log.info("p: {}",p);
        List<FeedGetRes> list = service.getFeedList(p);
        return ResultResponse.<List<FeedGetRes>>builder()
                .resultMessage(String.format("%d rows",list.size()))
                .resultData(list)
                .build();
    }
    /*
    @GetMapping("/param")
    public ResultResponse<FeedGetRes> getFeedList(@RequestParam int page, //뒤에 required 어쩌구 없으면 고정적으로 들어감
                                                  @RequestParam int size){
        //List<FeedGetRes> list= service.selFeedList()
    }


     */
}
