package com.ISSUberTim10.ISSUberTim10.ride.service.impl;

import com.ISSUberTim10.ISSUberTim10.ride.repository.RejectionRepository;
import com.ISSUberTim10.ISSUberTim10.ride.service.interfaces.IRejectionService;
import org.springframework.beans.factory.annotation.Autowired;

public class RejectionService implements IRejectionService {

    @Autowired
    RejectionRepository rejectionRepository;
}
