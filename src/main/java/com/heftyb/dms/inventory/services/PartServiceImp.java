package com.heftyb.dms.inventory.services;

import com.heftyb.dms.account.po.services.PurchaseOrderService;
import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.inventory.Part;
import com.heftyb.dms.inventory.repositories.PartRepository;
import com.heftyb.dms.repairorder.services.RepairOrderService;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "partService")
public class PartServiceImp implements PartService {

    final private PartRepository partRepo;
    final private RepairOrderService repairOrderService;
    final private PurchaseOrderService purchaseOrderService;

    public PartServiceImp(final PartRepository partRepo,
                          final RepairOrderService repairOrderService,
                          final PurchaseOrderService purchaseOrderService) {
        this.partRepo = partRepo;
        this.repairOrderService = repairOrderService;
        this.purchaseOrderService = purchaseOrderService;
    }

    @Override
    public List<Part> findAll() {
        List<Part> parts = new ArrayList<>();
        partRepo.findAll().iterator().forEachRemaining(parts::add);
        return parts;
    }

    @Override
    public Part findById(long id) {
        return partRepo.findById(id).orElseThrow(
                () -> new DataNotFoundException(String.format(
                        "PartService Error: could not find Part id %g", id
                ))
        );
    }

    @Override
    public Part save(Part part) {
        Part p = new Part();
        p.setSource(part.getSource());
        p.setPartNumber(part.getPartNumber());
        p.setOEMPartNumber(part.getOEMPartNumber());
        p.setDescription(part.getDescription());
        p.setBin(part.getBin());
        p.setInStock(part.isInStock());
        p.setQty(part.getQty());
        p.setReceivedStock(new ArrayList<>());

        p.setCost(part.getCost());
        p.setPrice(part.getPrice());
        p.setMarkup(part.getMarkup());
        p.setJobs(part.getJobs());

        return partRepo.save(p);
    }

    @Override
    public void delete(long id) {
        findById(id);
        partRepo.deleteById(id);
    }
}
