package de.jaypi4c.serialcontroller.controller;

import de.jaypi4c.serialcontroller.model.Communicator;
import de.jaypi4c.serialcontroller.view.SerialControllerFrame;
import lombok.extern.slf4j.Slf4j;

import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;

@Slf4j
public class SerialController {

    private final Communicator communicator;

    public SerialController(Communicator communicator, SerialControllerFrame frame) {
        this.communicator = communicator;
        frame.getOffBtn().addActionListener(getOffBtnListener());
        frame.getOnBtn().addActionListener(getOnBtnListener());
        frame.addWindowListener(getWindowListener());
        new ConnectionPanelController(communicator, frame.getConnectionPanel());
    }

    private WindowListener getWindowListener() {
        return new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent e) {
                finish();
            }
        };
    }

    private void finish() {
        communicator.close();
        log.debug("Closing serial controller");
    }

    private ActionListener getOffBtnListener() {
        return _ -> communicator.sendLEDCommand(13, false);
    }

    private ActionListener getOnBtnListener() {
        return _ -> communicator.sendLEDCommand(13, true);
    }

}
