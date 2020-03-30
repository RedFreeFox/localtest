package com.ccq.service;

import com.ccq.pojo.Message;
import com.ccq.pojo.User;

import java.util.List;

/**
 * @author ccq
 * @Description
 * @date 2017/11/28 22:11
 */
public interface UserService {

    public User getUserById(String userid);
    public int updateUser(User user);
    public int insert(User user);
    void addMessage(Message message);
    List<Message> getAllMessage();
}
