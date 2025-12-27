package de.jaypi4c.serialcontroller.view.connect;

import lombok.Getter;
import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;

import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDevice;
import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDeviceConfiguration;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

@Getter
public class CharacterDevicePanel extends JPanel implements ConfigurationPanel {

    public static final String ID = "CHAR_DEVICE";

    private final JComboBox<Device> connections;
    private final JTextField baudInputField;

    public CharacterDevicePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        connections = new JComboBox<>();
        add(connections);

        baudInputField = new JTextField();
        add(baudInputField); 

      //  connections.setRenderer(getPortRenderer());
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
    public void addDevice(Device device) {
        connections.addItem(device);
    }

    @Override
    public void addDevices(List<? extends Device> devices) {
        devices.forEach(this::addDevice); 
    }

    @Override
    public DeviceConfiguration getCurrentConfiguration() {
        return new CharacterDeviceConfiguration((CharacterDevice) connections.getSelectedItem());
    }
}
