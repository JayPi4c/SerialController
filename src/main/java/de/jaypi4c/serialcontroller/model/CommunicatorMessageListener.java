package de.jaypi4c.serialcontroller.model;

@FunctionalInterface
public interface CommunicatorMessageListener {

    void onMessageReceived(String message);

}
