package de.jaypi4c.serialcontroller.model;

import de.jaypi4c.serialcontroller.agnostic.ChannelWrapper;
import de.jaypi4c.serialcontroller.channel.characterdevice.CharacterDeviceChannel;
import de.jaypi4c.serialcontroller.protocol.mytlv.MyTLV;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.ChannelMessageListener;
import org.schlunzis.jduino.protocol.Protocol;

@Slf4j
public class Communicator {


    @Getter
    private final ChannelWrapper channel;
    @Getter
    @Setter
    private int ledPin;

    @Getter
    private Protocol protocol;

    public Communicator() {
        channel = new ChannelWrapper();
        channel.setChannelFactory(CharacterDeviceChannel::new);
        setProtocol(new MyTLV());
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
        log.info("Setting protocol to {}", protocol.getClass().getSimpleName());
        channel.setProtocol(protocol);
    }

    public void setChannelFactory(Channel.ChannelFactory<Protocol, Channel> channelFactory) {
        this.channel.setChannelFactory(channelFactory);
    }

    public void addMessageListener(ChannelMessageListener listener) {
        this.channel.addMessageListener(listener);
    }

}
