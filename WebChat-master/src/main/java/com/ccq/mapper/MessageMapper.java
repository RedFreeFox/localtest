package com.ccq.mapper;


import com.ccq.pojo.Message;

import java.util.List;

public interface MessageMapper {
    void addMessage(Message message);

    List<Message> getAllMessage();
}
