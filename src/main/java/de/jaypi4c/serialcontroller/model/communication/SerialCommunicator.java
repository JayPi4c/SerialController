package de.jaypi4c.serialcontroller.model.communication;

import com.fazecast.jSerialComm.SerialPort;
import de.jaypi4c.serialcontroller.model.Communicator;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;

@Slf4j
public class SerialCommunicator implements Communicator {

    private SerialPort serialPort;

    @Getter
    private boolean connected = false;

    public SerialPort[] getPorts() {
        return SerialPort.getCommPorts();
    }

    @Override
    public void open(String portDescriptor, int baudRate) {
        if (connected) {
            log.warn("Communicator is already connected");
            return;
        }

        serialPort = SerialPort.getCommPort(portDescriptor);

        serialPort.setComPortParameters(
                baudRate,
                8,       // Data bits
                SerialPort.ONE_STOP_BIT,
                SerialPort.NO_PARITY
        );

        serialPort.setComPortTimeouts(
                SerialPort.TIMEOUT_READ_BLOCKING,
                1000,    // Read timeout (ms)
                0
        );
        serialPort.addDataListener(new TLVDataListener(serialPort));

        if (!serialPort.openPort())
            log.error("Failed to open port");
        else {
            connected = true;
            log.info("Port opened successfully");
        }
    }

    @Override
    public void close() {
        if (!connected) {
            log.info("Communicator is not connected");
            return;
        }
        if (serialPort.closePort()) {
            connected = false;
            log.info("Port closed successfully");
        } else {
            log.error("Failed to close port");
        }
    }

    @Override
    public void sendCommand(String command) {
        byte[] payload = command.getBytes(StandardCharsets.UTF_8);
        if (payload.length > 255) throw new IllegalArgumentException("Command too long");

        serialPort.writeBytes(new byte[]{(byte) payload.length}, 1); // length
        serialPort.writeBytes(payload, payload.length);               // payload
    }

    @Override
    public void sendEchoCommand(String msg) {
        byte[] textBytes = msg.getBytes(StandardCharsets.UTF_8);

        byte[] payload = new byte[1 + textBytes.length];
        payload[0] = Protocol.CMD_ECHO.getCode();
        System.arraycopy(textBytes, 0, payload, 1, textBytes.length);

        serialPort.writeBytes(new byte[]{(byte) payload.length}, 1);
        serialPort.writeBytes(payload, payload.length);
    }

    @Override
    public void sendLEDCommand(int ledPin, boolean state) {
        byte[] payload = new byte[3];
        payload[0] = de.jaypi4c.serialcontroller.model.communication.Protocol.CMD_LED.getCode();
        payload[1] = (byte) ledPin;
        payload[2] = (byte) (state ? 1 : 0);

        serialPort.writeBytes(new byte[]{(byte) payload.length}, 1);
        serialPort.writeBytes(payload, payload.length);
    }

    @Override
    public void sendLCDCommand(String msg) {
        byte[] textBytes = msg.getBytes(StandardCharsets.UTF_8);

        byte[] payload = new byte[1 + textBytes.length];
        payload[0] = de.jaypi4c.serialcontroller.model.communication.Protocol.CMD_LCD.getCode();
        System.arraycopy(textBytes, 0, payload, 1, textBytes.length);

        serialPort.writeBytes(new byte[]{(byte) payload.length}, 1);
        serialPort.writeBytes(payload, payload.length);
    }

}
