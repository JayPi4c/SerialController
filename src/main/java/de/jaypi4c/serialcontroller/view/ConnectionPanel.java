package de.jaypi4c.serialcontroller.view;

import com.fazecast.jSerialComm.SerialPort;
import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class ConnectionPanel extends JPanel {

    private final JButton connectButton;
    private final JComboBox<SerialPort> connections;

    public ConnectionPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        connections = new JComboBox<>();
        add(connections);
        connectButton = new JButton("Connect");
        add(connectButton);
    }
}
