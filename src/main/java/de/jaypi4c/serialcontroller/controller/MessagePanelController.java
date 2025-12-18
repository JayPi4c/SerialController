package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.view.MessagePanel;
import org.schlunzis.jduino.simple.SimpleChannel;

import java.awt.event.ActionListener;

public class MessagePanelController {

    private final SimpleChannel communicator;
    private final MessagePanel messagePanel;

    public MessagePanelController(SimpleChannel communicator, MessagePanel messagePanel) {
        this.communicator = communicator;
        this.messagePanel = messagePanel;
        messagePanel.getSendButton().addActionListener(getSendActionListener());
    }

    private ActionListener getSendActionListener() {
        return _ -> communicator.sendEchoCommand(messagePanel.getMessageField().getText());
    }

}
