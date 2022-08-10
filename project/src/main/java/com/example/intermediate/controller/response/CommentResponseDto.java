package com.example.intermediate.controller.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.SubComment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.catalina.LifecycleState;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponseDto {
  private Long id;
  private String author;
  private String content;
  private List<SubCommentResponseDto> subCommentList;
  private LocalDateTime createdAt;
  private LocalDateTime modifiedAt;
}
