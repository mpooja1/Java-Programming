package edu.pdx.cs410J.mpooja.client;

import edu.pdx.cs410J.AbstractAppointment;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.mpooja.client.Appointment;

import java.security.acl.Owner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;


/**
 * Used to create appointment book for specified owner including appointment details.
 * has owner for storing owner name
 * has list for storing appointment details
 * has searchAppointmentOnly to specify for searching appointment
 * has singleAppointment to add appointment
 */
public class AppointmentBook extends AbstractAppointmentBook {

    String owner;
    ArrayList<Appointment> appointments;
    Appointment searchCallOnly;
    Appointment singlePhoneCall;

    /**
     * Used to initialize private data members
     * @param owner ownerName
     * @param appointment appointment details to be added to list
     */
    public AppointmentBook(String owner, Appointment appointment)
    {
        this.owner = owner;
        appointments = new ArrayList<Appointment>();
        addAppointment(appointment);
        searchCallOnly=null;
        singlePhoneCall = null;
    }

    /**
     * Used to initialize private data members
     * @param owner ownerName
     */
    public AppointmentBook(String owner)
    {
        this.owner = owner;
        appointments = new ArrayList<Appointment>();
        searchCallOnly=null;
        singlePhoneCall = null;
    }


    /**
     * Used to initialize private data members as null
     */
    public AppointmentBook()
    {
        //Create an empty phonebill
        owner = "";
        appointments = new ArrayList<Appointment>();
        searchCallOnly=null;
        singlePhoneCall = null;
    }

    /**
     * Used to inintialize private data members
     * @param owner ownerName
     * @param tempAppointment Appointment to be added or searched
     * @param s to specify whether the appointment is for search or appointment to be added
     */
    public AppointmentBook(String owner, Appointment tempAppointment, String s) {
        if(s.equals("-search")) {
            this.owner = owner;
            appointments = null;
            searchCallOnly = tempAppointment;
        }
        if(s.equals("-single")) {
            this.owner = owner;
            appointments = null;
            singlePhoneCall = tempAppointment;
        }
    }

    /**
     * Used to get owner name
     * @return owner - owner name
     */
    @Override
    public String getOwnerName() {
        return owner;
    }


    /**
     * Used to add appointments created into list
     * @param abstractAppointment - appointment details
     */
    @Override
    public void addAppointment(AbstractAppointment abstractAppointment) {
       /* boolean addPhoneCall = true;
        for(AbstractAppointment call:appointments){
            if(call.toString().equals(abstractAppointment.toString())) {
                addPhoneCall = false;
            }
        }
        if(addPhoneCall) {
            appointments.add((Appointment) abstractAppointment);
        }*/
        appointments.add((Appointment) abstractAppointment);
        Collections.sort(appointments);
    }

    /**
     * Used to get appointments created
     * @return list - list of appointments
     */
    @Override
    public Collection getAppointments() {
        return appointments;
    }

    /**
     * This method is used to check if any exception was thrown
     * @return true/false according to exception was thrown or not
     */
    public boolean exceptionWasThrown(){
        for(Appointment appointment: appointments){
            if(appointment.exceptionWasThrown())
                return true;
        }
        return false;
    }
}