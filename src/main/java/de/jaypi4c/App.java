package de.jaypi4c;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )throws InterruptedException
    {
        SerialPort serialPort = SerialPort.getCommPort("/dev/ttyACM0"); // or "/dev/ttyUSB0"

        serialPort.setComPortParameters(
                250000,    // Baud rate
                8,       // Data bits
                SerialPort.ONE_STOP_BIT,
                SerialPort.NO_PARITY
        );

        serialPort.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING,
                1000,    // Read timeout (ms)
                0
        );

        serialPort.addDataListener(new SerialPortDataListener() {
            
            private final StringBuilder lineBuffer = new StringBuilder();
            
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] buffer = new byte[serialPort.bytesAvailable()];
                serialPort.readBytes(buffer, buffer.length);

                for (byte b : buffer) {
                    if (b == '\n') {
                        // message from arduino is complete
                        String line = lineBuffer.toString().trim();
                        lineBuffer.setLength(0);
                        if (!line.isEmpty())
                            System.out.println("Received: " + line);
                    } else {
                        lineBuffer.append((char) b);
                    }
                }
            }
        });

        if (!serialPort.openPort()) {
            System.err.println("Failed to open port");
            return;
        }
        System.out.println("Port opened successfully");
        
        Thread.sleep(1000);
        String message = "HIGH\n";
        byte[] data = message.getBytes();
        serialPort.writeBytes(data, data.length);
    }
}
