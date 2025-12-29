package de.jaypi4c.serialcontroller.controller.config;

import de.jaypi4c.serialcontroller.model.Communicator;
import de.jaypi4c.serialcontroller.view.connect.CharacterDevicePanel;
import de.jaypi4c.serialcontroller.view.connect.ConfigurationPanel;
import de.jaypi4c.serialcontroller.view.connect.ConnectionPanel;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.DeviceConfiguration;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ConnectionPanelController {

    private final ConnectionPanel connectionPanel;
    private final Communicator communicator;
    private final CardLayout layout;
    private final Map<String, ConfigurationPanel> configurationCards;
    private final List<String> configurationIDs;
    private String currentID = CharacterDevicePanel.ID;

    public ConnectionPanelController(Communicator communicator, ConnectionPanel connectionPanel) {
        this.connectionPanel = connectionPanel;
        layout = (CardLayout) this.connectionPanel.getConfigurationCards().getLayout();

        this.communicator = communicator;

        connectionPanel.getConnectButton().addActionListener(getConnectButtonListener());
        connectionPanel.getNextConfig().addActionListener(getNextConfigButtonListener());

        configurationCards = Arrays.stream(connectionPanel.getConfigurationCards().getComponents())
                .map(c -> ((ConfigurationPanel) c))
                .collect(Collectors.toMap(ConfigurationPanel::getID, Function.identity()));
        configurationIDs = new ArrayList<>(configurationCards.keySet());
        layout.show(connectionPanel.getConfigurationCards(), currentID);
        updateCardDevices(currentID);
    }


    private ActionListener getNextConfigButtonListener() {
        return _ -> {
            int currentIndex = configurationIDs.indexOf(currentID);
            int nextIndex = (currentIndex + 1) % configurationIDs.size();
            currentID = configurationIDs.get(nextIndex);
            updateCardDevices(currentID);
            layout.show(connectionPanel.getConfigurationCards(), currentID);
        };
    }

    private void updateCardDevices(String id) {
        ConfigurationPanel configPanel = configurationCards.get(id);
        communicator.resetChannel();
        communicator.setChannel(configPanel.getChannel());
        configPanel.clearDevices();
        configPanel.addDevices(communicator.getChannel().getDevices());
    }

    private ActionListener getConnectButtonListener() {
        return _ -> {
            ConfigurationPanel configPanel = configurationCards.get(currentID);
            DeviceConfiguration dc = configPanel.getCurrentConfiguration();
            if (dc != null) {
                log.debug("Connecting to port {}", dc);
                communicator.getChannel().open(dc);
                communicator.setLedPin(configPanel.getLEDPin());
            } else {
                log.warn("No port selected!");
            }
        };
    }

}
