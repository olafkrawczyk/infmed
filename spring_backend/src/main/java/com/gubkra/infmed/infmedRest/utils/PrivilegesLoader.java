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
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collection;

/**
 * Created by Olaf on 2018-03-18.
 */

@Component
public class PrivilegesLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(PrivilegesLoader.class);

    @Autowired
    private PrivilegeRepository privilegeRepository;

    @Autowired
    private RoleRepository roleRepository;

    private boolean refreshed = false;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
       if (refreshed)
           return;

       Privilege addPatient = createPrivilegeIfNotExists("ADD_PATIENT_PRIVILEGE");
       Privilege deletePatient = createPrivilegeIfNotExists("DELETE_PATIENT_PRIVILEGE");
       Privilege deleteMedicalRecord = createPrivilegeIfNotExists("DELETE_RECORD_PRIVILEGE");

       Privilege saveMedicalRecord = createPrivilegeIfNotExists("SAVE_RECORD_PRIVILEGE");

       Role doctorRole = createRoleIfNotExists("ROLE_DOCTOR", Arrays.asList(addPatient, deletePatient, deleteMedicalRecord, saveMedicalRecord));
       Role patientRole = createRoleIfNotExists("ROLE_PATIENT", Arrays.asList(saveMedicalRecord));

       refreshed = true;
    }

    private Privilege createPrivilegeIfNotExists(String name) {
        Privilege privilege = privilegeRepository.findByName(name);
        if (privilege == null) {
            privilege = new Privilege();
            privilege.setName(name);
            privilege = privilegeRepository.save(privilege);
        }
        return  privilege;
    }

    private Role createRoleIfNotExists(String name, Collection<Privilege> privileges) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role();
            role.setName(name);
            role.setPrivileges(privileges);
            role = roleRepository.save(role);
        }
        return  role;
    }


}
