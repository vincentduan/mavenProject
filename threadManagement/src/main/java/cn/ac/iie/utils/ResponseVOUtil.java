package cn.ac.iie.utils;

import cn.ac.iie.vo.ResponseResult;
import org.springframework.http.HttpStatus;

/**
 * @author vincent
 * @time 2019-08-05 14:14
 */
public class ResponseVOUtil {
    public static ResponseResult success(Object data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(200);
        responseResult.setMessage("success");
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult failed(Object data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(500);
        responseResult.setMessage("failed");
        responseResult.setData(data);
        return responseResult;
    }

    public static ResponseResult exception(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(502);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult error(String message) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(501);
        responseResult.setMessage(message);
        return responseResult;
    }

    public static ResponseResult UNAUTHORIZED() {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(401);
        responseResult.setMessage("failed");
        responseResult.setData(HttpStatus.UNAUTHORIZED);
        return responseResult;
    }
}
