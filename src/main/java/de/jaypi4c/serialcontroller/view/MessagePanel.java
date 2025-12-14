package de.jaypi4c.serialcontroller.view;

import lombok.Getter;

import javax.swing.*;

@Getter
public class MessagePanel extends JPanel {

    private final JTextField messageField;
    private final JButton sendButton;

    public MessagePanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        messageField = new JTextField(20);
        sendButton = new JButton("Send");

        add(messageField);
        add(sendButton);
    }


}

