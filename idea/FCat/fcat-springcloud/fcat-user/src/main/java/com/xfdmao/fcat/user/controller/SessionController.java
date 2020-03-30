package com.xfdmao.fcat.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.xfdmao.fcat.api.vo.authority.SessionInfo;
import com.xfdmao.fcat.common.util.JsonUtil;
import com.xfdmao.fcat.user.feign.TUserServiceFeign;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by xiangfei on 2017/10/17.
 */
@RestController
@RequestMapping("v1/session")
public class SessionController extends TUserServiceFeign {
    private Logger logger = LoggerFactory.getLogger(SessionController.class);
    @Autowired
    protected HttpServletRequest request;
    /**
     * 测试从session中获取用户信息
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "获取session中的信息" )
    @RequestMapping(value = "sessionInfo", method = RequestMethod.GET)
    public JSONObject sessionUserInfo()throws Exception{
        try {
            SessionInfo sessionInfo  = (SessionInfo) request.getSession().getAttribute("sessionInfo");
            logger.info("FCat:sessionInfo:{}",sessionInfo);
            return JsonUtil.getSuccessJsonObject(sessionInfo);
        }catch ( Exception e){
            e.printStackTrace();
        }
        return JsonUtil.getFailJsonObject();
    }

}
