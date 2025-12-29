package de.jaypi4c.serialcontroller.view.message;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

/// https://stackoverflow.com/a/20121567/13670629
public class ToastMessage extends JDialog {
    int milliseconds;

    public ToastMessage(String toastString, int time, Component owner) {
        this.milliseconds = time;
        setUndecorated(true);
        getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBackground(Color.GRAY);
        panel.setBorder(new LineBorder(Color.LIGHT_GRAY, 2));
        getContentPane().add(panel, BorderLayout.CENTER);

        JLabel toastLabel = new JLabel("");
        toastLabel.setText(toastString);
        toastLabel.setFont(new Font("Dialog", Font.BOLD, 12));
        toastLabel.setForeground(Color.WHITE);

        setBounds(100, 100, toastLabel.getPreferredSize().width + 20, 31);

        setAlwaysOnTop(true);
        setLocationRelativeTo(owner);
        panel.add(toastLabel);
        setVisible(false);

        new Timer(milliseconds, e -> {
            ((Timer) e.getSource()).stop();
            dispose();
        }).start();
    }
}