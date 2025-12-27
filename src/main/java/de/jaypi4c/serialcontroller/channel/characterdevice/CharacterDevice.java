package de.jaypi4c.serialcontroller.channel.characterdevice;

import org.schlunzis.jduino.channel.Device;

public record CharacterDevice(
        String portName,
        String portPath,
        int baud
) implements Device {

    @Override
    public String getDisplayName() {
        return portName;
    }

}
