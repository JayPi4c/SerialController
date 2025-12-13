package de.jaypi4c;

public enum Protocol{
        CMD_ECHO((byte) 0x01),
        CMD_BUTTON((byte) 0x02),
        CMD_LED((byte) 0x03),
        CMD_LCD((byte) 0x04);
        
        
        private final byte code;
        
        public byte getCode(){
            return code;
        }
        Protocol(byte code) {
            this.code = code;
        }
    }