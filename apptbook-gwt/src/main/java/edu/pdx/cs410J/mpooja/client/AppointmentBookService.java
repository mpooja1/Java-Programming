package edu.pdx.cs410J.mpooja.client;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import com.google.gwt.user.client.rpc.core.java.lang.String_CustomFieldSerializer;

import java.util.List;
import java.util.Map;

/**
 * A GWT remote service that returns/adds/search/prints a appointment book
 */
@RemoteServiceRelativePath("appointments")
public interface AppointmentBookService extends RemoteService {

  /**
   * This method is used to create appointmentbook
   * @return Appointmentbook created.
   */
  public AppointmentBook createAppointmentBook();

  /**
   * This method is used to add appointment created to appointment book of specified owner
   * @param owner OwnerName
   * @param appointment appointment tobe added
   */
  public void add(String owner ,Appointment appointment);

  /**
   * This method is used to return data needed for printing appointment book
   * @return Map for OwnerName and Appointment Book needed for printing
   */
  public Map<String,AppointmentBook> print();

  /**
   * This method is used to search for appointments foespecified owner between begin time and end time
   * @param owner Ownername
   * @param beginTime beginTime
   * @param endTime endTime
   * @return list of appointments searched.
   */
  public List<Appointment> search(String owner, String beginTime , String endTime);

}
