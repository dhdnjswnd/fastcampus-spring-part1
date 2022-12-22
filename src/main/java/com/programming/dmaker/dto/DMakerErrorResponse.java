package com.programming.dmaker.dto;

import com.programming.dmaker.exception.DMakerErrorCode;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DMakerErrorResponse {
    private DMakerErrorCode errorCode;
    private String errorMessage;
    // 실무에서 API별로 성공때 내려주는 응답은 다른 방식으로 하고, 실패로 내려가는 경우 공통 실패 DTo를 만들어서 활용한다.
}
