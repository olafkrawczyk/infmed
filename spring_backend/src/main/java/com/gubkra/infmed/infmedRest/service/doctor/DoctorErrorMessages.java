package com.gubkra.infmed.infmedRest.service.doctor;

public class DoctorErrorMessages {
    public static final String INVALID_DOCTOR_ID = "Could not find doctor with given UUID";
    public static final String INVALID_DOCTOR_ROLE = "User given as doctor does not have patient doctor privileges";
    public static final String INVALID_PATIENT_ROLE = "User given as patient does not have patient privileges";
    public static final String INVALID_PATIENT_ID = "Could not find patient with given UUID";
    public static final String INVALID_PATIENT_USERNAME = "Could not find patient with given username";
    public static final String PATIENT_ALREADY_ASSIGNED = "Patient already registered to this doctor";
    public static final String PATIENT_ISNT_ASSIGNED = "Patient is not assigned to this doctor";

}
