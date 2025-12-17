package de.jaypi4c.serialcontroller.controller;

import com.fazecast.jSerialComm.SerialPort;
import de.jaypi4c.serialcontroller.view.ConnectionPanel;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.Channel;
import org.schlunzis.jduino.proto.tlv.TLV;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;

@Slf4j
public class ConnectionPanelController {

    private final ConnectionPanel connectionPanel;
    private final Channel<TLV> communicator;

    public ConnectionPanelController(Channel<TLV> communicator, ConnectionPanel connectionPanel) {
        this.connectionPanel = connectionPanel;
        this.communicator = communicator;
        Arrays.stream(communicator.getPorts()).forEach(connectionPanel.getConnections()::addItem);
        connectionPanel.getConnections().setRenderer(getPortRenderer());
        connectionPanel.getConnectButton().addActionListener(getConnectButtonListener());
    }

    private SerialPort getSelectedPort() {
        return (SerialPort) connectionPanel.getConnections().getSelectedItem();
    }

    private ActionListener getConnectButtonListener() {
        return _ -> {
            SerialPort selectedPort = getSelectedPort();
            if (selectedPort != null) {
                log.debug("Connecting to port {}", selectedPort.getSystemPortPath());
                communicator.open(selectedPort.getSystemPortPath(), 250000);
            } else {
                log.warn("No port selected!");
            }
        };
    }

    private ListCellRenderer<Object> getPortRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                SerialPort serialPort = (SerialPort) value;
                String disp;
                if (index == -1)
                    disp = serialPort.getSystemPortName();
                else
                    disp = serialPort.getSystemPortPath();
                return super.getListCellRendererComponent(list, disp, index, isSelected, cellHasFocus);
            }
        };
    }

}
