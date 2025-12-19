package de.jaypi4c.serialcontroller.channel.characterdevice;

import org.schlunzis.jduino.channel.DeviceConfiguration;

public record CharacterDeviceConfiguration(
        CharacterDevice device) implements DeviceConfiguration {

    @Override
    public CharacterDevice getDevice() {
        return device;
    }

}
