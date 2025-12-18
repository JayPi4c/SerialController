package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.view.SerialControllerFrame;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.ChannelMessageListener;
import org.schlunzis.jduino.protocol.tlv.TLV;
import org.schlunzis.jduino.protocol.tlv.TLVMessage;
import org.schlunzis.jduino.simple.SimpleChannel;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SerialController {

    private final SimpleChannel communicator;

    public SerialController(SimpleChannel communicator, SerialControllerFrame frame) {
        this.communicator = communicator;
        frame.getOffBtn().addActionListener(getOffBtnListener());
        frame.getOnBtn().addActionListener(getOnBtnListener());
        frame.addWindowListener(getWindowListener());
        communicator.addMessageListener(getMessageProcessor(frame.getTextArea()));

        new ConnectionPanelController(communicator, frame.getConnectionPanel());
        new MessagePanelController(communicator, frame.getMessagePanel());
    }

    private ChannelMessageListener<TLV> getMessageProcessor(JTextComponent textComponent) {
        return (message) -> textComponent.setText(textComponent.getText() + new String(((TLVMessage)message).value(), StandardCharsets.UTF_8) + "\n");
    }

    private WindowListener getWindowListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                finish();
            }
        };
    }

    private void finish() {
        communicator.close();
        log.debug("Closing serial controller");
    }

    private ActionListener getOffBtnListener() {
        return _ -> communicator.sendLEDCommand(13, false);
    }

    private ActionListener getOnBtnListener() {
        return _ -> communicator.sendLEDCommand(13, true);
    }

}
