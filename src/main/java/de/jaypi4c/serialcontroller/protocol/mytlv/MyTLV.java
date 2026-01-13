package de.jaypi4c.serialcontroller.protocol.mytlv;

import org.schlunzis.jduino.protocol.MessageDecoder;
import org.schlunzis.jduino.protocol.tlv.TLV;

public class MyTLV extends TLV {

    private final MyTLVDecoder messageDecoder = new MyTLVDecoder();

    @Override
    public MessageDecoder getMessageDecoder() {
        return messageDecoder;
    }
}
