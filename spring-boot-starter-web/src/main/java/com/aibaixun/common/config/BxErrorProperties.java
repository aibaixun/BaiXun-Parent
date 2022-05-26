package com.aibaixun.common.config;

import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.stereotype.Service;

/**
 * @author wang xiao
 * @date 2022/5/26
 */
@Service("bxErrorProperties")
public class BxErrorProperties extends ErrorProperties {
    public BxErrorProperties() {
    }
}
