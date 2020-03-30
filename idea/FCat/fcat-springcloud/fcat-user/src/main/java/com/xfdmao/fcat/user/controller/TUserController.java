package com.xfdmao.fcat.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xfdmao.fcat.common.constant.CommonConstant;
import com.xfdmao.fcat.common.controller.BaseController;
import com.xfdmao.fcat.common.enumtype.ResultCodeEnum;
import com.xfdmao.fcat.common.util.CheckUtil;
import com.xfdmao.fcat.common.util.JsonUtil;
import com.xfdmao.fcat.common.util.TreeUtil;
import com.xfdmao.fcat.user.entity.TElement;
import com.xfdmao.fcat.user.entity.TMenu;
import com.xfdmao.fcat.user.entity.TUser;
import com.xfdmao.fcat.user.po.TMenuTree;
import com.xfdmao.fcat.user.service.TElementService;
import com.xfdmao.fcat.user.service.TMenuService;
import com.xfdmao.fcat.user.service.TUserService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.collections.ListUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by xiangfei on 2017/10/17.
 */
@RestController
@RequestMapping("v1/tUser")
public class TUserController extends BaseController<TUserService,TUser,Integer>{

    @Autowired
    private TMenuService tMenuService;
    @Autowired
    private TElementService tElementService;

    /**
     * 注册用户
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "注册用户信息" )
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public JSONObject registerUser(@RequestBody TUser tUser)throws Exception{
        String username = tUser.getUsername();
        String email = tUser.getEmail();
        String mobilePhone = tUser.getMobilePhone();

        if(!CheckUtil.checkEmaile(email)){
            return JsonUtil.getResultJson(ResultCodeEnum.EMAILCHECKFAIL);
        }
        TUser tUser1 = baseServiceImpl.getByUsername(username);
        if(tUser1!=null){
            return JsonUtil.getResultJson(ResultCodeEnum.USER_EXIST);
        }
        tUser1 = new TUser();
        tUser1.setMobilePhone(mobilePhone);
        List<TUser> tUserList = baseServiceImpl.selectList(tUser1);
        if(tUserList !=null && tUserList.size()>0){
            return JsonUtil.getResultJson(ResultCodeEnum.MOBILE_PHONE_CHECK_FAIL);
        }

        tUser1 = new TUser();
        tUser1.setEmail(tUser.getEmail());
        tUserList = baseServiceImpl.selectList(tUser1);
        if(tUserList !=null && tUserList.size()>0){
            return JsonUtil.getResultJson(ResultCodeEnum.EMAIL_EXIST);
        }
        tUser.setCreateTime(new Date());
        tUser.setUpdateTime(new Date());
        baseServiceImpl.register(tUser);
        return JsonUtil.getSuccessJsonObject();
    }
    /**
     * 通过名称模糊搜索获取用户列表
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "通过名称模糊搜索获取用户列表" )
    @RequestMapping(value = "getList/{key}", method = RequestMethod.GET)
    public JSONObject getList(@PathVariable String key)throws Exception{
        List<TUser> result = baseServiceImpl.getByKey(key);
        return JsonUtil.getSuccessJsonObject(result);
    }

    /**
     * 通过组织ID获取用户列表
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "通过组织ID获取管理者用户列表" )
    @RequestMapping(value = "getListByGroupId/{groupId}", method = RequestMethod.GET)
    public JSONObject getLeadersByGroupId(@PathVariable Integer groupId)throws Exception{
        JSONObject result = new JSONObject();
        List<TUser> leaders = baseServiceImpl.getLeadersByGroupId(groupId);
        List<TUser> members = baseServiceImpl.getMembersByGroupId(groupId);
        result.put("leaders",leaders);
        result.put("members",members);
        return JsonUtil.getSuccessJsonObject(result);
    }

    /**
     * 通过用户名获取菜单和元素的权限
     * @return
     * @throws RuntimeException
     */
    @ApiOperation(value = "通过用户名获取菜单和元素的权限" )
    @RequestMapping(value = "getAuthority/{username}", method = RequestMethod.GET)
    public JSONObject getAuthority(@PathVariable String username)throws Exception{
        JSONObject result = new JSONObject();
        List<TMenuTree> tMenuTreeList = new ArrayList<>();
        List<TMenu> tMenus = tMenuService.getAuthorityMenusByUsername(username);
        for(TMenu tMenu:tMenus){
            TMenuTree tMenuTree = new TMenuTree();
            BeanUtils.copyProperties(tMenu,tMenuTree);
            tMenuTreeList.add(tMenuTree);
        }
        List<TMenuTree> tMenuTrees = TreeUtil.buildByRecursive(tMenuTreeList,-1);

        List<TElement> tElements = tElementService.getAuthorityElementsByUsername(username);
        result.put("tMenuTrees",tMenuTrees);
        result.put("tElements",tElements);
        return JsonUtil.getSuccessJsonObject(result);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject get(@PathVariable Integer id){
        TUser tUser = baseServiceImpl.selectById(id);
        tUser.setPassword(null);
        return JsonUtil.getSuccessJsonObject(tUser);
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject update(@RequestBody TUser entity){
        entity.setPassword(baseServiceImpl.selectOne(entity).getPassword());
        int result= baseServiceImpl.updateById(entity);
        if(result==0){
            return JsonUtil.getFailJsonObject();
        }else{
            return JsonUtil.getSuccessJsonObject();
        }
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject list(){
        List<TUser> tUserList = baseServiceImpl.selectListAll();
        tUserList.forEach(tUser -> tUser.setPassword(null));
        return JsonUtil.getSuccessJsonObject(tUserList);
    }


    @RequestMapping(value = "/listByPage",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listByPage(Integer pageNum, Integer pageSize){

        pageNum = pageNum == null? CommonConstant.PAGE_NUM:pageNum;
        pageSize = pageSize == null?CommonConstant.PAGE_SIZE:pageSize;

        PageHelper.startPage(pageNum, pageSize);
        List<TUser> list = baseServiceImpl.selectListAll();
        list.forEach(tUser -> tUser.setPassword(null));
        PageInfo page = new PageInfo(list);
        return JsonUtil.getSuccessJsonObject(page);
    }
}
