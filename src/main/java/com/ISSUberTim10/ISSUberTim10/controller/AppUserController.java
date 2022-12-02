package com.ISSUberTim10.ISSUberTim10.controller;

import com.ISSUberTim10.ISSUberTim10.domain.AppUser;
import com.ISSUberTim10.ISSUberTim10.service.interfaces.IAppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping("/api/v2/appUser")
public class AppUserController {

    @Autowired
    IAppUserService service;

    @GetMapping
    public Collection<AppUser> getAll() {
        return service.getAll();
    }

    @PostMapping
    public void createAll() {
        service.createAll();
    }

    @DeleteMapping
    public void deleteAll() {
        service.deleteAll();
    }


}
