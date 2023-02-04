package com.ISSUberTim10.ISSUberTim10.appUser.account.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.appUser.account.AppUser;
import com.ISSUberTim10.ISSUberTim10.appUser.account.Message;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IMessageService {

    public List<Message> getMessagesBySenderOrReceiver(AppUser sender, AppUser receiver, Pageable pageable);
    public Message save(Message message);

}
