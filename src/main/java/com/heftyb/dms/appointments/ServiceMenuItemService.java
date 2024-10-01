package com.heftyb.dms.appointments;

import java.util.List;

public interface ServiceMenuItemService {
    List<ServiceMenuItem> findAll();

    ServiceMenuItem findById(long id);

    ServiceMenuItem save(ServiceMenuItem item);

    void delete(long id);

}
