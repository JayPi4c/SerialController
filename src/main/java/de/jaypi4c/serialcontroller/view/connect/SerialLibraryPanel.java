package de.jaypi4c.serialcontroller.view.connect;

import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;
import org.schlunzis.jduino.channel.serial.SerialDevice;
import org.schlunzis.jduino.channel.serial.SerialDeviceConfiguration;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class SerialLibraryPanel extends JPanel implements ConfigurationPanel {
    public static final String ID = "SERIAL_LIB";
    private final JComboBox<Device> connections;
    private final JTextField baudInputField;
    private final JTextField ledPinInputField;

    public SerialLibraryPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));

        JLabel nameLabel = new JLabel("Serial Library");
        nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
        add(nameLabel);

        add(createSeparator());

        JLabel connectionLabel = new JLabel("Device:");
        add(connectionLabel);
        connections = new JComboBox<>();
        connections.setRenderer(getPortRenderer());
        add(connections);

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

    private Dimension expandSize(Dimension original, double factor) {
        return new Dimension((int) (original.getWidth() * factor), (int) original.getHeight());
    }

    @Override
    public void addDevice(Device device) {
        connections.addItem(device);
    }

    @Override
    public void addDevices(List<? extends Device> devices) {
        devices.forEach(this::addDevice);
    }

    @Override
    public void clearDevices() {
        connections.removeAllItems();
    }

    private ListCellRenderer<Object> getPortRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                return super.getListCellRendererComponent(list, ((Device) value).getDisplayName(), index, isSelected, cellHasFocus);
            }
        };
    }

    @Override
    public DeviceConfiguration getCurrentConfiguration() {
        SerialDevice device = (SerialDevice) connections.getSelectedItem();
        return new SerialDeviceConfiguration(device, getBaudRate());
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
            return ConfigurationPanel.super.getLEDPin();
        }
    }

    private JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setOrientation(SwingConstants.VERTICAL);
        separator.setPreferredSize(new Dimension(2, 24));
        return separator;
    }

    @Override
    public String getID() {
        return ID;
    }

}
