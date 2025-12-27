package de.jaypi4c.serialcontroller.view.connect;

import java.awt.event.ActionListener;
import java.util.List;

import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;

public interface ConfigurationPanel {
    
    void addDevice(Device device);

    void addDevices(List<? extends Device> devices);

    DeviceConfiguration getCurrentConfiguration();

}
