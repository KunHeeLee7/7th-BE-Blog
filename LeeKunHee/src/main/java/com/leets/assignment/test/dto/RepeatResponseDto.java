package com.leets.assignment.test.dto;

import lombok.Getter;

@Getter
public class RepeatResponseDto {
    private final String string_one;
    private final String string_two;

    public RepeatResponseDto(String string_one, String string_two) {
        this.string_one = string_one;
        this.string_two = string_two;
    }
}
