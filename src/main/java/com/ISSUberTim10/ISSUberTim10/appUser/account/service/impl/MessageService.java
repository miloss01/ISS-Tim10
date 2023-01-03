package com.ISSUberTim10.ISSUberTim10.appUser.account.service.impl;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Message;
import com.ISSUberTim10.ISSUberTim10.appUser.account.repository.MessageRepository;
import com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces.IMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService implements IMessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Override
    public List<Message> getMessagesBySenderOrReceiver(AppUser sender, AppUser receiver) {
        return messageRepository.findAllBySenderOrReceiver(sender, receiver);
    }

    @Override
    public Message save(Message message) {
        return messageRepository.save(message);
    }

}
