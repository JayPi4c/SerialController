package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.protocol.ltv.LTV;
import de.jaypi4c.serialcontroller.view.ConnectionPanel;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.serial.SerialDevice;
import org.schlunzis.jduino.channel.serial.SerialDeviceConfiguration;

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

    private SerialDevice getSelectedPort() {
        return (SerialDevice) connectionPanel.getConnections().getSelectedItem();
    }

    private ActionListener getConnectButtonListener() {
        return _ -> {
            SerialDevice selectedPort = getSelectedPort();
            if (selectedPort != null) {
                log.debug("Connecting to port {}", selectedPort.portPath());
                communicator.open(new SerialDeviceConfiguration(selectedPort, 250000));
            } else {
                log.warn("No port selected!");
            }
        };
    }

    private ListCellRenderer<Object> getPortRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                SerialDevice serialPort = (SerialDevice) value;
                String disp;
                if (index == -1)
                    disp = serialPort.getDisplayName();
                else
                    disp = serialPort.portPath();
                return super.getListCellRendererComponent(list, disp, index, isSelected, cellHasFocus);
            }
        };
    }

}
