package com.example.intermediate.domain;

import com.example.intermediate.controller.request.CommentRequestDto;

import javax.persistence.*;

import com.example.intermediate.domain.heart.CommentHeart;
import com.example.intermediate.domain.heart.PostHeart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Comment extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @JoinColumn(name = "post_id", nullable = false)
  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @JsonIgnore
  @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY,cascade = CascadeType.ALL, orphanRemoval = true)
  private List<SubComment> subComments;

  @JsonIgnore
  @OneToMany(mappedBy = "comment", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<CommentHeart> commentHearts;

  @Column(nullable = false)
  private String content;

  public void update(CommentRequestDto commentRequestDto) {
    this.content = commentRequestDto.getContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }
}
