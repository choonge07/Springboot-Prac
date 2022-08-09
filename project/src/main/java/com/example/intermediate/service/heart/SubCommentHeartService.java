package com.example.intermediate.service.heart;

import antlr.Token;
import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.domain.Member;
import com.example.intermediate.domain.SubComment;
import com.example.intermediate.domain.heart.CommentHeart;
import com.example.intermediate.domain.heart.SubCommentHeart;
import com.example.intermediate.jwt.TokenProvider;
import com.example.intermediate.repository.SubCommentRepository;
import com.example.intermediate.repository.heart.SubCommentHeartRepository;
import com.example.intermediate.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubCommentHeartService {

    private final TokenProvider tokenProvider;
    private final SubCommentHeartRepository subCommentHeartRepository;
    private final SubCommentService subCommentService;

    public ResponseDto<?> heart(Long subCommentId, HttpServletRequest request) {
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
        SubComment subComment = subCommentService.isPresentSubComment(subCommentId);
        if (null == subComment) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 SubComment ID 입니다.");
        }

        SubCommentHeart subCommentHeart = SubCommentHeart.builder()
                .member(member)
                .subComment(subComment)
                .build();
        subCommentHeartRepository.save(subCommentHeart);
        return ResponseDto.success("success");

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
        SubCommentHeart subCommentHeart = isPresentHeart(id);
        if (null == subCommentHeart) {
            return ResponseDto.fail("NOT_FOUND", "존재하지 않는 Heart ID 입니다.");
        }
        if (subCommentHeart.validateMember(member)) {
            return ResponseDto.fail("BAD_REQUEST", "작성자만 취소할 수 있습니다.");
        }

        subCommentHeartRepository.delete(subCommentHeart);
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
    public SubCommentHeart isPresentHeart(Long id) {
        Optional<SubCommentHeart> optionalSubCommentHeart = subCommentHeartRepository.findById(id);
        return optionalSubCommentHeart.orElse(null);
    }


}
