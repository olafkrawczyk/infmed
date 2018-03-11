package com.gubkra.infmed.infmedRest.repository;

import com.gubkra.infmed.infmedRest.domain.Address;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

/**
 * Created by Olaf on 2018-03-11.
 */
public interface AddressRepository extends CrudRepository<Address, Long> {
    Optional<Address> findById(Long id);
}
