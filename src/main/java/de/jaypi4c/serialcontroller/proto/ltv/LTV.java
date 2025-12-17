package de.jaypi4c.serialcontroller.proto.ltv;

import org.schlunzis.jduino.proto.MessageDecoder;
import org.schlunzis.jduino.proto.MessageEncoder;
import org.schlunzis.jduino.proto.Protocol;

public class LTV implements Protocol<LTV> {
    @Override
    public MessageEncoder<LTV> getMessageEncoder() {
        return new LTVMessageEncoder();
    }

    @Override
    public MessageDecoder<LTV> getMessageDecoder() {
        return new LTVMessageDecoder();
    }
}
