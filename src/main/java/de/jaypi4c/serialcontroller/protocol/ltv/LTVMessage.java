package de.jaypi4c.serialcontroller.protocol.ltv;

import org.schlunzis.jduino.protocol.Message;

public record LTVMessage(byte type, byte[] value) implements Message<LTV> {
    @Override
    public byte getMessageType() {
        return type;
    }

    @Override
    public byte[] getPayload() {
        return value;
    }
}
