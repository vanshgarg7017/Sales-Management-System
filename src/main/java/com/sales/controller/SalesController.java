package com.sales.controller;

import com.sales.model.Sale;
import com.sales.service.SalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/sales")
public class SalesController {

    @Autowired
    private SalesService salesService;

    // Create a new sale
    @PostMapping
    public ResponseEntity<Sale> createSale(@RequestBody Sale sale) {
        Sale savedSale = salesService.saveSale(sale);
        return new ResponseEntity<>(savedSale, HttpStatus.CREATED);
    }

    // Get a sale by ID
    @GetMapping("/{id}")
    public ResponseEntity<Sale> getSaleById(@PathVariable("id") Long id) {
        Optional<Sale> sale = salesService.getSaleById(id);
        return sale.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Get all sales
    @GetMapping
    public ResponseEntity<List<Sale>> getAllSales() {
        List<Sale> sales = salesService.getAllSales();
        return ResponseEntity.ok(sales);
    }

    // Update a sale
    @PutMapping("/{id}")
    public ResponseEntity<Sale> updateSale(@PathVariable("id") Long id, @RequestBody Sale sale) {
        Sale updatedSale = salesService.updateSale(id, sale);
        return updatedSale != null ? ResponseEntity.ok(updatedSale)
                : ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    // Delete a sale
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSale(@PathVariable("id") Long id) {
        salesService.deleteSale(id);
        return ResponseEntity.noContent().build();
    }
}
