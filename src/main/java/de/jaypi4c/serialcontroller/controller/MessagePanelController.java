package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.model.Communicator;
import de.jaypi4c.serialcontroller.view.MessagePanel;
import org.schlunzis.jduino.protocol.tlv.TLVMessage;

import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class MessagePanelController {

    private final Communicator communicator;
    private final MessagePanel messagePanel;

    public MessagePanelController(Communicator communicator, MessagePanel messagePanel) {
        this.communicator = communicator;
        this.messagePanel = messagePanel;
        messagePanel.getSendButton().addActionListener(getSendActionListener());
        messagePanel.getMessageField().addActionListener(getSendActionListener());
    }

    private ActionListener getSendActionListener() {
        return _ -> {
            communicator.getChannel().sendMessage(new TLVMessage((byte) 1, messagePanel.getMessageField().getText().getBytes(StandardCharsets.UTF_8)));
            messagePanel.getMessageField().setText("");
        };
    }

}
