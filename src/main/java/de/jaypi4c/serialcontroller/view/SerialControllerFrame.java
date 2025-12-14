package de.jaypi4c.serialcontroller.view;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class SerialControllerFrame extends JFrame {

    private final JButton onBtn;
    private final JButton offBtn;
    private final ConnectionPanel connectionPanel;
    private final JTextArea textArea;
    private final MessagePanel messagePanel;

    public SerialControllerFrame() {
        setPreferredSize(new Dimension(800, 600));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("SerialController");

        setLayout(new BorderLayout());

        connectionPanel = new ConnectionPanel();
        add(connectionPanel, BorderLayout.NORTH);

        textArea = new JTextArea();
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane, BorderLayout.CENTER);

        messagePanel = new MessagePanel();
        add(messagePanel, BorderLayout.EAST);

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
