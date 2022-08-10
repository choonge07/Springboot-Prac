package com.example.intermediate.domain;

import com.example.intermediate.controller.request.SubCommentRequestDto;
import com.example.intermediate.domain.heart.SubCommentHeart;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.util.List;

import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubComment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    @JsonIgnore
    @OneToMany(mappedBy = "subComment", fetch = LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCommentHeart> subCommentHearts;
    public void update(SubCommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}
