package de.jaypi4c.serialcontroller.view.connect;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class ConnectionPanel extends JPanel {

    private final JButton connectButton;
    private final JButton nextConfig;
    private final JPanel controlPanel;

    private final CharacterDevicePanel characterDevicePanel;
    private final SerialLibraryPanel serialLibraryPanel;
    private final JPanel configurationCards;

    public ConnectionPanel() {
        this.connectButton = new JButton("connect");
        this.nextConfig = new JButton(">");
        this.controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        controlPanel.add(connectButton);
        controlPanel.add(nextConfig);

        characterDevicePanel = new CharacterDevicePanel();
        serialLibraryPanel = new SerialLibraryPanel();

        configurationCards = new JPanel(new CardLayout());
        configurationCards.add(characterDevicePanel, CharacterDevicePanel.ID);
        configurationCards.add(serialLibraryPanel, SerialLibraryPanel.ID);

        setLayout(new BorderLayout());
        add(configurationCards, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
    }

}
