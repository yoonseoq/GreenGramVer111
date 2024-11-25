package com.green.greengramver1.common.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Paging {
    @JsonIgnore
    private final static int DEFAULT_PAGE_SIZE = 20; // 건들지도 못하게
    @Schema(example = "1", description = "Selected Page")
    private int page;
    @Schema(example = "30", description = "item count per page")
    private int size;
    @JsonIgnore
    private int sIdx;

    public Paging(Integer page, Integer size) {
        /*
        if (size == null || size <= 0) {
            size = 20;
        }
        if (page == null || page <= 0) {
            page = 1;
        }
        }// 점검하는 부분이여서 if 문이 젤 위에 있어야함
        this.page = page;
        이 방법도 있어요

         */
            this.page = (page == null || page <= 0)? 1 : page;
            this.size = (size == null || size <= 0)? DEFAULT_PAGE_SIZE : size; //이런 방법도 있어요
            this.sIdx = (this.page - 1) * this.size;

     /*public Paging(){
         super(); //부모에 있는 기본생성자 호출
     }

      */
        //기본생성자 있나요 있어요. 생성자가 하나라도 없으묜 컴파일러가 자동으로 넣어줘요

    }
}
