package de.jaypi4c.serialcontroller.model;

import java.util.ArrayList;
import java.util.List;

import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.ChannelMessageListener;

import de.jaypi4c.serialcontroller.protocol.ltv.LTV;
import lombok.Getter;

public class Communicator {
    @Getter
    private Channel<LTV> channel;

    private List<ChannelMessageListener<LTV>> messageListeners;

    public Communicator(){
        messageListeners = new ArrayList<>();
    }

    public void addMessageListener(ChannelMessageListener<LTV> listener){
        messageListeners.add(listener);
    }

    public void setChannel(Channel<LTV> channel){
        this.channel = channel;
        messageListeners.forEach(channel::addMessageListener);
    }

}
