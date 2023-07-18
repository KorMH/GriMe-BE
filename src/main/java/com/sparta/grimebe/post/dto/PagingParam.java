package com.sparta.grimebe.post.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Setter;

@Schema(description = "페이지 네이션 처리를 위한 입력값", nullable = true)
@Setter
public class PagingParam {

    @Schema(description = "정렬 기준", nullable = true, defaultValue = "NEWEST", allowableValues = {"NEWEST","HOT"})
    private String sort = "NEWEST";

    @Schema(description = "페이지 번호", nullable = true, defaultValue = "1", minimum = "0")
    private Integer page = 1;

    @Schema(description = "한 페이지 당 게시글 개수", nullable = true, defaultValue = "10")
    private Integer size = 10;

    public int getPage() {
        if (this.page == 0) {
            return 0;
        }
        return this.page - 1;
    }

    public String getSort() {
        return sort;
    }

    public Integer getSize() {
        return size;
    }
}
