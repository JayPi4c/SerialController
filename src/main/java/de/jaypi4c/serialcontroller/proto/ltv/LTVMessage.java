package de.jaypi4c.serialcontroller.proto.ltv;

import org.schlunzis.jduino.proto.Message;

public record LTVMessage(
        byte length,
        byte type,
        byte[] value
) implements Message<LTV> {
    static LTVMessage fromString(String str) {
        String[] parts = str.split(":", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid LTVMessage string format");
        }
        byte length = Byte.parseByte(parts[0]);
        byte type = Byte.parseByte(parts[1]);
        String[] valueParts = parts[2].split(",");
        byte[] value = new byte[valueParts.length];
        for (int i = 0; i < valueParts.length; i++) {
            value[i] = Byte.parseByte(valueParts[i]);
        }
        return new LTVMessage(length, type, value);
    }
}
