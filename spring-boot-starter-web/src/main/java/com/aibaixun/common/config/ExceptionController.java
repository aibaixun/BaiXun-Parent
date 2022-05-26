package com.aibaixun.common.config;


import com.aibaixun.basic.result.BaseResultCode;
import com.aibaixun.basic.result.JsonResult;
import com.aibaixun.basic.util.IDUtils;
import com.aibaixun.common.util.SerializeUtil;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@RestController
@RequestMapping({"${server.error.path:${error.path:/error}}"})
public class ExceptionController extends BasicErrorController {

    public ExceptionController(ErrorAttributes errorAttributes, @Qualifier("bxErrorProperties") BxErrorProperties errorProperties) {
        super(errorAttributes, errorProperties);
    }

    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        HttpStatus status = this.getStatus(request);
        String requestId = MDC.get("TraceId") != null ? MDC.get("TraceId") : IDUtils.randomUUID();
        JsonResult<?> fail = JsonResult.failed(BaseResultCode.GENERAL_ERROR, status.toString());
        SerializeUtil.writeResponse(response, new ResponseEntity(fail, status));
        return null;
    }

    @RequestMapping(
            produces = {"application/json;charset=UTF-8"}
    )
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = this.getStatus(request);
        String requestId = MDC.get("TraceId") != null ? MDC.get("TraceId") : IDUtils.randomUUID();
        JsonResult<?> fail = JsonResult.failed(BaseResultCode.GENERAL_ERROR, status.toString());
        return new ResponseEntity(fail, status);
    }
}
