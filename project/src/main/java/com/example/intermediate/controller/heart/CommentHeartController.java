package com.example.intermediate.controller.heart;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.heart.CommentHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class CommentHeartController {

    private final CommentHeartService heartService;

    @RequestMapping(value = "/api/auth/heart/comment/{id}", method = RequestMethod.POST)
    public ResponseDto<?> heart(@PathVariable Long id, HttpServletRequest request) {
        return heartService.heart(id,request);
    }

    @RequestMapping(value = "/api/auth/heart/comment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> unHeart(@PathVariable Long id, HttpServletRequest request) {
        return heartService.unHeart(id, request);
    }
}
