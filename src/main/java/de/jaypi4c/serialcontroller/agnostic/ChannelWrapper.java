package de.jaypi4c.serialcontroller.agnostic;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.schlunzis.jduino.channel.AbstractChannel;
import org.schlunzis.jduino.channel.Channel;
import org.schlunzis.jduino.channel.Device;
import org.schlunzis.jduino.channel.DeviceConfiguration;
import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.Protocol;

import java.util.List;

@Slf4j
public class ChannelWrapper extends AbstractChannel {

    @Setter
    private ChannelFactory<Protocol, Channel> channelFactory;
    private Channel channel;

    public ChannelWrapper() {
    }

    public void setProtocol(Protocol protocol) {
        if (this.channel != null) {
            this.channel.close();
        }
        if (channelFactory != null) {
            this.channel = channelFactory.createChannel(protocol);
            log.info("Adding {} listeners to new channel", channelMessageListeners.size());
            channelMessageListeners.forEach(channel::addMessageListener);
            channel.addMessageListener(m -> log.debug("Received message: Type={}, Payload={}", m.getMessageType(), m.getPayload()));
        } else {
            throw new IllegalStateException("ChannelBuilder is not set. Cannot create channel.");
        }
    }

    @Override
    public void open(DeviceConfiguration deviceConfiguration) {
        if (this.channel == null) {
            throw new IllegalStateException("Protocol not set. Call setProtocol() before opening the channel.");
        }
        this.channel.open(deviceConfiguration);
    }

    @Override
    public void close() {
        if (this.channel != null) {
            this.channel.close();
        }
        this.channel = null;
    }

    @Override
    public void sendMessage(Message message) {
        if (this.channel == null) {
            throw new IllegalStateException("Channel is not open. Cannot send message.");
        }
        this.channel.sendMessage(message);
    }

    @Override
    public List<? extends Device> getDevices() {
        if (this.channel == null) {
            throw new IllegalStateException("Channel is not open. Cannot get devices.");
        }

        return this.channel.getDevices();
    }

    @Override
    public boolean isConnected() {
        return this.channel != null && this.channel.isConnected();
    }
}
