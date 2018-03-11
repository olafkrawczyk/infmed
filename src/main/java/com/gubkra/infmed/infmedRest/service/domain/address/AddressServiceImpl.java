package com.gubkra.infmed.infmedRest.service.domain.address;

import com.gubkra.infmed.infmedRest.domain.Address;
import com.gubkra.infmed.infmedRest.repository.AddressRepository;
import com.gubkra.infmed.infmedRest.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Created by Olaf on 2018-03-11.
 */
@Service
public class AddressServiceImpl extends AbstractRepositoryService<Address, Long> implements AddressService {

    @Autowired
    private void setRepository(AddressRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional findById(Long id) {
        return this.repository.findById(id);
    }
}
