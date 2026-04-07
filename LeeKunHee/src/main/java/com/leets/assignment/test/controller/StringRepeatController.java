package com.leets.assignment.test.controller;

import com.leets.assignment.test.dto.RepeatRequestDto;
import com.leets.assignment.test.dto.RepeatResponseDto;
import com.leets.assignment.test.service.StringRepeatService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor // final이 붙은 필드를 모아서 생성자 자동 생성.
public class StringRepeatController {

    private final StringRepeatService stringRepeatService;


    @PostMapping("/string/repeat")
    public RepeatResponseDto repeat(@RequestBody RepeatRequestDto requestDto) {
        // @RequestBody : 사용자가 보낸 JSON 본문을 자바 객체로 변환
        return stringRepeatService.repeatString(requestDto.getValue());
    }
}
