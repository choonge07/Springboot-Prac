package com.example.intermediate.repository.heart;

import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.domain.heart.SubCommentHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SubCommentHeartRepository extends JpaRepository<SubCommentHeart, Long> {
    Optional<SubCommentHeart> findSubCommentHeartByMemberAndSubCommentId(Member member, Long subCommentId);
}
