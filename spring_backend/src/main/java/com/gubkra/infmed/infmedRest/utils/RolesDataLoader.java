package com.gubkra.infmed.infmedRest.utils;

import com.gubkra.infmed.infmedRest.domain.Privilege;
import com.gubkra.infmed.infmedRest.domain.Role;
import com.gubkra.infmed.infmedRest.repository.PrivilegeRepository;
import com.gubkra.infmed.infmedRest.repository.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

@Order(1)
@Component
public class RolesDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger logger = LoggerFactory.getLogger(RolesDataLoader.class);

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    private boolean refreshed = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        logger.info("Roles data loader invoked...");

        if (refreshed)
            return;

        Privilege addPatient = createPrivilegeIfNotExists(SecurityConstants.ADD_PATIENT_P);
        Privilege deletePatient = createPrivilegeIfNotExists(SecurityConstants.DELETE_PATIENT_P);
        Privilege deleteMedicalRecord = createPrivilegeIfNotExists(SecurityConstants.DELETE_RECORD_P);
        Privilege saveMedicalRecord = createPrivilegeIfNotExists(SecurityConstants.SAVE_RECORD_P);

        Role doctorRole = createRoleIfNotExists(SecurityConstants.ROLE_DOCTOR,
                Arrays.asList(addPatient, deletePatient, deleteMedicalRecord, saveMedicalRecord));
        Role patientRole = createRoleIfNotExists(SecurityConstants.ROLE_PATIENT, Arrays.asList(saveMedicalRecord));

        refreshed = true;
    }

    private Privilege createPrivilegeIfNotExists(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilege = privilegeRepository.save(privilege);
        }
        return privilege;
    }

    private Role createRoleIfNotExists(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            role = roleRepository.save(role);
        }
        return role;
    }
}
