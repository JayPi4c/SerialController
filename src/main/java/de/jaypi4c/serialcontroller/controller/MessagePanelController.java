package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.view.MessagePanel;
import org.schlunzis.jduino.Channel;
import org.schlunzis.jduino.proto.tlv.TLV;
import org.schlunzis.jduino.proto.tlv.TLVMessage;
import org.schlunzis.jduino.simple.SimpleProtocol;

import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;

public class MessagePanelController {

    private final Channel<TLV> communicator;
    private final MessagePanel messagePanel;

    public MessagePanelController(Channel<TLV> communicator, MessagePanel messagePanel) {
        this.communicator = communicator;
        this.messagePanel = messagePanel;
        messagePanel.getSendButton().addActionListener(getSendActionListener());
    }

    private ActionListener getSendActionListener() {
        return _ -> communicator.sendMessage(new TLVMessage(SimpleProtocol.CMD_ECHO.getCode(), (byte) messagePanel.getMessageField().getText().getBytes(StandardCharsets.UTF_8).length, messagePanel.getMessageField().getText().getBytes(StandardCharsets.UTF_8)));
    }

}
