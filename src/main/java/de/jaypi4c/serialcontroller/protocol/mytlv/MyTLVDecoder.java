package de.jaypi4c.serialcontroller.protocol.mytlv;

import org.schlunzis.jduino.protocol.tlv.TLVMessageDecoder;

public class MyTLVDecoder extends TLVMessageDecoder {

    @Override
    public void pushNextByte(byte next) {
        System.out.println("Pushed byte: " + next);
        super.pushNextByte(next);
    }
}
