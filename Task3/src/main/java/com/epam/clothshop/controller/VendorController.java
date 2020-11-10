package com.epam.clothshop.controller;

import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @GetMapping("/")
    public ResponseEntity<List<Vendor>> getVendors() {
        return ResponseEntity.ok(((List<Vendor>) vendorService.findAll()));
    }

    @PostMapping("/")
    public ResponseEntity postVendor(@RequestBody Vendor vendor) {
        vendorService.save(vendor);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vendor> getVendor(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.findById(id).get());
    }

    @PutMapping("/{id}")
    public ResponseEntity putVendor(@PathVariable Long id, @RequestBody Vendor vendor) {
        Vendor old = vendorService.findById(id).get();
        vendor.setId(old.getId());
        vendorService.save(vendor);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteVendor(@PathVariable Long id) {
        vendorService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/products")
    public ResponseEntity<List<Product>> getVendorProducts(@PathVariable Long id) {
        return ResponseEntity.ok(vendorService.findById(id).get().getProducts());
    }

    @PostMapping("/{id}/products")
    public ResponseEntity postVendorProducts(@PathVariable Long id, @RequestBody Product product) {
        Vendor vendor = vendorService.findById(id).get();
        vendor.getProducts().add(product);
        vendorService.save(vendor);
        return ResponseEntity.ok().build();
    }

}
