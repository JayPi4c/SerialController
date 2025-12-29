package de.jaypi4c.serialcontroller.view.connect;

import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDevice;
import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDeviceConfiguration;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
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

        JLabel nameLabel = new JLabel("Character Device");
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
        add(nameLabel);

        add(createSeparator());

        JLabel pathLabel = new JLabel("Path:");
        add(pathLabel);
        pathInputField = new JTextField("/dev/ttyACM0");
        pathInputField.setPreferredSize(expandSize(pathInputField.getPreferredSize(), 1.25));
        add(pathInputField);

        add(createSeparator());

        JLabel baudLabel = new JLabel("Baud:");
        add(baudLabel);
        baudInputField = new JTextField("9600");
        baudInputField.setPreferredSize(expandSize(baudLabel.getPreferredSize(), 1.5));
        add(baudInputField);

        add(createSeparator());

        JLabel ledPinLabel = new JLabel("LED Pin:");
        add(ledPinLabel);
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

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(2, 24));
        return separator;
    }

    private Dimension expandSize(Dimension original, double factor) {
        return new Dimension((int) (original.getWidth() * factor), (int) original.getHeight());
    }
}
