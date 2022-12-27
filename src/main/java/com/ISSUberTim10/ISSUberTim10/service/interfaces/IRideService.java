package com.ISSUberTim10.ISSUberTim10.service.interfaces;

import com.ISSUberTim10.ISSUberTim10.domain.Ride;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.Collection;

public interface IRideService {
    public Collection<Ride> getAll();

    public void createAll();

    public void deleteAll();

    public Page<Ride> getByUser(Long id, Pageable page);

}
