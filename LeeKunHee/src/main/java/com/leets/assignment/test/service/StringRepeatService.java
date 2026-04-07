package com.leets.assignment.test.service;


import com.leets.assignment.test.dto.RepeatResponseDto;
import org.springframework.stereotype.Service;


@Service
public class StringRepeatService {

    public RepeatResponseDto repeatString(String value) {
        return new RepeatResponseDto(value, value);
    }
}
