package com.smartmall.domain;

import lombok.Data;

@Data
public class Result {
    private Integer code;
    private String msg;
    private Object data;
    public static Result success(){
        Result result = new Result();
        result.setCode(200);
        result.setMsg("success");
        return result;
    }

    public static Result success(Object data){
        Result result = new Result();
        result.data = data;
        result.setCode(200);
        result.setMsg("success");
        return result;
    }
    public static Result error(String msg){
        Result result = new Result();
        result.setMsg(msg);
        result.setCode(500);
        result.setMsg("error");
        return result;
    }
}
