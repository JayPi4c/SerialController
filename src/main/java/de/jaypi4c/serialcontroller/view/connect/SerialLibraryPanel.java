package de.jaypi4c.serialcontroller.view.connect;

import de.jaypi4c.serialcontroller.protocol.ltv.LTV;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;
import org.schlunzis.jduino.channel.serial.SerialChannel;

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
        connections = new JComboBox<>();
        add(connections);

        baudInputField = new JTextField("9600");
        add(baudInputField);

        ledPinInputField = new JTextField("13");
        add(ledPinInputField);
        connections.setRenderer(getPortRenderer());
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentConfiguration'");
    }

    @Override
    public int getLEDPin() {
        return 13;
    }

    @Override
    public String getID() {
        return ID;
    }

    @Override
    public Channel<LTV> getChannel() {
        return SerialChannel.builder().protocol(new LTV()).channelFactory(SerialChannel::new).build();
    }


}
