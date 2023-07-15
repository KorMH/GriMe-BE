package com.sparta.grimebe.post.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class PostRequestDTO {

    private String title;

    private String content;

    private String mood;

    private String weather;

}
