package com.leets.assignment.domain.post.dto.req;

import com.leets.assignment.domain.post.entity.BlockType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

public class PostRequestDTO {

    @Getter
    @NoArgsConstructor
    public static class CreatePostDTO {
        @NotBlank(message = "제목을 입력해주세요.")
        @Size(max = 255, message = "제목은 최대 255자까지 가능합니다.")
        private String title;

        @NotNull(message = "작성자 ID는 필수입니다.")
        private Long userId;

        @NotEmpty(message = "내용을 입력해주세요.")
        @Valid // 내부 블록들의 검증을 수행하기 위해 필수!
        private List<BlockDTO> blocks;
    }

    @Getter
    @NoArgsConstructor
    public static class BlockDTO {
        @NotNull(message = "순서는 필수입니다.")
        private Integer sequence;

        @NotNull(message = "블록 타입은 필수입니다.")
        private BlockType blockType;

        @NotBlank(message = "내용을 입력해주세요.") // 각 블록의 내용이 비었을 때
        private String content;
    }


}