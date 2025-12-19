package de.jaypi4c.serialcontroller.channel.characterdevice;

import org.schlunzis.jduino.channel.DeviceConfiguration;

public record CharacterDeviceConfiguration(
        CharacterDevice device,
        int baudRate
) implements DeviceConfiguration {

    @Override
    public CharacterDevice getDevice() {
        return device;
    }

}
