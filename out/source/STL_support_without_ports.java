/* autogenerated by Processing revision 1293 on 2024-05-03 */
import processing.core.*;
import processing.data.*;
import processing.event.*;
import processing.opengl.*;

import processing.serial.*;
import controlP5.*;
import java.io.InputStreamReader;
import java.util.*;
import javax.swing.JOptionPane;
import processing.serial.*;
import processing.net.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
import java.net.*;
import hypermedia.net.*;

import java.util.HashMap;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;

public class STL_support_without_ports extends PApplet {

 //<>// //<>// //<>// //<>// //<>// //<>//
public void setup() {

  /* size commented out by preprocessor */;


  initTheDisplay();
}


public void draw() {


  mainPage.show();

  background(0xFFC5DFF8);
  fontHead = createFont("AdobeHeitiStd-Regular", 25);
  textFont(fontHead);
  textAlign(CENTER);
  stroke(0xFFA0BFE0);
  fill(0xFFA0BFE0);
  stroke(0xFFA0BFE0);
  fill(0xFFA0BFE0);
  rect(55*10/10, 160*10/10, 390*10/10, 220*10/10);

  stroke(0xFF333333);
  line(150*10/10, 183*10/10, 307*10/10, 183*10/10);
  image(BEL_logo, 6*10/10, 4*10/10, 150*10/10, 65*10/10);
}



//Main event loop

public void controlEvent(ControlEvent event) {


  String tempData = ".";
  if ( event.isFrom(findMPTButton)) {
    appendText("Searching. . . ");
    udp.listen( true );
  } else if ( event.isFrom(connectMPTButton)) { //<>//
    udp.listen( true );
    appendText("Connecting . . .");
    if (searchedIPField.getText().length()>0) {

      connectTCP();
    } else {
      appendText("No MPT available for connection");
    }
  } else if ( event.isFrom(checkConfigurationButton)) {
    if (TCPclient.active()) {
      try {
        TCPclient.write("CONFIGURATION\r");
      }
      catch(Exception e) {
        e.printStackTrace();
      }
    }
  } else if ( event.isFrom(disconnectMPTButton)) {
    TCPclient.stop();
    connectMPTButton.show();
    disconnectMPTButton.hide();
    appendText("Disconnected from MPT");
  } 
  
  else if ( event.isFrom(configureButton)) {

    //ipAddressField, gatewayField, subnetField, localPortField, remotePortField, serialBaudField;

    if (ipAddressField.getText().length()>0 ) {
      tempData = ipAddressField.getText();
      
      String[] m = match(tempData, "(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])");

      if ( m != null) {

        if (TCPclient.active()) {
          println(tempData);
          TCPclient.write("LOCAL_IP="+tempData+"\r");
        }
      } else appendText("\r\n Invalid IP Address");
    }

    if (gatewayField.getText().length()>0 ) {
      tempData = gatewayField.getText();
      String[] m = match(tempData, "(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])");

      if ( m != null) {
        if (TCPclient.active()) {
          println(tempData);
          TCPclient.write("DEFAULT_GATEWAY="+tempData+"\r");
        }
      } else appendText("Invalid Gateway");
    }

    if (subnetField.getText().length()>0 ) {
      tempData = subnetField.getText();
      String[] m = match(tempData, "(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])");

      if ( m != null) {
        if (TCPclient.active()) {
          println(tempData);
          TCPclient.write("SUBNET="+tempData+"\r");
        }
      } else appendText("Invalid Subnet Mask");
    }

    if (localPortField.getText().length()>0 ) {
      tempData = localPortField.getText();
      String[] m = match(tempData, "([0-9]+)");

      if ( m != null) {
        if (TCPclient.active()) {
          println(tempData);
          TCPclient.write("LOCAL_PORT="+tempData+"\r");
          //println("LOCAL_PORT="+tempData+"\r");
        }
      } else appendText("Invalid Port");
    }

    if (remotePortField.getText().length()>0 ) {
      tempData = remotePortField.getText();
      String[] m = match(tempData, "([0-9]+)");

      if ( m != null) {
        if (TCPclient.active()) {
          println(tempData);
          TCPclient.write("REMOTE_PORT="+tempData+"\r");
          //println("REMOTE_PORT="+tempData+"\r");
        }
      } else appendText("Invalid Port");
    }

    if (serialBaudField.getText().length()>0 ) {
      tempData = serialBaudField.getText();
      String[] m = match(tempData, "([0-9]+)");

      if ( m != null) {
        if (TCPclient.active()) {
          println(tempData);
          TCPclient.write("BAUD_RATE="+tempData+"\r");
          //println("BAUD_RATE="+tempData+"\r");
        }
      } else appendText("Invalid Baud Rate");
    }



    if (customCommandField.getText().length()>0 ) {
      tempData = customCommandField.getText();
      if (TCPclient.active()) {
        println(tempData);
        TCPclient.write(tempData+"\r");
      }
    }
  } else if ( event.isFrom(pingMPTButton)) {
    //println("EVENT");
    if (searchedIPField.getText().length()>0) {
      //println("LENGTH CHECK");
      tempData = searchedIPField.getText();
      String[] m = match(tempData, "(([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])");

      if ( m != null) {
        //println("REGEX CHECK");
        try {
          sendPingRequest(searchedIPField.getText());
        }
        catch (IOException e)
        {
          e.printStackTrace();
        }
      }
    }
  } else if (event.isController() && event.getController() instanceof Textfield) {

    focusCtrl = event.getController();
  }
}
 








// import java.net.*;
//import java.io.*;





UDP udp;  // define the UDP object



String serverAddress = ""; // IP address of the TCP server
int TCP_Port = 23; // Port number of the TCP server
Client TCPclient; // TCP client object








Serial myPort;  // Create a Serial object


ControlP5 mainPage;
ControlP5 sensorPage;
PFont font, fontHead;
Textlabel Title;
Textlabel configureSettings;
Textlabel remotePort, serialBaud, customCommand;

Textfield searchedIPField, ipAddressField, gatewayField, subnetField, localPortField, remotePortField, serialBaudField, customCommandField;
ScrollableList satList;
Button  configureButton, findMPTButton, pingMPTButton, connectMPTButton, disconnectMPTButton, checkConfigurationButton;
Controller focusCtrl = null;
PImage BEL_logo;

//SCALING CAN BE CHANGED ACC TO SCREEN SIZE IN CODE
//REPLACE 10/ 10 with 8/ 10 for 80% scaling and similarly for other scaling percentages
int selectedSatIndex = 0;
String serialData="";
boolean tcpConnected = false;
int timeSinceLastTry = 0;
Textarea messageArea;
String tempText="";
public void icon(boolean theValue) {
  println("got an event for icon", theValue);
}

public void connectTCP() {
  String serverAddress = searchedIPField.getText();
  String[] m = match(serverAddress, "\\.(.*?)\\.");

  if ( m != null) {
    TCPclient = new Client(this, serverAddress, TCP_Port); // Create a new TCP client
    if (TCPclient.active()) {
      tcpConnected = true;
      appendText("MPT Connected at " + serverAddress);
      println("Connected");
      connectMPTButton.hide();
      disconnectMPTButton.show();
    } else {
      tcpConnected = false;
      connectMPTButton.show();
      disconnectMPTButton.hide();
    }
  } else {
    appendText("Invalid IP Address");
  }
}

public void disconnectEvent(Client client) {

  tcpConnected = false;
  connectMPTButton.show();
  disconnectMPTButton.hide();
  println("Disconnected");
}



public void initTheDisplay() {

  CColor bg = new CColor();



  font = createFont("AdobeHeitiStd-Regular", 10*10/10);
  udp = new UDP( this, 9999 );

  mainPage = new ControlP5(this);
  surface.setTitle("                                                      MPT Eth Configuration");
  //surface.setResizable(true);
  Title = mainPage.addTextlabel("title")
    //.setText("MINT")
    .setPosition(250*10/10, 110*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 25*10/10))

