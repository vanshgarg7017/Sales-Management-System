package com.sales.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.sales.model.Sale;

import com.sales.repository.SaleRepository;

@Service
public class SalesService {
    @Autowired
    private SaleRepository saleRepository;

    public Sale saveSale(Sale sale) {
        return saleRepository.save(sale);
    }

    public Optional<Sale> getSaleById(Long id) {
        return saleRepository.findById(id);
    }

    public List<Sale> getAllSales() {
        return saleRepository.findAll();
    }

    public void deleteSale(Long id) {
        saleRepository.deleteById(id);
    }

    public Sale updateSale(Long id, Sale sale) {
        if (saleRepository.existsById(id)) {
            sale.setId(id);
            return saleRepository.save(sale);
        }
        return null;
    }

}
