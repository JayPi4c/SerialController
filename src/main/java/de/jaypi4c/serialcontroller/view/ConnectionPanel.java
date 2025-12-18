package de.jaypi4c.serialcontroller.view;

import lombok.Getter;
import org.schlunzis.jduino.channel.serial.SerialDevice;

import javax.swing.*;
import java.awt.*;

@Getter
public class ConnectionPanel extends JPanel {

    private final JButton connectButton;
    private final JComboBox<SerialDevice> connections;

    public ConnectionPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        connections = new JComboBox<>();
        add(connections);
        connectButton = new JButton("Connect");
        add(connectButton);
    }
}
