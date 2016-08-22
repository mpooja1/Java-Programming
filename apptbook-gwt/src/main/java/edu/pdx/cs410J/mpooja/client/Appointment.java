package edu.pdx.cs410J.mpooja.client;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import edu.pdx.cs410J.AbstractAppointment;

import java.util.Date;

/**
 * Used to create appointment with details including description , begin time and end time.
 * has description for storing description for appointment
 * has begintime for storing begin time for appointment
 * has endtime for storing end time for appointment
 */
public class Appointment extends AbstractAppointment implements Comparable<Appointment>
{
    String description;
    public Date beginTime;
    public Date endTime;
    private boolean flag;

    /**
     * Constructor for the Appointment class. Holds all relavent data for a particular Appointment
     * @param description The phone number of the customer
     * @param beginTime The time at which the Appointment began
     * @param endTime The time at which the Appointment ended
     */
    public Appointment(String description, String beginTime, String endTime){
        flag = false;
        //Check for bad data
        try{
            if(beginTime.contains("\"")||endTime.contains("\""))
                throw new IllegalArgumentException("Date and time cannot contain quotes ");

            if(description.trim().isEmpty())
                throw new IllegalArgumentException("Description must not be empty");

            String[] tempStart = beginTime.split(" ");
            String[] tempEnd= endTime.split(" ");
            if(!tempStart[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")||!tempEnd[0].matches("(0?[1-9]|1[012])/(0?[1-9]|[12][0-9]|3[01])/((19|20)\\d\\d)")) {
                throw new IllegalArgumentException("Date format must follow mm/dd/yyyy");
            }

            if(!tempStart[1].matches("(1[0-2]|0?[1-9]):([0-5]?[0-9])")||!tempEnd[1].matches("(1[0-2]|0?[1-9]):([0-5]?[0-9])"))
                throw new IllegalArgumentException("Time format must follow mm:hh (12 hour time)");
            if(!tempStart[2].matches("(am|pm|AM|PM)")&&!tempEnd[2].matches("(am|pm|AM|PM)"))
                throw new IllegalArgumentException("Time must include am/pm");
        }
        catch(IllegalArgumentException ex) {
            Window.alert(ex.getMessage());
            flag = true;
            return;
        }
        this.description = description;
        setDate(beginTime,endTime);
    }

    /**
     * This is an empty constructor for initialising description , begin time and end time to null
     */
    public Appointment(){
        flag = false;
        try {
            description = "";
            beginTime =null;
            endTime = null;
        }
        catch(Exception ex){
            Window.alert(ex.getMessage());
            flag = true;
            return;
        }
    }

    /**
     * This method is used to set begin time and end time in proper format
     * @param start begin time
     * @param end end time
     */


    public void setDate(String start, String end){
        flag = false;
        DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
        DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a",formatInfo){};
        try {
            this.beginTime=ShortDateFormat.parse(start);
            this.endTime = ShortDateFormat.parse(end);
        }
        catch(Exception ex){
            Window.alert("Error Parsing the time, please enter valid time, dont forget to include am/pm " + ex.getMessage());
            flag = true;
            return;
        }
    }

    /**
     * This method returns true/false if exception was thrown
     * @return flag - true /false according to exception was thrown or not
     */

    public boolean exceptionWasThrown(){
        return flag;
    }

    /**
     * This method is used to get Description for appointment
     * @return Returns description of appointment
     */
    @Override
    public String getDescription() {
        return description;
    }


    /**
     * This method  is used to get begin time for appointment
     * @return Returns beginTime of appointment
     */
    @Override
    public String getBeginTimeString() {
        DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
        DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a",formatInfo){};
        if(beginTime != null)
            return (ShortDateFormat.format(beginTime));
        else
            return "";
    }

    /**
     * This method is usedto get end time for appointment
     * @return Returns endTime of appointment
     */
    @Override
    public String getEndTimeString() {
        DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
        DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a",formatInfo){};
        if(endTime != null)
            return (ShortDateFormat.format(endTime));
        else
            return "";
    }

    /**
     * This method returns begin time for appointment
     * @return begin time for appointment
     */
    @Override
    public Date getBeginTime() {
      return this.beginTime;
    }

    /**
     * This method is used to return end time for appointment
     * @return endtime for appointment
     */
    @Override
    public Date getEndTime() {
        return this.endTime;
    }

    /**
     * This methud is used to calculate duration of appointment
     * @return duration of appointment
     */
    public String duration(){
        long duration =beginTime.getTime()-endTime.getTime();
        long diffMinutes = duration / (60 * 1000);
        long diffHours = duration / (60 * 60 * 1000);

        return String.valueOf(-1*diffMinutes);

    }

    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * <p>
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     * <p>
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     * <p>
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     * <p>
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     * <p>
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param appt the object to be compared.
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException   if the specified object's type prevents it
     *                              from being compared to this object.
     */

    @Override
    public int compareTo(Appointment appt) {
        long diffBegTime, diffEndTime = 0;
        int diffdes = 0;
        diffdes = this.getDescription().compareTo(appt.getDescription());
        diffBegTime = this.getBeginTime().compareTo(appt.getBeginTime());
        diffEndTime = this.getEndTime().compareTo(appt.getEndTime());
        if (diffBegTime > 0) {
            return 1;
        }
        if (diffBegTime < 0) {
            return -1;
        }
        if (diffBegTime == 0) {
            if (diffEndTime > 0) {
                return 1;
            }
            if (diffEndTime < 0) {
                return -1;
            }
            if (diffEndTime == 0) {
                if (diffdes > 0) {
                    return 1;
                }
                if (diffdes < 0) {
                    return -1;
                }
            }
        }
        return 0;}


}

