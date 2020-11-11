package com.epam.clothshop.repository;

import com.epam.clothshop.model.Vendor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorRepository extends CrudRepository<Vendor, Long> {
}
