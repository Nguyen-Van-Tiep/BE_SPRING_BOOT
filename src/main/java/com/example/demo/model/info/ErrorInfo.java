package com.example.demo.model.info;

import com.example.demo.model.BaseObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorInfo extends BaseObject {
    private int status;
    private String error;
    private String path;
    private String uuid;
    //Optional
    private Object data;

}
