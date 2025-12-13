package de.jaypi4c;

import com.fazecast.jSerialComm.SerialPort;

public class ListPorts {
    public static void main(String[] args) {
        SerialPort[] ports = SerialPort.getCommPorts();
        for (SerialPort port : ports) {
            System.out.println(port.getSystemPortName() + " - " + port.getDescriptivePortName());
        }
    }
}