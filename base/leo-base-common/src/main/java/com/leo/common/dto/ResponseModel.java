package com.leo.common.dto;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.leo.common.constant.BaseConstants;
import com.leo.common.exception.BaseExceptionCode;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回参数模型
 * -1 和 90开头的错误码为系统使用，其他场景自定义错误码即可
 *
 * @author leo
 */
public class ResponseModel {

    /**
     * 成功响应吗
     */
    private final static String GLOBAL_DEFAULT_SUCCESS = "0";
    /**
     * 默认逻辑错误响应吗,ResponeCodeException  默认错误也是这个，前端会对这个进行统一错误提示。
     */
    private final static String GLOBAL_DEFAULT_ERROR = "-1";

    private final static String GLOBAL_DEFAULT_SUCCESS_MSG = "操作成功";

    private final static String GLOBAL_DEFAULT_ERROR_MSG = "操作失败";

    /**
     * 响应码
     */
    private String code = GLOBAL_DEFAULT_SUCCESS;
    /**
     * 响应信息
     */
    private String msg = GLOBAL_DEFAULT_SUCCESS_MSG;
    /**
     * requestId
     */
    private String requestId;

    /**
     * 总条数，前段分页插件分页用
     */
    private long count;

    /**
     * 实际数据
     */
    private Object data;

    public ResponseModel() {
		this.requestId = MDC.get(BaseConstants.LOG_TRACE_ID);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public long getCount() {
        return count;
    }

    public void setCount(long count) {
        this.count = count;
    }

    public static ResponseModel ok() {
        return ok(GLOBAL_DEFAULT_SUCCESS_MSG);
    }

    public static ResponseModel ok(Object data) {
        ResponseModel responeModel = new ResponseModel();
        responeModel.setData(data);
        return responeModel;
    }

    public static ResponseModel ok(String msg) {
        ResponseModel responeModel = new ResponseModel();
        responeModel.setMsg(msg);
        return responeModel;
    }

    public static ResponseModel ok(String msg, Object data) {
        ResponseModel responeModel = new ResponseModel();
        responeModel.setMsg(msg);
        responeModel.setData(data);
        return responeModel;
    }

    public static ResponseModel ok(IPage<?> page) {
        ResponseModel responeModel = new ResponseModel();
        if (page != null) {
            responeModel.setCount(page.getTotal());
            responeModel.setData(page.getRecords());
        }
        return responeModel;
    }

    public static ResponseModel error() {
        return error(GLOBAL_DEFAULT_ERROR_MSG);
    }

    public static ResponseModel error(String code, String msg) {
        ResponseModel responeModel = new ResponseModel();
        responeModel.setCode(code);
        responeModel.setMsg(msg);
        return responeModel;
    }

    public static ResponseModel error(String code, String msg, Object... params) {
        ResponseModel responeModel = new ResponseModel();
        responeModel.setCode(code);
        if (null != params && StringUtils.isNotEmpty(msg)) {
            MessageFormat mf = new MessageFormat(msg);
            msg = mf.format(params);
        }
        responeModel.setMsg(msg);
        return responeModel;
    }

    public static ResponseModel error(String msg) {
        ResponseModel responeModel = new ResponseModel();
        responeModel.setCode(GLOBAL_DEFAULT_ERROR);
        responeModel.setMsg(msg);
        return responeModel;
    }

    public static ResponseModel error(BaseExceptionCode baseExceptionCode) {
        ResponseModel responeModel = new ResponseModel();
        responeModel.setCode(baseExceptionCode.getCode());
        responeModel.setMsg(baseExceptionCode.getMsg());
        return responeModel;
    }

    /**
     * 将data设置成为map
     *
     * @param key
     * @param value
     */
    public void addProperty(String key, String value) {
        if (null == data || (!(data instanceof Map))) {
            Map<String, Object> propertyMap = new HashMap<String, Object>();
            propertyMap.put(key, value);
            this.setData(propertyMap);
        } else {
            @SuppressWarnings("unchecked")
            Map<String, Object> map = (Map<String, Object>) data;
            map.put(key, value);
        }
    }
}
