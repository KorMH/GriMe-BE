package com.sparta.grimebe.post.dto;

import lombok.Getter;

@Getter
public class PagingDTO<T> {

    private boolean hasNextPage;

    private T data;

    public PagingDTO(boolean hasNextPage, T data) {
        this.hasNextPage = hasNextPage;
        this.data = data;
    }
}
