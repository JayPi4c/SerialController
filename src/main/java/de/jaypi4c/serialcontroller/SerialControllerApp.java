package de.jaypi4c.serialcontroller;

import de.jaypi4c.serialcontroller.controller.SerialController;
import de.jaypi4c.serialcontroller.view.SerialControllerFrame;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.protocol.tlv.TLV;
import org.schlunzis.jduino.simple.SimpleChannel;

import javax.swing.*;

/**
 * SerialController allows to communicate with and control an Arduino via a Java application
 *
 */
@Slf4j
public class SerialControllerApp {

    static void main() {
        SimpleChannel conn = Channel.builder().protocol(new TLV()).channelFactory(SimpleChannel::new).build();
        SwingUtilities.invokeLater(() -> new SerialController(conn, new SerialControllerFrame()));
    }

}
