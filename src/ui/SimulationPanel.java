package ui;

import processing.*;
import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SimulationPanel extends JPanel implements Runnable {
    private Grid grid;
    private Thread thread;

    private final int framerate = 30;
    private boolean running, gridSet;

    public SimulationPanel(int x, int y) {
        setPreferredSize(new Dimension(x, y));
        this.setFocusable(true);
        this.grid = new Grid(this);

        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    grid.clear();
                } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    grid.scanStart();
                    gridSet = true;
                }
            }
        });

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                Tile selected = grid.getTile(e.getX(), e.getY());
                if (selected != null) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        selected.becomeWall();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        selected.becomeFloor();
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Tile selected = grid.getTile(e.getX(), e.getY());
                if (selected != null) {
                    if (SwingUtilities.isLeftMouseButton(e)) {
                        selected.becomeWall();
                    } else if (SwingUtilities.isRightMouseButton(e)) {
                        selected.becomeFloor();
                    }
                }
            }
        });
    }

    public void run() {
        long start, end, sleepTime;

        while (running) {
            start = System.currentTimeMillis();
            if (gridSet) {
                grid.scanStep();
            }
            repaint();
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

    public void stopScan() {
        gridSet = false;
    }

    public synchronized void start() {
        this.thread = new Thread(this, "Pathfinder");
        running = true;
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
