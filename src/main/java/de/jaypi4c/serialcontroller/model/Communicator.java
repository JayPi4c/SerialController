package de.jaypi4c.serialcontroller.model;

import lombok.Getter;
import lombok.Setter;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.ChannelMessageListener;
import org.schlunzis.jduino.protocol.tlv.TLV;

import java.util.ArrayList;
import java.util.List;

public class Communicator {

    private final List<ChannelMessageListener<TLV>> messageListeners;

    @Getter
    private Channel<TLV> channel;
    @Getter
    @Setter
    private int ledPin;

    public Communicator() {
        messageListeners = new ArrayList<>();
    }

    public void addMessageListener(ChannelMessageListener<TLV> listener) {
        messageListeners.add(listener);
    }

    public void setChannel(Channel<TLV> channel) {
        resetChannel();
        this.channel = channel;
        messageListeners.forEach(channel::addMessageListener);
    }

    public void resetChannel() {
        if (channel != null)
            channel.close();
        channel = null;
    }

}
