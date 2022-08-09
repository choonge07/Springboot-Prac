package com.example.intermediate.service.heart;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.Post;
import com.example.intermediate.domain.heart.PostHeart;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.heart.PostHeartRepository;
import com.example.intermediate.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostHeartService {

    private final PostHeartRepository postHeartRepository;
    private final TokenProvider tokenProvider;
    private final PostService postService;

    public ResponseDto<?> heart(Long postId, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        Post post = postService.isPresentPost(postId);
        if (null == post) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 게시글 ID 입니다.");
        }
        PostHeart postHeart = PostHeart.builder()
                .member(member)
                .post(post)
                .build();
        postHeartRepository.save(postHeart);
        return ResponseDto.success("like success");
    }

    public ResponseDto<?> unHeart(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("MEMBER_NOT_FOUND", "로그인이 필요합니다.");
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        PostHeart postHeart = isPresentHeart(id);
        if (null == postHeart) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 Heart ID 입니다.");
        }
        if (postHeart.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 취소할 수 있습니다.");
        }

        postHeartRepository.delete(postHeart);
        return ResponseDto.success("success");
    }

    @Transactional
    public Member validateMember(HttpServletRequest request) {
        if (!tokenProvider.validateToken(request.getHeader("Refresh-Token"))) {
            return null;
        }
        return tokenProvider.getMemberFromAuthentication();
    }

    @Transactional(readOnly = true)
    public PostHeart isPresentHeart(Long id) {
        Optional<PostHeart> optionalPostHeart = postHeartRepository.findById(id);
        return optionalPostHeart.orElse(null);
    }



}
