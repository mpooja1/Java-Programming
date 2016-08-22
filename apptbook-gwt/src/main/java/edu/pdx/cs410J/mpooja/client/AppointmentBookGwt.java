package edu.pdx.cs410J.mpooja.client;

import com.google.common.annotations.VisibleForTesting;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.DefaultDateTimeFormatInfo;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.sun.prism.shader.Solid_TextureYV12_AlphaTest_Loader;
import edu.pdx.cs410J.AbstractAppointmentBook;
import edu.pdx.cs410J.AbstractAppointment;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * A basic GWT class that makes sure that we can send an appointment book back from the server
 */
public class AppointmentBookGwt implements EntryPoint {

  /**
   * This method is used to create UI for carrying out functionalities related to appointment book
   */
  public void onModuleLoad() {

    Button buttonPingServer = new Button("Ping Server");
    Button buttonCreateAppointmentBook = new Button("Create AppointmentBook");
    Button buttonAddCall = new Button("Add Appointment");
    Button buttonPrettyPrintAllCalls = new Button("Display All Appointments");
    Button buttonSearch = new Button("Search Appointment");
    Button buttonHelp = new Button("Help");

    //Text box~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    final TextArea textBoxResults = new TextArea();
    textBoxResults.setReadOnly(true);
    textBoxResults.setSize("800px","200px");

    //Vertical Panels~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    VerticalPanel panelHeader = new VerticalPanel();
    panelHeader.add(new Label());
    panelHeader.add(textBoxResults);

    //Adding a Owner
    VerticalPanel panelAddNewCustomer = new VerticalPanel();
    panelAddNewCustomer.add(new Label("Owner Name:"));
    final TextBox textBoxOwnerName = new TextBox();
    panelAddNewCustomer.add(textBoxOwnerName);
;

    panelAddNewCustomer.add(new Label("Description:"));
    final TextBox textBoxDexcription = new TextBox();
    panelAddNewCustomer.add(textBoxDexcription);

    panelAddNewCustomer.add(new Label("Begin Time(MM/dd/yyyy HH:mm am/pm (12 Hour format):"));
    final TextBox textBoxStartTime = new TextBox();
    panelAddNewCustomer.add(textBoxStartTime);

    panelAddNewCustomer.add(new Label("End Time MM/dd/yyyy HH:mm am/pm (12 Hour format):"));
    final TextBox textBoxEndTime = new TextBox();
    panelAddNewCustomer.add(textBoxEndTime);

    //add all the buttons to the root panel ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    RootPanel rootPanel = RootPanel.get();
    rootPanel.add(buttonAddCall,50,320);
    rootPanel.add(buttonCreateAppointmentBook,200,320);
    rootPanel.add(buttonPrettyPrintAllCalls,400,320);
    rootPanel.add(buttonSearch,600,320);
    //rootPanel.add(buttonPingServer,525,50);
    rootPanel.add(buttonHelp,780,320);
    rootPanel.add(panelAddNewCustomer, 50, 80);

    Label l1 = new Label("You can see your Appointment details below -");
    rootPanel.add(l1,50,370);

    Label l2 = new Label("If you wish to create/add/search/display appointment in appointment book. \n Please choose the following options by clicking on button  -");
    rootPanel.add(l2,50,270);

    Label l3 = new Label("Welcome... Please enter following details and create/add/search/display your own appointment book -");
    rootPanel.add(l3,50,50);

    //add Vertical Panels to the root panel~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    rootPanel.add(panelHeader, 50, 400);

    //Define all the addClickHandlers here~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    //Ping
    buttonPingServer.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent clickEvent) {
        AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
        async.createAppointmentBook(new AsyncCallback<AbstractAppointmentBook>() {

          public void onFailure(Throwable ex) {
            Window.alert(ex.toString());
          }

          public void onSuccess(AbstractAppointmentBook AppointmentBook) {
            StringBuilder sb = new StringBuilder(AppointmentBook.toString());
            Collection<AbstractAppointment> calls = AppointmentBook.getAppointments();
            for (AbstractAppointment call : calls) {
              sb.append(call);
              sb.append("\n");
            }
            Window.alert(sb.toString());
          }
        });
      }
    });

    //Help Readme
    buttonHelp.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        Window.alert("This is the README for the AppointmentBook RIA application! PROJECT 5 - POOJA MANE\n" +
                "Please enter the appropriate information for a owner. Including the name," +
                "description, and a begin and end time for the Appointment\n" +
                "You may save owners AppointmentBooks here, search them by begin time and endtime or " +
                "display all AppointmentBooks");
      }
    });

    //CreateAppointmentBook
    buttonCreateAppointmentBook.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        try {
          if(textBoxOwnerName.getValue() == ""){
            Window.alert("Please provide correct arguments for owner name");
            return;
          }
          if(textBoxDexcription.getValue() == ""){
            Window.alert("Please provide correct arguments for dscription");
            return;
          }

          if(textBoxStartTime.getValue() == ""){
            Window.alert("Please provide correct arguments for start time");
            return;
          }
          if(textBoxEndTime.getValue() == ""){
            Window.alert("Please provide correct arguments for end Time");
            return;
          }
          final Appointment tempAppointment = new Appointment(textBoxDexcription.getValue().trim(), textBoxStartTime.getValue().trim(), textBoxEndTime.getValue().trim());
          if(tempAppointment.exceptionWasThrown()){
            return;
          }
          AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
          async.add(textBoxOwnerName.getValue(),tempAppointment , new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
              Window.alert(throwable.getMessage());
              throwable.printStackTrace();
              throwable.getStackTrace();
            }
            @Override
            public void onSuccess(Void aVoid) {
              textBoxResults.setValue(textBoxOwnerName.getValue()+" has added a new Appointment: "+tempAppointment.toString() );
            }
          });
        }
        catch(Exception ex){
          //Not enough args or something
          Window.alert("Please provide correct arguments"+ex.getMessage());
          return;
        }

      }
    });

    //Add a Appointment
    buttonAddCall.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        try {
          if(textBoxOwnerName.getValue() == ""){
            Window.alert("Please provide correct arguments for owner name");
            return;
          }
          if(textBoxDexcription.getValue() == ""){
            Window.alert("Please provide correct arguments for description");
            return;
          }

          if(textBoxStartTime.getValue() == ""){
            Window.alert("Please provide correct arguments for start time");
            return;
          }
          if(textBoxEndTime.getValue() == ""){
            Window.alert("Please provide correct arguments for end Time");
            return;
          }
          final Appointment tempAppointment = new Appointment(textBoxDexcription.getValue(), textBoxStartTime.getValue(), textBoxEndTime.getValue());
          if(tempAppointment.exceptionWasThrown()){
            return;
          }
          AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
          async.add(textBoxOwnerName.getValue().trim(),tempAppointment , new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable throwable) {
              Window.alert(throwable.getMessage());
            }

            @Override
            public void onSuccess(Void aVoid) {
              textBoxResults.setValue(textBoxOwnerName.getValue()+" has added a new Appointment: "+tempAppointment.toString() );
            }
          });
        }
        catch(Exception ex){
          //Not enough args or something
          Window.alert("Please provide correct arguments"+ex.getMessage());
          return;
        }

      }
    });
    //pretty Print
    buttonPrettyPrintAllCalls.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
        async.print(new AsyncCallback<Map<String, AppointmentBook>>() {

          @Override
          public void onFailure(Throwable throwable) {
            Window.alert(throwable.getMessage());
          }

          @Override
          public void onSuccess(Map<String, AppointmentBook> stringAppointmentBookMap) {
            String prettyCalls = "";
            //pretty print it
            if (stringAppointmentBookMap == null || stringAppointmentBookMap.isEmpty()) {
              Window.alert("No AppointmentBooks to show");
              return;
            }
            for (String customer : stringAppointmentBookMap.keySet()) {
              Collection calls = stringAppointmentBookMap.get(customer).getAppointments();
              prettyCalls+=prettyPrint((List<Appointment>) calls,customer)+"\n";
            }
            textBoxResults.setValue("#   OwnerName  Description     Start Time          End Time              Duration \n"+prettyCalls);
          }
        });
      }
    });
    //Search for user
    buttonSearch.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(ClickEvent clickEvent) {
        if(textBoxOwnerName.getValue() == ""){
          Window.alert("Please provide correct arguments for OWNER name");
          return;
        }
        if(textBoxStartTime.getValue() == ""){
          Window.alert("Please provide correct arguments for begin time");
          return;
        }
        if(textBoxEndTime.getValue() == ""){
          Window.alert("Please provide correct arguments for end time");
          return;
        }

        DefaultDateTimeFormatInfo formatInfo = new DefaultDateTimeFormatInfo();
        DateTimeFormat ShortDateFormat = new DateTimeFormat("MM/dd/yyyy hh:mm a",formatInfo){};
        Long begin,end;
        try {
          Date search = ShortDateFormat.parse(textBoxStartTime.getValue().trim());
          Date search1 = ShortDateFormat.parse(textBoxEndTime.getValue().trim());
        }
        catch(Exception ex){
          //System.out.println("Error Parsing "+ ex);
          Window.alert("Enter dates in valid format");
          return;

        }
        //Search for appointment
        AppointmentBookServiceAsync async = GWT.create(AppointmentBookService.class);
        async.search(textBoxOwnerName.getValue(), textBoxStartTime.getValue(), textBoxEndTime.getValue(),new AsyncCallback<List<Appointment>>() {
          @Override
          public void onFailure(Throwable throwable) {
            Window.alert("error "+throwable.getMessage());
          }

          @Override
          public void onSuccess(List<Appointment> Appointments) {
            if(Appointments==null||Appointments.isEmpty()){
              Window.alert("No Appointments matching under "+textBoxOwnerName.getValue()+"\n or values not entered in proper format.. Something wrong .. Please try again");
              return;
            }
            //pretty print the search calls
            textBoxResults.setValue("#   OwnerName   Description        Start Time          End Time              Duration \n"+prettyPrint(Appointments,textBoxOwnerName.getValue()));
          }
        });

      }
    });
  }

  /**
   * This method is used to print appointment details on UI
   * @param appointments appointments tobe pretty printed
   * @param OwnerName ownername to be printed
   * @return formated String with all details to be displayed on UI
     */
  public String prettyPrint(List<Appointment> appointments ,String OwnerName){
    int x=0;
    String prettyCalls="";
    for(Appointment appointment: appointments){
      //prettyCalls += call.toString()+"\n";
      prettyCalls += ++x+" "+OwnerName+" "+ appointment.getDescription()+" "+ appointment.getBeginTimeString() + "  " + appointment.getEndTimeString() + "   " + appointment.duration() + "\n";
    }
    return prettyCalls;
  }


}
