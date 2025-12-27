package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDevice;
import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDeviceConfiguration;
import de.jaypi4c.serialcontroller.protocol.ltv.LTV;
import de.jaypi4c.serialcontroller.view.ConnectionPanel;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.Device;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Slf4j
public class ConnectionPanelController {

    private final ConnectionPanel connectionPanel;
    private final Channel<LTV> communicator;

    public ConnectionPanelController(Channel<LTV> communicator, ConnectionPanel connectionPanel) {
        this.connectionPanel = connectionPanel;
        this.communicator = communicator;
        communicator.getDevices().forEach(connectionPanel.getConnections()::addItem);
        connectionPanel.getConnections().setRenderer(getPortRenderer());
        connectionPanel.getConnectButton().addActionListener(getConnectButtonListener());
    }

    private Device getSelectedPort() {
        return (Device) connectionPanel.getConnections().getSelectedItem();
    }

    private ActionListener getConnectButtonListener() {
        return _ -> {
            CharacterDevice selectedPort = (CharacterDevice) getSelectedPort();
            if (selectedPort != null) {
                log.debug("Connecting to port {}", selectedPort);
                communicator.open(new CharacterDeviceConfiguration(selectedPort));
            } else {
                log.warn("No port selected!");
            }
        };
    }

    private ListCellRenderer<Object> getPortRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                return super.getListCellRendererComponent(list, ((Device) value).getDisplayName(), index, isSelected, cellHasFocus);
            }
        };
    }
}
