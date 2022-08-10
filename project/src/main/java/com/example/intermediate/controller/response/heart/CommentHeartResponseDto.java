package com.example.intermediate.controller.response.heart;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentHeartResponseDto {
    private Long commentId;
    private String content;
}
