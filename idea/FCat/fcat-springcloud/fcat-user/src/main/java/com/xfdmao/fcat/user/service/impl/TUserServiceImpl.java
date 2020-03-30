package com.xfdmao.fcat.user.service.impl;

import com.xfdmao.fcat.common.service.impl.BaseServiceImpl;
import com.xfdmao.fcat.user.constant.UserConstant;
import com.xfdmao.fcat.user.entity.TGroup;
import com.xfdmao.fcat.user.entity.TUser;
import com.xfdmao.fcat.user.entity.TUserGroup;
import com.xfdmao.fcat.user.mapper.TUserMapper;
import com.xfdmao.fcat.user.service.TGroupService;
import com.xfdmao.fcat.user.service.TUserGroupService;
import com.xfdmao.fcat.user.service.TUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by xiangfei on 2017/10/16.
 */
@Service
public class TUserServiceImpl extends BaseServiceImpl<TUserMapper,TUser> implements TUserService{

    @Autowired
    private TUserGroupService tUserGroupService;
    @Autowired
    private TGroupService tGroupService;

    @Override
    public TUser getByUsername(String userName) {
        return mapper.getByUsername(userName);
    }

    @Override
    public List<TUser> getByKey(String key) {
        return mapper.getByKey(key);
    }

    @Override
    public List<TUser> getLeadersByGroupId(Integer groupId) {
        return mapper.getLeadersByGroupId(groupId);
    }

    @Override
    public List<TUser> getMembersByGroupId(Integer groupId) {
        return mapper.getMembersByGroupId(groupId);
    }

    @Override
    public void register(TUser tUser) {
        //获取普通用户角色
        TGroup tGroup = new TGroup();
        tGroup.setCode(UserConstant.ROLE_USER_CODE);
        tGroup = tGroupService.selectOne(tGroup);

        //插入用户
        mapper.insert(tUser);

        //给新注册用户授权普通用户角色
        TUserGroup tUserGroup = new TUserGroup();
        tUserGroup.setUserId(tUser.getId());
        tUserGroup.setGroupId(tGroup.getId());
        tUserGroup.setType(UserConstant.USER_GROUP_TYPE_MEMBER);
        tUserGroupService.insert(tUserGroup);

    }
}
