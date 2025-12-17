package de.jaypi4c.serialcontroller.proto.ltv;

import org.schlunzis.jduino.proto.Message;
import org.schlunzis.jduino.proto.MessageEncoder;

public class LTVMessageEncoder implements MessageEncoder<LTV> {

    @Override
    public byte[] encode(Message<LTV> m) {
        if (!(m instanceof LTVMessage message)) {
            throw new IllegalArgumentException("Invalid message type");
        }
        if (message.value().length > 255) {
            throw new IllegalArgumentException("Message payload too long");
        }

        byte[] encoded = new byte[2 + message.value().length];
        encoded[0] = message.type();
        encoded[1] = (byte) message.value().length;
        System.arraycopy(message.value(), 0, encoded, 2, message.value().length);
        return encoded;
    }

}
