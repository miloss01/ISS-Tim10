package com.ISSUberTim10.ISSUberTim10.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.domain.Panic;
import com.ISSUberTim10.ISSUberTim10.repository.PanicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PanicService implements IPanicService{
    @Autowired
    PanicRepository panicRepository;

    @Override
    public void save(Panic panic) {
        panicRepository.save(panic);
    }
}
