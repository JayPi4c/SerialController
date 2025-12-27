package de.jaypi4c.serialcontroller.view.connect;

import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JPanel;

import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;

public class SerialLibraryPanel extends JPanel implements ConfigurationPanel {
    
    public static final String ID = "SERIAL_LIB";
    
    @Override
    public void addDevice(Device device) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addDevice'");
    }

    @Override
    public void addDevices(List<? extends Device> devices) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'addDevices'");
    }

    @Override
    public DeviceConfiguration getCurrentConfiguration() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCurrentConfiguration'");
    }
    
}
