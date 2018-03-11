package com.gubkra.infmed.infmedRest.service.domain.address;

import com.gubkra.infmed.infmedRest.domain.Address;
import com.gubkra.infmed.infmedRest.service.CrudService;

import java.util.Optional;

/**
 * Created by Olaf on 2018-03-11.
 */
public interface AddressService extends CrudService<Address>{
    Optional<Address> findById(Long Id);
}
