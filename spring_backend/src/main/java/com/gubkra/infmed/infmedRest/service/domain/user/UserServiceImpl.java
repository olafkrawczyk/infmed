package com.gubkra.infmed.infmedRest.service.domain.user;

import com.gubkra.infmed.infmedRest.domain.User;
import com.gubkra.infmed.infmedRest.repository.UserRepository;
import com.gubkra.infmed.infmedRest.service.AbstractRepositoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * Created by Olaf on 2018-03-11.
 */

@Service
public class UserServiceImpl extends AbstractRepositoryService<User, UUID> implements UserService {
    @Autowired
    private void setRepository(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public User addItem(User user) {
        UUID uuid = UUID.randomUUID();
        user.setUuid(uuid);
        return this.repository.save(user);
    }

    @Override
    public Optional<User> findById(UUID uuid) {
        return this.repository.findById(uuid);
    }
}
