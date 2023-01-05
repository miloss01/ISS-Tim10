package com.ISSUberTim10.ISSUberTim10.ride.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.ride.Panic;

import java.util.List;

public interface IPanicService {
    public Panic save(Panic panic);
    public List<Panic> getAllPanics();
}
