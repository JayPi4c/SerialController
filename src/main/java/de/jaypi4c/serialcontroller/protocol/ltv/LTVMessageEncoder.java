package de.jaypi4c.serialcontroller.protocol.ltv;

import org.schlunzis.jduino.protocol.Message;
import org.schlunzis.jduino.protocol.MessageEncoder;

public class LTVMessageEncoder implements MessageEncoder<LTV> {
    @Override
    public byte[] encode(Message<LTV> m) {
        if (!(m instanceof LTVMessage(byte type, byte[] value))) {
            throw new IllegalArgumentException("Invalid message type");
        }
        if (value.length > 255) {
            throw new IllegalArgumentException("Message payload too long");
        }

        byte[] encoded = new byte[2 + value.length];
        encoded[0] = (byte) value.length;
        encoded[1] = type;
        System.arraycopy(value, 0, encoded, 2, value.length);
        return encoded;
    }
}
