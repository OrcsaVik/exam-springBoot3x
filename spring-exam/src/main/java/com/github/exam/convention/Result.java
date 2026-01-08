package com.github.exam.convention;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;

@Data
@Accessors(chain = true)
public class Result<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public static final String SUCCESS_CODE = "200";
    public static final String FAIL_CODE = "401";

    private String code;
    private String message;
    private T data;


    public static <T> Result<T> success(T data) {
        return new Result<T>().setCode(SUCCESS_CODE).setMessage("Success").setData(data);
    }

    public static <T> Result<T> fail(String message) {
        return new Result<T>().setCode(FAIL_CODE).setMessage(message);
    }

}