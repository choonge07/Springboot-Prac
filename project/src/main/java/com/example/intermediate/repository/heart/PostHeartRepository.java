package com.example.intermediate.repository.heart;

import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.heart.CommentHeart;
import com.example.intermediate.domain.heart.PostHeart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostHeartRepository extends JpaRepository<PostHeart, Long> {

    Optional<PostHeart> findPostHeartByMemberAndPostId(Member member, Long postId);
    List<PostHeart> findPostHeartByMemberId(Long memberId);
}
