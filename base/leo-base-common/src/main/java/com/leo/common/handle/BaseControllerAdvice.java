package com.leo.common.handle;

import com.leo.common.dto.ResponseModel;
import com.leo.common.exception.BaseExceptionCode;
import com.leo.common.exception.ResponeException;
import com.leo.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringEscapeUtils;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Date;

/**
 * <p>Title: BaseControllerAdvice</p>
 * <p>Description: 自定义逻辑处理、异常捕获处理</p>
 *
 * @author leo
 * @date 2020-09-08
 */

@ControllerAdvice
@Slf4j
public class BaseControllerAdvice {

    /**
     * 初始化数据绑定 1. 将所有传递进来的String进行HTML编码，防止XSS攻击   2. 将字段中Date类型转换为String类型
     */
    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        // String类型转换，将所有传递进来的String进行HTML编码，防止XSS攻击
        binder.registerCustomEditor(String.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(text == null ? null : StringEscapeUtils.escapeHtml4(text.trim()));
            }

            @Override
            public String getAsText() {
                Object value = getValue();
                return value != null ? value.toString() : "";
            }
        });
        // Date 类型转换
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(DateUtils.parseDate(text));
            }
        });
    }

    @ResponseBody
    @ExceptionHandler(ResponeException.class)
    public ResponseModel responeException(ResponeException e) {
        log.error("respone exception ", e);
        ResponseModel responeModel = ResponseModel.error(e.getResponeCode(), e.getResponeMsg());
        return responeModel;
    }

    @ResponseBody
    @ExceptionHandler(BindException.class)
    public ResponseModel bindException(BindException e) {
        log.error("bind exception ", e);
        ResponseModel responeModel = ResponseModel.error(BaseExceptionCode.PARAM_BIND_ERROR.getCode(), BaseExceptionCode.PARAM_BIND_ERROR.getMsg(), e.getMessage());
        return responeModel;
    }

    @ResponseBody
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseModel exception(Exception e) {
        log.error("global exception ", e);
        ResponseModel responeModel = ResponseModel.error(BaseExceptionCode.UNKNOWN.getCode(), e.getMessage());
        return responeModel;
    }
}
