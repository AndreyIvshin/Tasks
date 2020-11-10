package com.epam.clothshop.service;

import com.epam.clothshop.model.Vendor;
import com.epam.clothshop.repository.VendorRepository;
import org.springframework.stereotype.Service;

@Service
public class VendorService extends AbstractCrudRepositoryMirroringService<Vendor, Long, VendorRepository> {
}
