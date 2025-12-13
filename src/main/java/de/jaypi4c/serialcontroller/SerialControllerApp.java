package de.jaypi4c.serialcontroller;

import de.jaypi4c.serialcontroller.communication.Communicator;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

/**
 * SerialController allows to communicate with and control an Arduino via a Java application
 *
 */
@Slf4j
public class SerialControllerApp {

    static void main() throws InterruptedException {
        Arrays.stream(Communicator.getPorts()).forEach(port -> System.out.println(port.getSystemPortName() + " - " + port.getDescriptivePortName() + " - " + port.getSystemPortPath()));

        Communicator communicator = new Communicator("/dev/ttyACM0", 250000);

        if (!communicator.isConnected())
            return;

        communicator.sendEchoCommand("Hello");
        communicator.sendLCDCommand("World");
        communicator.sendLEDCommand(13, true);
        Thread.sleep(1000);
        communicator.sendLEDCommand(13, false);
    }

}
