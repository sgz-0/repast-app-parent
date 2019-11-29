package com.aaa.lee.app.base;

import com.aaa.lee.app.status.LoginStatus;
import com.aaa.lee.app.status.StatusEnum;
import org.springframework.stereotype.Controller;

/**
 * @Company AAA软件教育
 * @Author SGZ
 * @Date Create in 2019/11/21 16:24
 * @Description
 **/
@Controller
public class BaseController {
    private ResultData resultData = new ResultData();
    /**
     * @Author SGZ
     * @Description
     *      登陆成功，使用系统消息
     * @Param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/21
     */
    /**
     * @author Seven Lee
     * @description
     *      登录成功，使用系统消息
     * @param []
     * @date 2019/11/20
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    protected ResultData success() {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(LoginStatus.LOGIN_SUCCESS.getMsg());
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      登录成功，自定义返回消息
     * @param [msg]
     * @date 2019/11/20
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    protected ResultData success(String msg) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      登录成功，使用系统消息，自定义返回值
     * @param [data]
     * @date 2019/11/20
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    protected ResultData success(Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(LoginStatus.LOGIN_SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      登录成功，自定义消息，自定义返回值
     * @param [msg, data]
     * @date 2019/11/20
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    protected ResultData success(String msg, Object data) {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }

    /**
     * @author Seven Lee
     * @description
     *      登录失败，返回系统消息
     * @param []
     * @date 2019/11/20
     * @return com.aaa.lee.app.base.ResultData
     * @throws
     **/
    protected ResultData failed() {
        ResultData resultData = new ResultData();
        resultData.setCode(LoginStatus.LOGIN_FAILED.getCode());
        resultData.setMsg(LoginStatus.LOGIN_FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author SGZ
     * @Description
     *      操作成功，返回系统信息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData operateSuccess(){
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(StatusEnum.SUCCESS.getMsg());
        return resultData;
    }
    /**
     * @Author SGZ
     * @Description
     *      操作成功返回自定义信息
     * @Param  * @param msg
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData operateSuccess(String msg){
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(msg);
        return resultData;
    }
    /**
     * @Author SGZ
     * @Description
     *      操作成功，返回系统信息，自定义数据
     * @Param  * @param data
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData operateSuccess(Object data){
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(StatusEnum.SUCCESS.getMsg());
        resultData.setData(data);
        return resultData;
    }
    /**
     * @Author SGZ
     * @Description
     *      操作成功，返回自定义信息，自定义数据
     * @Param  * @param msg
 * @param data
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData operateSuccess(String msg, Object data){
        resultData.setCode(StatusEnum.SUCCESS.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }
    /**
     * @Author SGZ
     * @Description
     *      操作失败，返回系统消息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/21
     */
    protected ResultData operateFailed(){
        resultData.setCode(StatusEnum.FAILED.getCode());
        resultData.setMsg(StatusEnum.FAILED.getMsg());
        return resultData;
    }

    /**
     * @Author SGZ
     * @Description
     *      信息存在，返回自定义信息，自定义数据
     * @Param  * @param msg
 * @param data
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData exist(String msg, Object data){
        resultData.setCode(StatusEnum.EXIST.getCode());
        resultData.setMsg(msg);
        resultData.setData(data);
        return resultData;
    }
    /**
     * @Author SGZ
     * @Description
     *      信息存在，返回系统信息，自定义数据
     * @Param  * @param data
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    protected ResultData exist(Object data){
        resultData.setCode(StatusEnum.EXIST.getCode());
        resultData.setMsg(StatusEnum.EXIST.getMsg());
        resultData.setData(data);
        return resultData;
    }
    /**
     * @Author SGZ
     * @Description
     *      信息存在，返回系统信息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    public ResultData exist(){
        resultData.setCode(StatusEnum.EXIST.getCode());
        resultData.setMsg(StatusEnum.EXIST.getMsg());
        return resultData;
    }
    /**
     * @Author SGZ
     * @Description
     *      信息不存在，返回系统信息
     * @Param  * @param
     * @Return com.aaa.lee.app.base.ResultData
     * @Date 2019/11/23
     */
    public ResultData notExist(){
        resultData.setCode(StatusEnum.NOT_EXIST.getCode());
        resultData.setMsg(StatusEnum.NOT_EXIST.getMsg());
        return resultData;
    }
    //TODO
}
