package com.example.intermediate.controller.response;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.heart.CommentHeart;
import com.example.intermediate.domain.heart.PostHeart;
import com.example.intermediate.domain.heart.SubCommentHeart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MyPageResponseDto {
    private String nickname;
    private List<PostResponseDto> postList;
    private List<CommentResponseDto> commentList;
    private List<PostHeartResponseDto> postHearts;
    private List<CommentHeartResponseDto> commentHearts;
}
