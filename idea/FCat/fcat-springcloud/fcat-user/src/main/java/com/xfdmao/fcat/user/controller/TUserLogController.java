package com.xfdmao.fcat.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfdmao.fcat.common.constant.CommonConstant;
import com.xfdmao.fcat.common.controller.BaseController;
import com.xfdmao.fcat.common.util.JsonUtil;
import com.xfdmao.fcat.user.entity.TUserLog;
import com.xfdmao.fcat.user.service.TUserLogService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by fier on 2017/11/28.
 */
@RestController
@RequestMapping("v1/tUserLog")
public class TUserLogController extends BaseController<TUserLogService,TUserLog,Integer> {

    @RequestMapping(value = "/listByPage",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listByPage(Integer pageNum, Integer pageSize){

        pageNum = pageNum == null? CommonConstant.PAGE_NUM:pageNum;
        pageSize = pageSize == null?CommonConstant.PAGE_SIZE:pageSize;

        PageHelper.startPage(pageNum, pageSize);
        List<TUserLog> tMenuList = baseServiceImpl.listByPageNewRecord();
        PageInfo page = new PageInfo(tMenuList);
        return JsonUtil.getSuccessJsonObject(page);
    }
}
