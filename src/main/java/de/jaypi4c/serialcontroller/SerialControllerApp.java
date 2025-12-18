package de.jaypi4c.serialcontroller;

import de.jaypi4c.serialcontroller.controller.SerialController;
import de.jaypi4c.serialcontroller.protocol.ltv.LTV;
import de.jaypi4c.serialcontroller.view.SerialControllerFrame;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.serial.SerialChannel;

import javax.swing.*;

/**
 * SerialController allows to communicate with and control an Arduino via a Java application
 *
 */
@Slf4j
public class SerialControllerApp {

    static void main() {
        Channel<LTV> connection = Channel.builder().protocol(new LTV()).channelFactory(SerialChannel::new).build();
        SwingUtilities.invokeLater(() -> new SerialController(connection, new SerialControllerFrame()));
    }

}
