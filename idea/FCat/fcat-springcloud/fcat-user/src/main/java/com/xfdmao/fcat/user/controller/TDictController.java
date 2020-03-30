package com.xfdmao.fcat.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.xfdmao.fcat.common.controller.BaseController;
import com.xfdmao.fcat.common.util.JsonUtil;
import com.xfdmao.fcat.user.entity.TDict;
import com.xfdmao.fcat.user.service.TDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by fier on 2017/11/28.
 */
@RestController
@RequestMapping("v1/tDict")
public class TDictController extends BaseController<TDictService,TDict,Integer> {

    @GetMapping(value = "/getByCode/{code}")
    public JSONObject getByCode(@PathVariable(value = "code") String code){
        TDict tDict = new TDict();
        tDict.setCode(code);
        return JsonUtil.getSuccessJsonObject(baseServiceImpl.selectList(tDict));
    }
}
