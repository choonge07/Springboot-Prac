package com.example.intermediate.domain;

import com.example.intermediate.controller.request.PostRequestDto;
import com.example.intermediate.domain.heart.PostHeart;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.FetchType.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Post extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String title;

  @Column(nullable = false)
  private String content;

  @OneToMany(mappedBy = "post", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  @JoinColumn(name = "member_id", nullable = false)
  @ManyToOne(fetch = LAZY)
  private Member member;

  @OneToMany(mappedBy = "post", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private List<PostHeart> postHearts;


  public void update(PostRequestDto postRequestDto) {
    this.title = postRequestDto.getTitle();
    this.content = postRequestDto.getContent();
  }

  public boolean validateMember(Member member) {
    return !this.member.equals(member);
  }

}
