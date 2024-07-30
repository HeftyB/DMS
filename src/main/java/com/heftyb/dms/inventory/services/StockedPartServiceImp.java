package com.heftyb.dms.inventory.services;

import com.heftyb.dms.exceptions.DataNotFoundException;
import com.heftyb.dms.inventory.StockedPart;
import com.heftyb.dms.inventory.repositories.StockedPartRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "stockedPartService")
public class StockedPartServiceImp implements StockedPartService{

    private final StockedPartRepository stockRepo;
    private final PartService partService;

    public StockedPartServiceImp(final StockedPartRepository stockRepo,
                                 final PartService partService) {
        this.stockRepo = stockRepo;
        this.partService = partService;
    }

    @Override
    public List<StockedPart> findAll() {
        List<StockedPart> parts = new ArrayList<>();
        stockRepo.findAll().iterator().forEachRemaining(parts::add);
        return parts;
    }

    @Override
    public StockedPart findById(long id) {
        return stockRepo.findById(id).orElseThrow(
                ()-> new DataNotFoundException(String.format(
                        "StockedPartSService Error: can not find stocked part id %g", id
                ))
        );
    }

    @Override
    public StockedPart save(StockedPart stockedPart) {
        StockedPart s = new StockedPart();
        s.setPart(partService.findById(stockedPart.getPart().getId()));
        s.setReceived(stockedPart.getReceived());
        s.setInvoice(stockedPart.getInvoice());
        s.setSource(stockedPart.getSource());
        s.setQuantity(stockedPart.getQuantity());
        s.setUnitCost(stockedPart.getUnitCost());
        s.setAltBin(stockedPart.getAltBin());

        return stockRepo.save(s);
    }

    @Override
    public void delete(long id) {
        findById(id);
        stockRepo.deleteById(id);
    }
}
