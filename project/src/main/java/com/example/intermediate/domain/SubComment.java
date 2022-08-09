package com.example.intermediate.domain;

import com.example.intermediate.controller.request.SubCommentRequestDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Builder
@Entity
@Getter
@Table(name = "sub_comment")
@NoArgsConstructor
@AllArgsConstructor
public class SubComment extends Timestamped {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = LAZY)
    private Member member;

    public void update(SubCommentRequestDto requestDto) {
        this.content = requestDto.getContent();
    }

    public boolean validateMember(Member member) {
        return !this.member.equals(member);
    }
}
