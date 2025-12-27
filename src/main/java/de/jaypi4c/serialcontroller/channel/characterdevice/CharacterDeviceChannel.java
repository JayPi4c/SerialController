package de.jaypi4c.serialcontroller.channel.characterdevice;

import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.ChannelMessageListener;
import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;
import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.Protocol;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/// Only 9600 baud rate supported for now. You have to set the Arduino to 9600 baud rate in the sketch:
///
/// Or you have to update the baud rate using stty command before running the application: `stty -F /dev/ttyACM0 115200`
/// 
/// Maybe also `stty -F /dev/ttyACM0 raw -echo 9600` required?
///
/// @param <P>
@Slf4j
public class CharacterDeviceChannel<P extends Protocol<P>> implements Channel<P> {
    private final List<ChannelMessageListener<P>> listeners;
    private final P protocol;
    private FileOutputStream outputStream;
    private FileInputStream inputStream;
    private boolean connected;
    private Thread dataListener;

    public CharacterDeviceChannel(P protocol) {
        this.listeners = new ArrayList<>();
        this.protocol = protocol;
    }

    @Override
    public void open(DeviceConfiguration deviceConfiguration) {
        if (!(deviceConfiguration instanceof CharacterDeviceConfiguration(
                CharacterDevice(_, String portPath, int baud)
        )))
            throw new IllegalArgumentException("Invalid device configuration type");

        if(!configureTTY(portPath, baud))
            log.warn("Failed to configure TTY");
        else
            log.debug("Successfully configured TTY");

        try {
            outputStream = new FileOutputStream(portPath);
            inputStream = new FileInputStream(portPath);
            Runnable r = () -> {
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        int data = inputStream.read();
                        protocol.getMessageDecoder().pushNextByte((byte) data);
                        // log.debug("Read byte: 0x{}", Integer.toHexString(data & 0xFF));
                        if (protocol.getMessageDecoder().isMessageComplete()) {
                            Message<P> message = protocol.getMessageDecoder().getDecodedMessage();
                            for (ChannelMessageListener<P> listener : listeners) {
                                listener.onMessageReceived(message);
                            }
                        }
                    }
                } catch (IOException e) {
                    log.error("Error reading from character device", e);
                }
            };
            dataListener = new Thread(r);
            dataListener.start();
            connected = true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean configureTTY(String path, int baud){
        try{
           ProcessBuilder pb = new ProcessBuilder(
    "stty",
                "-F",
                path,
                "raw",
                "-echo",
                String.valueOf(baud)
            );

            pb.redirectErrorStream(true);
            Process p = pb.start();
            int exitCode = p.waitFor();
            if (exitCode != 0) {
                log.warn("Executing tty configs failed!");
                return false;
            }
        } catch(Exception _) {
            return false;
        }
        return true;
    }

    @Override
    public void close() {
        try {
            if (dataListener != null)
                dataListener.interrupt();
            if (outputStream != null)
                outputStream.close();
            if (inputStream != null)
                inputStream.close();
            connected = false;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(Message<P> message) {
        byte[] encodedMessage = protocol.getMessageEncoder().encode(message);
        log.debug("Sending bytes: {}", Arrays.toString(encodedMessage));
        try {
            outputStream.write(encodedMessage);
            outputStream.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<? extends Device> getDevices() {
        return List.of(
                new CharacterDevice("Character Device", "/dev/ttyACM0", 9600)
        );
    }

    @Override
    public void addMessageListener(ChannelMessageListener<P> channelMessageListener) {
        listeners.add(channelMessageListener);
    }

    @Override
    public void removeMessageListener(ChannelMessageListener<P> channelMessageListener) {
        listeners.remove(channelMessageListener);
    }

    @Override
    public boolean isConnected() {
        return connected;
    }
}
