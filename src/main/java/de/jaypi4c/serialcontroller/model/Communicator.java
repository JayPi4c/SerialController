package de.jaypi4c.serialcontroller.model;

import com.fazecast.jSerialComm.SerialPort;

public interface Communicator {

    void open(String portDescriptor, int baudRate);

    void close();

    void sendCommand(String command);

    void sendEchoCommand(String msg);

    void sendLEDCommand(int ledPin, boolean state);

    void sendLCDCommand(String msg);

    SerialPort[] getPorts();

    void addMessageListener(CommunicatorMessageListener listener);

    void removeMessageListener(CommunicatorMessageListener listener);
}
