package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.protocol.ltv.LTV;
import de.jaypi4c.serialcontroller.protocol.ltv.LTVMessage;
import de.jaypi4c.serialcontroller.view.MessagePanel;
import org.schlunzis.jduino.channel.Channel;

import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class MessagePanelController {

    private final Channel<LTV> communicator;
    private final MessagePanel messagePanel;

    public MessagePanelController(Channel<LTV> communicator, MessagePanel messagePanel) {
        this.communicator = communicator;
        this.messagePanel = messagePanel;
        messagePanel.getSendButton().addActionListener(getSendActionListener());
        messagePanel.getMessageField().addActionListener(getSendActionListener());
    }

    private ActionListener getSendActionListener() {
        return _ -> {
            communicator.sendMessage(new LTVMessage((byte) 1, messagePanel.getMessageField().getText().getBytes(StandardCharsets.UTF_8)));
            messagePanel.getMessageField().setText("");
        };
    }

}
