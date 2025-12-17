package de.jaypi4c.serialcontroller.proto.ltv;

import org.schlunzis.jduino.proto.MessageDecoder;

import java.io.ByteArrayOutputStream;

public class LTVMessageDecoder implements MessageDecoder<LTV> {

    private final ByteArrayOutputStream payload = new ByteArrayOutputStream();
    private byte type = -1;
    private int expectedLength = -1;

    public void pushNextByte(byte next) {
        if (expectedLength < 0) {
            // First byte = message length
            expectedLength = next & 0xFF;  // convert to unsigned
            payload.reset();
        } else if (type < 0) {
            // Second byte = message length
            type = next;
        } else {
            // Subsequent bytes = payload
            payload.write(next);
        }
    }

    public boolean isMessageComplete() {
        return type >= 0 && expectedLength >= 0 && payload.size() == expectedLength;
    }

    public LTVMessage getDecodedMessage() {
        LTVMessage message = new LTVMessage((byte) expectedLength, type, payload.toByteArray());
        reset();
        return message;
    }

    private void reset() {
        type = -1;
        expectedLength = -1;
        payload.reset();
    }

}