    ;

  searchedIPField=  mainPage.addTextfield("searchedIPField")
    .setPosition(250*10/10, 75*10/10)
    .setSize(180*10/10, 25*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setFocus(true)
    .setColorBackground(0xFFC4C1BB )
    .setColor(0xFF333333 )
    .setCaptionLabel("")
    .setColorActive(0xFFC8E6F5 )
    .setColorForeground(0xFFC8E6F5)

    .setAutoClear(false)
    .setColorCursor(0xFF333333)
    ;


  mainPage.addTextlabel("configureSettings")
    .setText("MPT Configuration")
    .setPosition(150*10/10, 165*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))
    ;


  mainPage.addTextlabel("localIP")
    .setText("IP Address: ")
    .setPosition(55*10/10, 190*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;

  ipAddressField=  mainPage.addTextfield("ipAddressField")
    .setPosition(250*10/10, 190*10/10)
    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    //.setFocus(true)
    .setColorBackground(0xFFC8E6F5 )
    .setColor(0xFF333333 )
    .setCaptionLabel("")
    .setColorActive(0xFFC8E6F5 )
    .setColorForeground(0xFFC8E6F5)

    .setAutoClear(false)
    .setColorCursor(0xFF333333)
    ;


  mainPage.addTextlabel("gateway")
    .setText("Gateway: ")
    .setPosition(55*10/10, 215*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;

  gatewayField=  mainPage.addTextfield("gatewayField")
    .setPosition(250*10/10, 215*10/10)
    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(0xFFC8E6F5 )
    .setColor(0xFF333333 )
    .setCaptionLabel("")
    .setColorActive(0xFFC8E6F5 )
    .setColorForeground(0xFFC8E6F5)

    .setAutoClear(false)
    .setColorCursor(0xFF333333)
    ;

  mainPage.addTextlabel("subnetMask")
    .setText("Subnet Mask: ")
    .setPosition(55*10/10, 240*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;

  subnetField=  mainPage.addTextfield("subnetField")
    .setPosition(250*10/10, 240*10/10)
    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(0xFFC8E6F5 )
    .setColor(0xFF333333 )
    .setCaptionLabel("")
    .setColorActive(0xFFC8E6F5 )
    .setColorForeground(0xFFC8E6F5)

    .setAutoClear(false)
    .setColorCursor(0xFF333333)
    ;


  serialBaud = mainPage.addTextlabel("serialBaud")
    .setText("Baud Rate: ")
    //.setPosition(55*10/10, 315*10/10)
    .setPosition(55*10/10, 265*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;
  serialBaudField=  mainPage.addTextfield("serialBaudField")
    //.setPosition(250*10/10, 315*10/10)
    .setPosition(250*10/10, 265*10/10)

    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(0xFFC8E6F5 )
    .setColor(0xFF333333 )
    .setCaptionLabel("")
    .setColorActive(0xFFC8E6F5 )
    .setColorForeground(0xFFC8E6F5)

    .setAutoClear(false)
    .setColorCursor(0xFF333333)
    ;

  customCommand = mainPage.addTextlabel("customCommand")
    .setText("Custom Command: ")
    //.setPosition(55*10/10, 340*10/10)
    .setPosition(55*10/10, 290*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;
  customCommandField=  mainPage.addTextfield("customCommandField")
    //.setPosition(250*10/10, 340*10/10)
    .setPosition(250*10/10, 290*10/10)
    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia", 15*10/10))
    .setColorBackground(0xFFC8E6F5 )
    .setColor(0xFF333333 )
    .setCaptionLabel("")
    .setColorActive(0xFFC8E6F5 )
    .setColorForeground(0xFFC8E6F5)

    .setAutoClear(false)
    .setColorCursor(0xFF333333)
    ;
  messageArea= mainPage.addTextarea("messages")
    .setPosition(55*10/10, 400*10/10)
    .setSize(390*10/10, 200*10/10)
    .setLineHeight(14*10/10)
    .setFont(createFont("Georgia", 15*10/10))
    .setText(".")
    .setColorValue(0xFF333333)
    .setColorBackground(color(255, 255, 255, 100))
    .setColorForeground(color(255, 255, 255, 100))
    //.setAutoClear(false);
    ;



  ///////////////// HIDDEN ///////////////////////
  mainPage.addTextlabel("localPort")
    .setText("Local GUI Port: ")
    .setPosition(55*10/10, 265*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))
    .hide()

    ;

  localPortField=  mainPage.addTextfield("localPortField")
    .setPosition(250*10/10, 265*10/10)
    .setSize(130*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(0xFFC8E6F5 )
    .setColor(0xFF333333 )
    .setCaptionLabel("")
    .setColorActive(0xFFC8E6F5 )
    .setColorForeground(0xFFC8E6F5)
    .hide()
    .setAutoClear(false)
    .setColorCursor(0xFF333333)
    ;



  remotePort = mainPage.addTextlabel("remotePort")
    .setText("Remote GUI Port: ")
    .setPosition(55*10/10, 290*10/10)
    .setColorValue(0xFF333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))
    .hide()
    ;

  remotePortField=  mainPage.addTextfield("remotePortField")
    .setPosition(250*10/10, 290*10/10)
    .setSize(130*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(0xFFC8E6F5 )
    .setColor(0xFF333333 )
    .setCaptionLabel("")
    .setColorActive(0xFFC8E6F5 )
    .setColorForeground(0xFFC8E6F5)
    .hide()
    .setAutoClear(false)
    .setColorCursor(0xFF333333)
    ;

  ///////////////// HIDDEN ///////////////////////
  BEL_logo = loadImage("BEL.png");


  findMPTButton = mainPage.addButton("Search Terminal")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(54*10/10, 75*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(0xFF89AFD8 )
    .setColorCaptionLabel(0xFF333333 )
    .setColorForeground(0xFF71ACD9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;
  pingMPTButton = mainPage.addButton("Ping Terminal")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(54*10/10, 103*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(0xFF89AFD8 )
    .setColorCaptionLabel(0xFF333333 )
    .setColorForeground(0xFF71ACD9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;

  connectMPTButton = mainPage.addButton("Connect MPT")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(54*10/10, 131*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(0xFF89AFD8 )
    .setColorCaptionLabel(0xFF333333 )
    .setColorForeground(0xFF71ACD9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;
  disconnectMPTButton = mainPage.addButton("Disconnect MPT")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(54*10/10, 131*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(0xFF89AFD8 )
    .setColorCaptionLabel(0xFF333333 )
    .setColorForeground(0xFF71ACD9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    .hide()
    ;

  configureButton = mainPage.addButton("Configure")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(200*10/10, 350*10/10)
    .setSize(100*10/10, 25*10/10)
    .setColorBackground(0xFF89AFD8 )
    .setColorCaptionLabel(0xFF333333 )
    .setColorForeground(0xFF71ACD9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;

  checkConfigurationButton = mainPage.addButton("Check Config")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(250*10/10, 131*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(0xFF89AFD8 )
    .setColorCaptionLabel(0xFF333333 )
    .setColorForeground(0xFF71ACD9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;

  PFont.list();
}

public void keyPressed() {    // if enter is pressed without doing anything on gui start
  // Check if the Enter key is pressed
  //Textfield searchedIPField, ipAddressField, gatewayField, subnetField, localPortField, remotePortField, serialBaudField, customCommandField;
  if (key == ENTER) {
    // Trigger the button's action
    //configureButton.setValue(1) ;
    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    try {
      String clipboardData = (String) clipboard.getData(DataFlavor.stringFlavor);
      println(clipboardData);
      //(Textfield)focusCtrt.setText(clipboardData);
      if (customCommandField.isFocus()) {
        customCommandField.setText(clipboardData);
      } else if (searchedIPField.isFocus()) {
        searchedIPField.setText(clipboardData);
      } else if (ipAddressField.isFocus()) {
        ipAddressField.setText(clipboardData);
      } else if (gatewayField.isFocus()) {
        gatewayField.setText(clipboardData);
      } else if (subnetField.isFocus()) {
        subnetField.setText(clipboardData);
      } else if (serialBaudField.isFocus()) {
        serialBaudField.setText(clipboardData);
      }
    }
    catch (UnsupportedFlavorException e) {
      e.printStackTrace();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }
}



public void clientEvent(Client client) {
  String readData = TCPclient.readString();
  try {
    if (readData.length()>0) {
      appendText(readData);
    }
  }
  catch (Exception e) {
    //e.printStackTrace();
  }
  //readData = TCPclient.readString();
  //messageArea.setText(messageArea.getText() + readData);
}



public void receive( byte[] data, String ip, int port ) {  // <-- extended handler

  appendText("MPT is at IP Address: "+ip);
  searchedIPField.setText(ip);
  serverAddress = ip;
}


// Sends ping request to a provided IP address
public  void sendPingRequest(String ipAddress)
  throws UnknownHostException, IOException
{
  InetAddress pingIP = InetAddress.getByName(ipAddress);
  println("Sending Ping Request to " + ipAddress);
  if (pingIP.isReachable(5000))
    appendText("Ping Successful. ");
  else
    appendText("Ping Unsuccessful. ");
}


public void appendText(String newText) {
  String oldText=".";
  if (messageArea.getText().length()>0) {
    oldText = messageArea.getText();
  }
  messageArea.setText(oldText +"\r\n"+ newText);
  //messageArea.setText(newText);
}


  public void settings() { size(500, 650); }

  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "STL_support_without_ports" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}