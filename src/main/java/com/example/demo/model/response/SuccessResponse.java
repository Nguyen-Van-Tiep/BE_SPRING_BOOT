package com.example.demo.model.response;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class SuccessResponse<T> extends com.example.demo.model.response.BaseResponse {
    private T data;
}
