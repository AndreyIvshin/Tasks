package com.epam.clothshop.api;

import com.epam.clothshop.mapper.VendorMapper;
import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/vendors")
public class VendorController {

    @Autowired
    private VendorService vendorService;
    @Autowired
    private VendorMapper vendorMapper;

    @GetMapping
    public List<VendorMapper.VendorLite> getVendors() {
        return vendorService.findAll().stream().map(vendorMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public ResponseEntity<VendorMapper.VendorFull> getVendor(@PathVariable Long id) {
        return vendorService.findById(id).map(product -> ResponseEntity.ok(vendorMapper.mapFull(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity postVendor(@RequestBody VendorMapper.VendorToSave vendorToSave) {
        vendorService.save(vendorMapper.mapToSave(vendorToSave));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    public ResponseEntity putVendor(@PathVariable Long id, @RequestBody VendorMapper.VendorToSave vendorToSave) {
        if (vendorService.findById(id).isPresent()) {
            Vendor vendor = vendorMapper.mapToSave(vendorToSave);
            vendor.setId(id);
            vendorService.save(vendor);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteVendor(@PathVariable Long id) {
        if (vendorService.findById(id).isPresent()) {
            vendorService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
