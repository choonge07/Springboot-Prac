package com.example.intermediate.controller.heart;

import com.example.intermediate.controller.response.ResponseDto;
import com.example.intermediate.service.heart.PostHeartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostHeartController {

    private final PostHeartService heartService;

    @RequestMapping(value = "/api/auth/heart/post/{id}", method = RequestMethod.POST)
    public ResponseDto<?> heart(@PathVariable Long id, HttpServletRequest request) {
        return heartService.heart(id,request);
    }

    @RequestMapping(value = "/api/auth/heart/post/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> unHeart(@PathVariable Long id, HttpServletRequest request) {
        return heartService.unHeart(id, request);
    }
}
