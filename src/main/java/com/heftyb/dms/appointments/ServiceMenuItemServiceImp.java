package com.heftyb.dms.appointments;

import com.heftyb.dms.exceptions.DataNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "serviceMenuItemService")
public class ServiceMenuItemServiceImp implements ServiceMenuItemService{

    private final ServiceMenuItemRepository itemRepo;

    public ServiceMenuItemServiceImp(final  ServiceMenuItemRepository itemRepo) {
        this.itemRepo = itemRepo;
    }

    @Override
    public List<ServiceMenuItem> findAll() {
        List<ServiceMenuItem> items = new ArrayList<>();
        itemRepo.findAll().iterator().forEachRemaining(items::add);
        return items;
    }

    @Override
    public ServiceMenuItem findById(long id) {
        return itemRepo.findById(id).orElseThrow(() -> new DataNotFoundException(
                String.format("Could not find ServiceMenuItem id: %s", id)
        ));
    }

    @Override
    public ServiceMenuItem save(ServiceMenuItem item) {
        ServiceMenuItem i;

        if (item.getId() != 0) i = findById(item.getId());
        else i = new ServiceMenuItem();

        i.setItem(item.getItem());
        i.setBlocks(item.getBlocks());
        i.setPrice(item.getPrice());
        i.setDescription(item.getDescription());
        i.setSpecial(item.isSpecial());
        i.setImgUrl(item.getImgUrl());

        return itemRepo.save(i);
    }

    @Override
    public void delete(long id) {
        findById(id);
        itemRepo.deleteById(id);
    }
}
