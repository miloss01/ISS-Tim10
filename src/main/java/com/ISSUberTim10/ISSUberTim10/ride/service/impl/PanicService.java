package com.ISSUberTim10.ISSUberTim10.ride.service.impl;

import com.ISSUberTim10.ISSUberTim10.ride.Panic;
import com.ISSUberTim10.ISSUberTim10.ride.repository.PanicRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IPanicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PanicService implements IPanicService {
    @Autowired
    PanicRepository panicRepository;

    @Override
    public Panic save(Panic panic) {
        return panicRepository.save(panic);
    }

    @Override
    public List<Panic> getAllPanics() {
        return panicRepository.findAll();
    }
}
