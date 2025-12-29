package de.jaypi4c.serialcontroller.view.connect;

import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;

import java.util.List;

public interface ConfigurationPanel {

    void addDevice(Device device);

    void addDevices(List<? extends Device> devices);

    void clearDevices();

    DeviceConfiguration getCurrentConfiguration();

    int getLEDPin();

    String getID();

}
