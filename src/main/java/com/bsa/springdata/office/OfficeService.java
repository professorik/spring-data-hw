package com.bsa.springdata.office;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OfficeService {
    @Autowired
    private OfficeRepository officeRepository;

    public List<OfficeDto> getByTechnology(String technology) {
        return officeRepository
                .getOfficesByTechnology(technology)
                .stream()
                .map(OfficeDto::fromEntity)
                .collect(Collectors.toList());
    }

    public Optional<Office> updateAddress(String oldAddress, String newAddress) {
        officeRepository.updateAddressIfExist(oldAddress, newAddress);
        return officeRepository.getAllOfficeByAddress(newAddress);
    }
}
