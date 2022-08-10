package com.example.intermediate.repository.heart;

import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.heart.CommentHeart;
import com.example.intermediate.domain.heart.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentHeartRepository extends JpaRepository<CommentHeart, Long> {
    Optional<CommentHeart> findCommentHeartByMemberAndCommentId(Member member, Long commentId);
    List<CommentHeart> findCommentHeartByMemberId(Long memberId);
}
