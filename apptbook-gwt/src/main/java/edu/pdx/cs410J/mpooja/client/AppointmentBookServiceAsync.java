package edu.pdx.cs410J.mpooja.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import edu.pdx.cs410J.AbstractAppointmentBook;

import java.util.List;
import java.util.Map;

/**
 * The client-side interface to the AppointmentBook service
 */
public interface AppointmentBookServiceAsync {

  /**
   * Creates Appointmentbook on the server
   * @param async async callback
   */
  void createAppointmentBook(AsyncCallback<AbstractAppointmentBook> async);

  /**
   * Adds appointment to appointmentbook on the server
   * @param owner ownername
   * @param appointment appointment to be added
   * @param async async callback
   */
  public void add(String owner ,Appointment appointment,AsyncCallback<Void> async);

  /**
   * Prints Appointmentbook with appointments
   * @param async async callback
   */
  public void print(AsyncCallback<Map<String, AppointmentBook>> async);

  /**
   * Search for appointment for particular owner between begintime and endtime
   * @param owner ownername
   * @param beginTime begintime
   * @param endTime endtime
   * @param async async callback
   */
  public void search(String owner, String beginTime,String endTime,AsyncCallback<List<Appointment>>async);



}
