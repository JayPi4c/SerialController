package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.view.MessagePanel;
import org.schlunzis.jduino.Communicator;
import org.schlunzis.jduino.commuication.SimpleCommunicator;

import java.awt.event.ActionListener;

public class MessagePanelController {

    private final Communicator communicator;
    private final MessagePanel messagePanel;

    public MessagePanelController(Communicator communicator, MessagePanel messagePanel) {
        this.communicator = communicator;
        this.messagePanel = messagePanel;
        messagePanel.getSendButton().addActionListener(getSendActionListener());
    }

    private ActionListener getSendActionListener() {
        return _ -> ((SimpleCommunicator) communicator).sendEchoCommand(messagePanel.getMessageField().getText());
    }

}
