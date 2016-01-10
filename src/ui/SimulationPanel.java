package ui;

import processing.Grid;
import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationPanel extends JPanel implements Runnable {
    private Grid grid;
    private Thread thread;
    private JLabel label;

    private final int framerate = 60;
    private boolean running = false;
    private int cellsize, margin;
    private double runTimeStart;

    public SimulationPanel(int x, int y) {
        setPreferredSize(new Dimension(x, y));
        this.grid = new Grid();
        Font customFont = new Font("Source Code Pro", Font.PLAIN, 12);
        this.label = new JLabel("Label", JLabel.CENTER);
        label.setPreferredSize(new Dimension(x - 200, 30));
        label.setBackground(Color.WHITE);
        label.setFont(customFont);
        label.setOpaque(true);

        this.add(label);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                label.setText("X: " + e.getX() + ", Y: " + e.getY());
            }
        });
    }

    public void run() {
        long start, end, sleepTime;
        this.runTimeStart = System.currentTimeMillis();

        while (running) {
            start = System.currentTimeMillis();

            // percolation.open();
            // percolation.analyzeFlow();
            repaint();
            // if (percolation.percolates()) {
            //     updateLabels();
            //     stop();
            // }

            end = System.currentTimeMillis();
            // Sleep to match FPS limit
            sleepTime = (1000 / framerate) - (end - start);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    thread.interrupt();
                }
            }
        }
    }

    public synchronized void start() {
        this.thread = new Thread(this, "Pathfinder");
        this.running = true;
        thread.start(); // call run()
    }

    public synchronized void stop() {
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            thread.interrupt();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D gfx = (Graphics2D) g;

        // Clear screen
        gfx.setColor(Constants.BACKGROUND_COLOR);
        gfx.fillRect(0, 0, getWidth(), getHeight());
        // Render next frame
        grid.draw(gfx);
    }

}
