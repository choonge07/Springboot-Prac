package com.example.intermediate.service.heart;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.Comment;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.heart.CommentHeart;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.heart.CommentHeartRepository;
import com.example.intermediate.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentHeartService {

    private final CommentHeartRepository commentHeartRepository;
    private final CommentService commentService;
    private final TokenProvider tokenProvider;

    public ResponseDto<?> heart(Long commentId, HttpServletRequest request) {
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
        Comment comment = commentService.isPresentComment(commentId);
        if (null == comment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 Comment ID 입니다.");
        }

        CommentHeart commentHeart = CommentHeart.builder()
                .member(member)
                .comment(comment)
                .build();
        commentHeartRepository.save(commentHeart);
        return ResponseDto.success("like success");
    }

    public ResponseDto<?> unHeart(Long id, HttpServletRequest request) {
        if (null == request.getHeader("Refresh-Token")) {
            return ResponseDto.fail("NOT_FOUND", "로그인이 필요합니다.");
        }
        if (null == request.getHeader("Authorization")) {
            return ResponseDto.fail("NOT_FOUND", "로그인이 필요합니다.");
        }
        Member member = validateMember(request);
        if (null == member) {
            return ResponseDto.fail("INVALID_TOKEN", "Token이 유효하지 않습니다.");
        }
        CommentHeart commentHeart = isPresentHeart(id);
        if (null == commentHeart) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 Heart ID 입니다.");
        }
        if (commentHeart.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 취소할 수 있습니다.");
        }
        commentHeartRepository.delete(commentHeart);
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
    public CommentHeart isPresentHeart(Long id) {
        Optional<CommentHeart> optionalCommentHeart = commentHeartRepository.findById(id);
        return optionalCommentHeart.orElse(null);
    }
}
