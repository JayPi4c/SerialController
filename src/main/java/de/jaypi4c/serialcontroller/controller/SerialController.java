package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.view.SerialControllerFrame;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.Channel;
import org.schlunzis.jduino.ChannelMessageListener;
import org.schlunzis.jduino.proto.tlv.TLVMessage;
import org.schlunzis.jduino.simple.SimpleProtocol;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SerialController {

    private final Channel<?> communicator;

    public SerialController(Channel<?> communicator, SerialControllerFrame frame) {
        this.communicator = communicator;
        frame.getOffBtn().addActionListener(getOffBtnListener());
        frame.getOnBtn().addActionListener(getOnBtnListener());
        frame.addWindowListener(getWindowListener());
        communicator.addMessageListener(getMessageProcessor(frame.getTextArea()));

        new ConnectionPanelController(communicator, frame.getConnectionPanel());
        new MessagePanelController(communicator, frame.getMessagePanel());
    }

    private ChannelMessageListener<?> getMessageProcessor(JTextComponent textComponent) {
        return (message) -> textComponent.setText(textComponent.getText() + new String(message.value(), StandardCharsets.UTF_8) + "\n");
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
        return _ -> {
            byte[] ledPayload = new byte[2];
            ledPayload[0] = 13; // LED pin
            ledPayload[1] = 0;  // OFF
            communicator.sendMessage(new TLVMessage(SimpleProtocol.CMD_LED.getCode(), (byte) ledPayload.length, ledPayload));
        };
    }

    private ActionListener getOnBtnListener() {
        return _ -> {
            byte[] ledPayload = new byte[2];
            ledPayload[0] = 13; // LED pin
            ledPayload[1] = 1;  // ON
            communicator.sendMessage(new TLVMessage(SimpleProtocol.CMD_LED.getCode(), (byte) ledPayload.length, ledPayload));
        };
    }

}
