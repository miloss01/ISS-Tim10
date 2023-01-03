package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Message;

import java.util.List;

public interface IMessageService {

    public List<Message> getMessagesBySenderOrReceiver(AppUser sender, AppUser receiver);
    public Message save(Message message);

}
