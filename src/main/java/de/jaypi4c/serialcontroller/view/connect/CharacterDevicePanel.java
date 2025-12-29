package de.jaypi4c.serialcontroller.view.connect;

import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDevice;
import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDeviceChannel;
import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDeviceConfiguration;
import de.jaypi4c.serialcontroller.protocol.ltv.LTV;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;

import javax.swing.*;
import java.awt.*;
import java.util.List;

@Slf4j
@Getter
public class CharacterDevicePanel extends JPanel implements ConfigurationPanel {

    public static final String ID = "CHAR_DEVICE";

    private final JTextField pathInputField;
    private final JTextField baudInputField;
    private final JTextField ledPinInputField;

    public CharacterDevicePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        pathInputField = new JTextField("/dev/ttyACM0");
        add(pathInputField);

        baudInputField = new JTextField("9600");
        add(baudInputField);

        ledPinInputField = new JTextField("13");
        add(ledPinInputField);
    }

    @Override
    public void addDevice(Device device) {
        // noop
    }

    @Override
    public void addDevices(List<? extends Device> devices) {
        // noop
    }

    @Override
    public void clearDevices() {
        // noop
    }

    @Override
    public DeviceConfiguration getCurrentConfiguration() {
        return new CharacterDeviceConfiguration(new CharacterDevice("Character Device", getPath(), getBaudRate()));
    }

    private String getPath() {
        return pathInputField.getText();
    }

    private int getBaudRate() {
        try {
            return Integer.parseInt(baudInputField.getText());
        } catch (NumberFormatException _) {
            return 9600;
        }
    }

    @Override
    public int getLEDPin() {
        try {
            return Integer.parseInt(ledPinInputField.getText());
        } catch (NumberFormatException _) {
            return 13;
        }
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public Channel<LTV> getChannel() {
        return new CharacterDeviceChannel<>(new LTV());
    }
}
