import processing.serial.*; 
import controlP5.*;
import java.io.InputStreamReader;
import java.util.*;
import javax.swing.JOptionPane;
import processing.serial.*;
import processing.net.*;
import java.awt.datatransfer.*;
import java.awt.Toolkit;
// import java.net.*;
//import java.io.*;
import java.net.*;

import hypermedia.net.*;


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
void icon(boolean theValue) {
  println("got an event for icon", theValue);
}

void connectTCP() {
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

void disconnectEvent(Client client) {

  tcpConnected = false;
  connectMPTButton.show();
  disconnectMPTButton.hide();
  println("Disconnected");
}



void initTheDisplay() {

  CColor bg = new CColor();



  font = createFont("AdobeHeitiStd-Regular", 10*10/10);
  udp = new UDP( this, 9999 );

  mainPage = new ControlP5(this);
  surface.setTitle("                                                      MPT Eth Configuration");
  //surface.setResizable(true);
  Title = mainPage.addTextlabel("title")
    //.setText("MINT")
    .setPosition(250*10/10, 110*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 25*10/10))

    ;

  searchedIPField=  mainPage.addTextfield("searchedIPField")
    .setPosition(250*10/10, 75*10/10)
    .setSize(180*10/10, 25*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setFocus(true)
    .setColorBackground(#c4c1bb )
    .setColor(#333333 )
    .setCaptionLabel("")
    .setColorActive(#C8E6F5 )
    .setColorForeground(#C8E6F5)

    .setAutoClear(false)
    .setColorCursor(#333333)
    ;


  mainPage.addTextlabel("configureSettings")
    .setText("MPT Configuration")
    .setPosition(150*10/10, 165*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))
    ;


  mainPage.addTextlabel("localIP")
    .setText("IP Address: ")
    .setPosition(55*10/10, 190*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;

  ipAddressField=  mainPage.addTextfield("ipAddressField")
    .setPosition(250*10/10, 190*10/10)
    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    //.setFocus(true)
    .setColorBackground(#C8E6F5 )
    .setColor(#333333 )
    .setCaptionLabel("")
    .setColorActive(#C8E6F5 )
    .setColorForeground(#C8E6F5)

    .setAutoClear(false)
    .setColorCursor(#333333)
    ;


  mainPage.addTextlabel("gateway")
    .setText("Gateway: ")
    .setPosition(55*10/10, 215*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;

  gatewayField=  mainPage.addTextfield("gatewayField")
    .setPosition(250*10/10, 215*10/10)
    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(#C8E6F5 )
    .setColor(#333333 )
    .setCaptionLabel("")
    .setColorActive(#C8E6F5 )
    .setColorForeground(#C8E6F5)

    .setAutoClear(false)
    .setColorCursor(#333333)
    ;

  mainPage.addTextlabel("subnetMask")
    .setText("Subnet Mask: ")
    .setPosition(55*10/10, 240*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;

  subnetField=  mainPage.addTextfield("subnetField")
    .setPosition(250*10/10, 240*10/10)
    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(#C8E6F5 )
    .setColor(#333333 )
    .setCaptionLabel("")
    .setColorActive(#C8E6F5 )
    .setColorForeground(#C8E6F5)

    .setAutoClear(false)
    .setColorCursor(#333333)
    ;


  serialBaud = mainPage.addTextlabel("serialBaud")
    .setText("Baud Rate: ")
    //.setPosition(55*10/10, 315*10/10)
    .setPosition(55*10/10, 265*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;
  serialBaudField=  mainPage.addTextfield("serialBaudField")
    //.setPosition(250*10/10, 315*10/10)
    .setPosition(250*10/10, 265*10/10)

    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(#C8E6F5 )
    .setColor(#333333 )
    .setCaptionLabel("")
    .setColorActive(#C8E6F5 )
    .setColorForeground(#C8E6F5)

    .setAutoClear(false)
    .setColorCursor(#333333)
    ;

  customCommand = mainPage.addTextlabel("customCommand")
    .setText("Custom Command: ")
    //.setPosition(55*10/10, 340*10/10)
    .setPosition(55*10/10, 290*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))

    ;
  customCommandField=  mainPage.addTextfield("customCommandField")
    //.setPosition(250*10/10, 340*10/10)
    .setPosition(250*10/10, 290*10/10)
    .setSize(180*10/10, 20*10/10)
    .setFont(createFont("Georgia", 15*10/10))
    .setColorBackground(#C8E6F5 )
    .setColor(#333333 )
    .setCaptionLabel("")
    .setColorActive(#C8E6F5 )
    .setColorForeground(#C8E6F5)

    .setAutoClear(false)
    .setColorCursor(#333333)
    ;
  messageArea= mainPage.addTextarea("messages")
    .setPosition(55*10/10, 400*10/10)
    .setSize(390*10/10, 200*10/10)
    .setLineHeight(14*10/10)
    .setFont(createFont("Georgia", 15*10/10))
    .setText(".")
    .setColorValue(#333333)
    .setColorBackground(color(255, 255, 255, 100))
    .setColorForeground(color(255, 255, 255, 100))
    //.setAutoClear(false);
    ;



  ///////////////// HIDDEN ///////////////////////
  mainPage.addTextlabel("localPort")
    .setText("Local GUI Port: ")
    .setPosition(55*10/10, 265*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))
    .hide()

    ;

  localPortField=  mainPage.addTextfield("localPortField")
    .setPosition(250*10/10, 265*10/10)
    .setSize(130*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(#C8E6F5 )
    .setColor(#333333 )
    .setCaptionLabel("")
    .setColorActive(#C8E6F5 )
    .setColorForeground(#C8E6F5)
    .hide()
    .setAutoClear(false)
    .setColorCursor(#333333)
    ;



  remotePort = mainPage.addTextlabel("remotePort")
    .setText("Remote GUI Port: ")
    .setPosition(55*10/10, 290*10/10)
    .setColorValue(#333333 )
    .setFont(createFont("Georgia Bold", 15*10/10))
    .hide()
    ;

  remotePortField=  mainPage.addTextfield("remotePortField")
    .setPosition(250*10/10, 290*10/10)
    .setSize(130*10/10, 20*10/10)
    .setFont(createFont("Georgia Bold", 15*10/10))
    .setColorBackground(#C8E6F5 )
    .setColor(#333333 )
    .setCaptionLabel("")
    .setColorActive(#C8E6F5 )
    .setColorForeground(#C8E6F5)
    .hide()
    .setAutoClear(false)
    .setColorCursor(#333333)
    ;

  ///////////////// HIDDEN ///////////////////////
  BEL_logo = loadImage("BEL.png");


  findMPTButton = mainPage.addButton("Search Terminal")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(54*10/10, 75*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(#89afd8 )
    .setColorCaptionLabel(#333333 )
    .setColorForeground(#71acd9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;
  pingMPTButton = mainPage.addButton("Ping Terminal")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(54*10/10, 103*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(#89afd8 )
    .setColorCaptionLabel(#333333 )
    .setColorForeground(#71acd9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;

  connectMPTButton = mainPage.addButton("Connect MPT")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(54*10/10, 131*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(#89afd8 )
    .setColorCaptionLabel(#333333 )
    .setColorForeground(#71acd9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;
  disconnectMPTButton = mainPage.addButton("Disconnect MPT")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(54*10/10, 131*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(#89afd8 )
    .setColorCaptionLabel(#333333 )
    .setColorForeground(#71acd9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    .hide()
    ;

  configureButton = mainPage.addButton("Configure")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(200*10/10, 350*10/10)
    .setSize(100*10/10, 25*10/10)
    .setColorBackground(#89afd8 )
    .setColorCaptionLabel(#333333 )
    .setColorForeground(#71acd9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;

  checkConfigurationButton = mainPage.addButton("Check Config")  // this is only required for touch screen display
    .setValue(0)
    .setPosition(250*10/10, 131*10/10)
    .setSize(150*10/10, 25*10/10)
    .setColorBackground(#89afd8 )
    .setColorCaptionLabel(#333333 )
    .setColorForeground(#71acd9 )
    .setFont(createFont("Georgia Bold", 12*10/10))
    //.hide()
    ;

  PFont.list();
}

void keyPressed() {    // if enter is pressed without doing anything on gui start
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



void clientEvent(Client client) {
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



void receive( byte[] data, String ip, int port ) {  // <-- extended handler

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


void appendText(String newText) {
  String oldText=".";
  if (messageArea.getText().length()>0) {
    oldText = messageArea.getText();
  }
  messageArea.setText(oldText +"\r\n"+ newText);
  //messageArea.setText(newText);
}
