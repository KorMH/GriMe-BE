package com.sparta.grimebe.post.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.grimebe.User.entity.User;
import com.sparta.grimebe.comment.entity.Comment;
import com.sparta.grimebe.global.BaseTimeEntity;
import com.sparta.grimebe.post.dto.PostRequestDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @OrderBy("id DESC")
    private List<Comment> commentList = new ArrayList<>();

    @Builder
    private Post(String title, String content, String image, String mood, String weather, User user) {
        this.title = title;
        this.content = content;
        this.image = image;
        this.mood = mood;
        this.weather = weather;
        this.user = user;
    }

    public void modifyPost(PostRequestDTO postRequestDTO) {
        this.title = postRequestDTO.getTitle();
        this.content = postRequestDTO.getContent();
        this.mood = postRequestDTO.getMood();
        this.weather = postRequestDTO.getWeather();
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
