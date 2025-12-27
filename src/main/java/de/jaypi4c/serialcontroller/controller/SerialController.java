package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.controller.config.ConnectionPanelController;
import de.jaypi4c.serialcontroller.model.Communicator;
import de.jaypi4c.serialcontroller.protocol.ltv.LTV;
import de.jaypi4c.serialcontroller.protocol.ltv.LTVMessage;
import de.jaypi4c.serialcontroller.view.SerialControllerFrame;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.ChannelMessageListener;

import javax.swing.text.JTextComponent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.nio.charset.StandardCharsets;

@Slf4j
public class SerialController {

    private final Communicator communicator;

    public SerialController(SerialControllerFrame frame) {
        this.communicator = new Communicator();
        frame.getOffBtn().addActionListener(getOffBtnListener());
        frame.getOnBtn().addActionListener(getOnBtnListener());
        frame.addWindowListener(getWindowListener());
        communicator.addMessageListener(getMessageProcessor(frame.getTextArea()));

        new ConnectionPanelController(communicator, frame.getConnectionPanel());
        new MessagePanelController(communicator, frame.getMessagePanel());
    }

    private ChannelMessageListener<LTV> getMessageProcessor(JTextComponent textComponent) {
        return (message) -> textComponent.setText(textComponent.getText() + new String(message.getPayload(), StandardCharsets.UTF_8) + "\n");
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
        communicator.getChannel().close();
        log.debug("Closing serial controller");
    }

    private ActionListener getOffBtnListener() {
        return _ -> {
            byte[] payload = new byte[2];
            payload[0] = (byte) 13;
            payload[1] = (byte) 0;
            communicator.getChannel().sendMessage(new LTVMessage((byte) 3, payload));
        };
    }

    private ActionListener getOnBtnListener() {
        return _ -> {
            byte[] payload = new byte[2];
            payload[0] = (byte) 13;
            payload[1] = (byte) 1;
            communicator.getChannel().sendMessage(new LTVMessage((byte) 3, payload));
        };
    }

}
