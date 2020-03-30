package com.xfdmao.fcat.coin.controller;

import com.alibaba.fastjson.JSONObject;
import com.xfdmao.fcat.common.controller.BaseController;
import com.xfdmao.fcat.common.util.JsonUtil;
import com.xfdmao.fcat.coin.entity.TDict;
import com.xfdmao.fcat.coin.service.TDictService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
        List<TDict> list = baseServiceImpl.selectList(tDict);
        for(TDict t:list){
            System.out.println(t.getName());
        }
        return JsonUtil.getSuccessJsonObject(list);
    }
}
