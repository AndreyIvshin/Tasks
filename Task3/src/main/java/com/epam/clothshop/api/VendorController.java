package com.epam.clothshop.api;

import com.epam.clothshop.mapper.VendorMapper;
import com.epam.clothshop.model.Product;
import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.service.ProductService;
import com.epam.clothshop.service.VendorService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RestController
@RequestMapping("api/vendors")
@PreAuthorize("hasAuthority('WRITE')")
@Api(tags = "vendor")
public class VendorController {

    @Autowired
    private VendorService vendorService;
    @Autowired
    private ProductService productService;
    @Autowired
    private VendorMapper vendorMapper;

    @GetMapping
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
    @Operation(summary = "Get all vendors")
    public List<VendorMapper.VendorLite> getVendors() {
        return vendorService.findAll().stream().map(vendorMapper::mapLite).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    @PreAuthorize("hasAuthority('READ') or hasAuthority('WRITE')")
    @Operation(summary = "Get vendor")
    public ResponseEntity<VendorMapper.VendorFull> getVendor(@PathVariable Long id) {
        return vendorService.findById(id).map(product -> ResponseEntity.ok(vendorMapper.mapFull(product)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create vendor")
    public ResponseEntity postVendor(@RequestBody VendorMapper.VendorToSave vendorToSave) {
        vendorService.save(vendorMapper.mapToSave(vendorToSave));
        return ResponseEntity.ok().build();
    }

    @PutMapping("{id}")
    @Operation(summary = "Modify vendor")
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
    @Operation(summary = "Remove vendor")
    public ResponseEntity deleteVendor(@PathVariable Long id) {
        if (vendorService.findById(id).isPresent()) {
            vendorService.deleteById(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("{id}/products/{iid}")
    @Operation(summary = "Add product to vendor")
    public ResponseEntity putVendorProduct(@PathVariable Long id, @PathVariable Long iid) {
        Optional<Vendor> vendorOptional = vendorService.findById(id);
        if (vendorOptional.isPresent()) {
            Optional<Product> productOptional = productService.findById(iid);
            if (productOptional.isPresent()) {
                vendorOptional.get().getProducts().add(productOptional.get());
                vendorService.save(vendorOptional.get());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("{id}/products/{iid}")
    @Operation(summary = "Delete product from vendor")
    public ResponseEntity deleteVendorProduct(@PathVariable Long id, @PathVariable Long iid) {
        Optional<Vendor> vendorOptional = vendorService.findById(id);
        if (vendorOptional.isPresent()) {
            Optional<Product> productOptional = productService.findById(iid);
            if (productOptional.isPresent()) {
                vendorOptional.get().getProducts().remove(productOptional.get());
                vendorService.save(vendorOptional.get());
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
