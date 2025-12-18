package de.jaypi4c.serialcontroller.protocol.ltv;

import org.schlunzis.jduino.protocol.MessageDecoder;
import org.schlunzis.jduino.protocol.MessageEncoder;
import org.schlunzis.jduino.protocol.Protocol;

public class LTV implements Protocol<LTV> {

    private final MessageEncoder<LTV> messageEncoder = new LTVMessageEncoder();
    private final MessageDecoder<LTV> messageDecoder = new LTVMessageDecoder();

    @Override
    public MessageEncoder<LTV> getMessageEncoder() {
        return messageEncoder;
    }

    @Override
    public MessageDecoder<LTV> getMessageDecoder() {
        return messageDecoder;
    }
}
