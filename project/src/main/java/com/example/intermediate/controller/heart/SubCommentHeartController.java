package com.example.intermediate.controller.heart;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.heart.CommentHeartService;
import com.example.intermediate.service.heart.SubCommentHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class SubCommentHeartController {

    private final SubCommentHeartService subCommentHeartService;

    @RequestMapping(value = "/api/auth/heart/sub-comment/{id}", method = RequestMethod.POST)
    public ResponseDto<?> heart(@PathVariable Long id, HttpServletRequest request) {
        return subCommentHeartService.heart(id,request);
    }

    @RequestMapping(value = "/api/auth/heart/sub-comment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> unHeart(@PathVariable Long id, HttpServletRequest request) {
        return subCommentHeartService.unHeart(id, request);
    }
}
