package de.jaypi4c.serialcontroller.protocol.ltv;

import lombok.Getter;
import org.schlunzis.jduino.protocol.Protocol;

@Getter
public class LTV implements Protocol {

    private final LTVMessageEncoder messageEncoder = new LTVMessageEncoder();
    private final LTVMessageDecoder messageDecoder = new LTVMessageDecoder();

}
