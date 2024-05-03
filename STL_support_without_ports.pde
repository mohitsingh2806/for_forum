 //<>// //<>// //<>// //<>// //<>// //<>//
void setup() {

  size(500, 650);


  initTheDisplay();
}


void draw() {


  mainPage.show();

  background(#C5DFF8);
  fontHead = createFont("AdobeHeitiStd-Regular", 25);
  textFont(fontHead);
  textAlign(CENTER);
  stroke(#A0BFE0);
  fill(#A0BFE0);
  stroke(#A0BFE0);
  fill(#A0BFE0);
  rect(55*10/10, 160*10/10, 390*10/10, 220*10/10);

  stroke(#333333);
  line(150*10/10, 183*10/10, 307*10/10, 183*10/10);
  image(BEL_logo, 6*10/10, 4*10/10, 150*10/10, 65*10/10);
}



//Main event loop

void controlEvent(ControlEvent event) {


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
