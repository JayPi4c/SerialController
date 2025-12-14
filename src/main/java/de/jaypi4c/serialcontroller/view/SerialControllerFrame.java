package de.jaypi4c.serialcontroller.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class SerialControllerFrame extends JFrame {

    private final JButton onBtn;
    private final JButton offBtn;
    private final ConnectionPanel connectionPanel;

    public SerialControllerFrame() {
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        setLayout(new BorderLayout());

        connectionPanel = new ConnectionPanel();
        add(connectionPanel, BorderLayout.NORTH);

        onBtn = new JButton("On");
        offBtn = new JButton("Off");
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        actionPanel.add(onBtn);
        actionPanel.add(offBtn);
        add(actionPanel, BorderLayout.SOUTH);

        pack();
        setVisible(true);
    }

}
