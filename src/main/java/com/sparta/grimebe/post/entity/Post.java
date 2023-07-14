package com.sparta.grimebe.post.entity;

import com.sparta.grimebe.global.BaseTimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String content;

    private String image;

    private String mood;

    private String weather;

    // private User user;

    // private List<Comment> commentList = new ArrayList<>();

    @Builder
    private Post(String title, String content, String image, String mood, String weather) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.mood = mood;
        this.weather = weather;
    }
}
