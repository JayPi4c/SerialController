package de.jaypi4c;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Hello world!
 *
 */
@Slf4j
public class App {

    static void main() throws InterruptedException {
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

            private final ByteArrayOutputStream payload = new ByteArrayOutputStream();
            private int expectedLength = -1;           // -1 = waiting for length

            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
            }

            // TODO: comply to TLV protocol pattern
            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] buffer = new byte[serialPort.bytesAvailable()];
                serialPort.readBytes(buffer, buffer.length);

                for (byte b : buffer) {
                    processByte(b);
                }
            }

            private void processByte(byte b) {
                if (expectedLength < 0) {
                    // First byte = message length
                    expectedLength = b & 0xFF;  // convert to unsigned
                    log.debug("expected length {}", expectedLength);
                    payload.reset();
                } else {
                    payload.write(b);

                    if (payload.size() == expectedLength) {
                        // Full message received
                        String message = payload.toString(StandardCharsets.UTF_8);
                        log.info("Received: {}", message);

                        // Reset for next message
                        expectedLength = -1;
                        payload.reset();
                    }
                }
            }
        });


        if (!serialPort.openPort()) {
            log.error("Failed to open port");
            return;
        }
        log.info("Port opened successfully");
        // Thread.sleep(1000);

        sendEchoCommand(serialPort, "Hello");
        sendLCDCommand(serialPort, "World");
        sendLEDCommand(serialPort, 13, true);
        Thread.sleep(1000);
        sendLEDCommand(serialPort, 13, false);

    }

    public static void sendCommand(SerialPort serialPort, String command) {
        byte[] payload = command.getBytes(StandardCharsets.UTF_8);
        if (payload.length > 255) throw new IllegalArgumentException("Command too long");

        serialPort.writeBytes(new byte[]{(byte) payload.length}, 1); // length
        serialPort.writeBytes(payload, payload.length);               // payload
    }

    public static void sendEchoCommand(SerialPort serialPort, String msg) {
        byte[] textBytes = msg.getBytes(StandardCharsets.UTF_8);

        byte[] payload = new byte[1 + textBytes.length];
        payload[0] = Protocol.CMD_ECHO.getCode();
        System.arraycopy(textBytes, 0, payload, 1, textBytes.length);

        serialPort.writeBytes(new byte[]{(byte) payload.length}, 1);
        serialPort.writeBytes(payload, payload.length);
    }

    public static void sendLEDCommand(SerialPort serialPort, int ledPin, boolean state) {
        byte[] payload = new byte[3];
        payload[0] = Protocol.CMD_LED.getCode();
        payload[1] = (byte) ledPin;
        payload[2] = (byte) (state ? 1 : 0);

        serialPort.writeBytes(new byte[]{(byte) payload.length}, 1);
        serialPort.writeBytes(payload, payload.length);
    }

    public static void sendLCDCommand(SerialPort serialPort, String msg) {
        byte[] textBytes = msg.getBytes(StandardCharsets.UTF_8);

        byte[] payload = new byte[1 + textBytes.length];
        payload[0] = Protocol.CMD_LCD.getCode();
        System.arraycopy(textBytes, 0, payload, 1, textBytes.length);

        serialPort.writeBytes(new byte[]{(byte) payload.length}, 1);
        serialPort.writeBytes(payload, payload.length);
    }
}
