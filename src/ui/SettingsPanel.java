package ui;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel extends JPanel {
    private JLabel label;

    public SettingsPanel(int x, int y) {
        setPreferredSize(new Dimension(x, y));

        Font customFont = new Font("Source Code Pro", Font.PLAIN, 12);
        this.label = new JLabel("Label", JLabel.CENTER);
        label.setFont(customFont);

        this.add(label);
    }
}
