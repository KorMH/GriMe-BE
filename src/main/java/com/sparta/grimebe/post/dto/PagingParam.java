package com.sparta.grimebe.post.dto;

import lombok.Setter;

@Setter
public class PagingParam {

    private String sort = "NEWEST";

    private Integer page = 1;

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
