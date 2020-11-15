package com.epam.clothshop.mapper;

import com.epam.clothshop.model.Vendor;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Mapper
public abstract class VendorMapper {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class VendorToSave {
        String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class VendorLite {
        Long id;
        String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @FieldDefaults(level = AccessLevel.PRIVATE)
    public static class VendorFull {
        Long id;
        String name;
        List<ProductMapper.ProductLite> products = new ArrayList<>();
    }

    public abstract VendorToSave mapToSave(Vendor vendor);

    public abstract Vendor mapToSave(VendorToSave vendorToSave);

    public abstract VendorLite mapLite(Vendor vendor);

    public abstract Vendor mapLite(VendorLite vendorLite);

    public abstract VendorFull mapFull(Vendor vendor);

    public abstract Vendor mapFull(VendorFull vendorFull);
}
