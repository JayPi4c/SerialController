package de.jaypi4c.serialcontroller;

import de.jaypi4c.serialcontroller.controller.SerialController;
import de.jaypi4c.serialcontroller.view.SerialControllerFrame;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;

/**
 * SerialController allows to communicate with and control an Arduino via a Java application
 *
 */
@Slf4j
public class SerialControllerApp {

    static void main() {
        SwingUtilities.invokeLater(() -> new SerialController(new SerialControllerFrame()));
    }

}
