package org.example.autologin.utils;

public class Result<T> {
    //code
    private String code;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    //msg
    private String msg;
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    //token
    private String token;
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    //errorCount
    private int errorCount;
    public int getErrorCount() {
        return errorCount;
    }
    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }
    //data
    private T data;
    public T getData() {
        return data;
    }
    public void setData(T data) {
        this.data = data;
    }
    //url
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String url) {
        this.url = url;
    }


//构造成功与失败结果
    public Result() {
    }
    //构造函数，参数为data
    public Result(T data) {
        this.data = data;
    }

//success
    //构造函数，参数为空
    public static Result success() {
        Result result = new Result<>();
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }
    //构造函数，参数为data
    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg("成功");
        return result;
    }
    //构造函数，参数为data和msg
    public static <T> Result<T> success(T data,String msg) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg(msg);
        return result;
    }
    //构造函数，参数为data和msg、errorCount、url
    public static <T> Result<T> success(T data,String msg,int errorCount,String url) {
        Result<T> result = new Result<>(data);
        result.setCode("0");
        result.setMsg(msg);
        result.setErrorCount(errorCount);
        result.setUrl(url);
        return result;
    }
//    //构造函数，参数为code和msg、errorCount
//    public static <T> Result<T> succes(String code, String msg,int errorCount) {
//        Result<T> result = new Result<>();
//        result.setCode(code);
//        result.setMsg(msg);
//        result.setErrorCount(errorCount);
//        return result;
//    }
    //构造函数，参数为code和msg、url
    public static Result success(String code, String msg,String url) {
        Result result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setUrl(url);
        return result;
    }
//error
    //构造函数，参数为code和msg
    public static Result error(String code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
    //构造函数，参数为code和msg、errorCount、url
    public static Result error(String code, String msg,int errorCount,String url) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setErrorCount(errorCount);
        result.setUrl(url);
        return result;
    }
    //构造函数，参数为code和msg、errorCount
    public static Result error(String code, String msg,int errorCount) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setErrorCount(errorCount);
        return result;
    }
}
