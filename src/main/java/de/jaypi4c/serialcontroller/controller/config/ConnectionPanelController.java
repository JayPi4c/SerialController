package de.jaypi4c.serialcontroller.controller.config;

import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDevice;
import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDeviceConfiguration;
import de.jaypi4c.serialcontroller.model.Communicator;
import de.jaypi4c.serialcontroller.view.connect.ConnectionPanel;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.DeviceConfiguration;

import java.awt.event.ActionListener;

@Slf4j
public class ConnectionPanelController {

    private final ConnectionPanel configurationPanel;
    private final Communicator communicator;

    public ConnectionPanelController(Communicator communicator, ConnectionPanel connectionPanel) {
        this.configurationPanel = connectionPanel;
        this.communicator = communicator;
      //  connectionPanel.addDevices(communicator.getChannel().getDevices());
        connectionPanel.getConnectButton().addActionListener(getConnectButtonListener());
    }

    private ActionListener getConnectButtonListener() {
        return _ -> {
            DeviceConfiguration dc =new CharacterDeviceConfiguration(new CharacterDevice("Character Device", "/dev/ttyACM0", 9600));
            if (dc != null) {
                log.debug("Connecting to port {}", dc);
                communicator.getChannel().open(dc);
            } else {
                log.warn("No port selected!");
            }
        };
    }

}
