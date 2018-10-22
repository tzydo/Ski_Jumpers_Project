package com.pl.skijumping.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JumpResultToSkiJumperDTO {

    private Long id;
    private Long jumpResultId;
    private Long skiJumperId;

    public JumpResultToSkiJumperDTO id(Long id) {
        this.id = id;
        return this;
    }

    public JumpResultToSkiJumperDTO jumpResultId(Long jumpResultId) {
        this.jumpResultId = jumpResultId;
        return this;
    }

    public JumpResultToSkiJumperDTO skiJumperId(Long skiJumperId) {
        this.skiJumperId = skiJumperId;
        return this;
    }
}