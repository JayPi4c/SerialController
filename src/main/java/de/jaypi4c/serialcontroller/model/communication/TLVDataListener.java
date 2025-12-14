package de.jaypi4c.serialcontroller.model.communication;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import de.jaypi4c.serialcontroller.model.CommunicatorMessageListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * DataListener expecting data in the TLV Format
 *
 * @see <a href="https://de.wikipedia.org/wiki/Type-Length-Value">Wikipedia</a>
 */
@Slf4j
@RequiredArgsConstructor
public class TLVDataListener implements SerialPortDataListener {

    private final ByteArrayOutputStream payload = new ByteArrayOutputStream();
    private final SerialPort serialPort;
    private final List<CommunicatorMessageListener> listeners;
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
                log.debug("Received: {}", message);
                for (CommunicatorMessageListener listener : listeners) {
                    listener.onMessageReceived(message);
                }

                // Reset for next message
                expectedLength = -1;
                payload.reset();
            }
        }
    }
}
