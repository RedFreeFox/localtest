package com.ccq.service.impl;

import com.ccq.mapper.MessageMapper;
import com.ccq.mapper.UserMapper;
import com.ccq.pojo.Message;
import com.ccq.pojo.User;
import com.ccq.pojo.UserExample;
import com.ccq.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

/**
 * @author ccq
 * @Description
 * @date 2017/11/28 22:12
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private MessageMapper messageMapper;

    public User getUserById(String userId) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andUseridEqualTo(userId);
        List<User> users = userMapper.selectByExample(userExample);
        if (CollectionUtils.isNotEmpty(users)){
            return users.get(0);
        }
        return null;
    }

    public int updateUser(User user) {
        int flag = userMapper.updateByPrimaryKeySelective(user);
        return flag;
    }

    public int insert(User user) {
        return userMapper.insert(user);
    }

    @Override
    public void addMessage(Message message) {
        messageMapper.addMessage(message);
    }

    @Override
    public List<Message> getAllMessage() {


        List<Message> allMessage = messageMapper.getAllMessage();
        return allMessage;
    }
}
