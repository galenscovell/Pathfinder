package ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SettingsPanel extends JPanel implements ActionListener {
    private SimulationPanel rootPanel;
    private JLabel label;
    private JButton manhattanButton, euclideanButton, chebyshevButton, clearButton;

    public SettingsPanel(SimulationPanel rootPanel, int x, int y) {
        this.rootPanel = rootPanel;
        setPreferredSize(new Dimension(x, y));

        Font smallFont = new Font("Source Code Pro", Font.PLAIN, 12);
        Dimension buttonSize = new Dimension(100, 20);

        this.manhattanButton = new JButton("Manhattan");
        manhattanButton.setFocusPainted(false);
        manhattanButton.setEnabled(true);
        manhattanButton.setActionCommand("manhattan");
        manhattanButton.addActionListener(this);
        manhattanButton.setPreferredSize(buttonSize);
        manhattanButton.setFont(smallFont);

        this.euclideanButton = new JButton("Euclidean");
        euclideanButton.setFocusPainted(false);
        euclideanButton.setEnabled(true);
        euclideanButton.setActionCommand("euclidean");
        euclideanButton.addActionListener(this);
        euclideanButton.setPreferredSize(buttonSize);
        euclideanButton.setFont(smallFont);

        this.chebyshevButton = new JButton("Chebyshev");
        chebyshevButton.setFocusPainted(false);
        chebyshevButton.setEnabled(true);
        chebyshevButton.setActionCommand("chebyshev");
        chebyshevButton.addActionListener(this);
        chebyshevButton.setPreferredSize(buttonSize);
        chebyshevButton.setFont(smallFont);

        this.clearButton = new JButton("Clear");
        clearButton.setFocusPainted(false);
        clearButton.setEnabled(true);
        clearButton.setActionCommand("clear");
        clearButton.addActionListener(this);
        clearButton.setPreferredSize(buttonSize);
        clearButton.setFont(smallFont);

        this.add(manhattanButton);
        this.add(euclideanButton);
        this.add(chebyshevButton);
        this.add(clearButton);
    }

    public void actionPerformed(ActionEvent event) {
        String command = event.getActionCommand();
        if (command == "clear") {
            rootPanel.clear();
        } else {
            rootPanel.startScan(command);
        }
    }
}
