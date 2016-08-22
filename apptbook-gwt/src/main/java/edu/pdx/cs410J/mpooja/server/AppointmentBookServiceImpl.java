package edu.pdx.cs410J.mpooja.server;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import edu.pdx.cs410J.mpooja.client.Appointment;
import edu.pdx.cs410J.mpooja.client.AppointmentBook;
import edu.pdx.cs410J.mpooja.client.AppointmentBookService;

import java.util.*;

/**
 * The server-side implementation of the division service
 */
public class AppointmentBookServiceImpl extends RemoteServiceServlet implements AppointmentBookService
{
  private final Map<String, AppointmentBook> data = new HashMap<String, AppointmentBook>();
  AppointmentBook book;

  /**
   * This method is used to create appointment book
   * @return appointment book that is created
   */
  @Override
  public AppointmentBook createAppointmentBook() {
    AppointmentBook appointmentBook = new AppointmentBook();
    appointmentBook.addAppointment(new Appointment());
    return appointmentBook;
  }

  /**
   * This method is used to add appointment to appointmentbook
   * @param owner OwnerName
   * @param appointment appointment to be added
   */
  @Override
  public void add(String owner, Appointment appointment) {
    if(data.get(owner)!=null){
      book=data.get(owner);
      book.addAppointment(appointment);
      data.put(owner, book);
    }
    else{
      book= new AppointmentBook(owner,appointment);
      data.put(owner, book);
    }
  }

  /**
   * This method is used to return data needed for printing appointment book
   * @return Map for OwnerName and Appointment Book needed for printing
   */
  @Override
  public Map<String, AppointmentBook> print() {
    return data;
  }

  /**
   * This method is used to search for appointments for owner between specified begin time and end time
   * @param owner Ownername
   * @param beginTime beginTime
   * @param endTime endTime
   * @return list of appointments - that are searched
   */
  @Override
  public List<Appointment> search(String owner, String beginTime,String endTime) {
    DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
    DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a",formatInfo){};
    Long begin,end;
    try {
      Date search = ShortDateFormat.parse(beginTime.trim());
      begin = search.getTime();
      Date search1 = ShortDateFormat.parse(endTime.trim());
      end = search1.getTime();
    }
    catch(Exception ex){
      System.out.println("Error Parsing "+ ex);
      return null;
    }
    List<Appointment> data = new ArrayList<Appointment>();
    if(this.data.isEmpty())
      return null;

    book = this.data.get(owner);
    if(book == null)
      return null;
    Collection<Appointment>appnts = this.data.get(owner).getAppointments();

    for(Appointment appnt: appnts){
      if(begin<= (Long)appnt.beginTime.getTime()&&begin<=(Long)appnt.endTime.getTime()) {
        if (end >= (Long) appnt.beginTime.getTime() && end >= (Long) appnt.endTime.getTime()) {
          data.add(appnt);
        }
      }
    }
    return data;
  }

  /**
   * This mehtod is used to print or inform about unhandeled exception
   * @param unhandled Throwable data
   */
    @Override
    protected void doUnexpectedFailure(Throwable unhandled) {
        unhandled.printStackTrace(System.err);
        super.doUnexpectedFailure(unhandled);
    }
}