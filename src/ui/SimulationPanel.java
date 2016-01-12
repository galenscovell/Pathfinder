package ui;

import processing.*;
import util.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SimulationPanel extends JPanel implements Runnable {
    private Grid grid;
    private Thread thread;
    private SettingsPanel settingsPanel;

    private final int framerate = 120;
    private boolean running, gridSet, tracing;
    private ArrayList<Coordinate> pathLine;
    private double pathfindStart, totalruntime;

    public SimulationPanel(int x, int y) {
        setPreferredSize(new Dimension(x, y));
        this.setLayout(new BorderLayout());
        this.setFocusable(true);
        this.settingsPanel = new SettingsPanel(this, Constants.SCREEN_X, 40);
        this.grid = new Grid(this);

        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (!gridSet) {
                    Tile selected = grid.getTile(e.getX(), e.getY());
                    if (selected != null) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            selected.becomeWall();
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            grid.clearScan();
                            grid.selectEndTile(selected);
                        }
                    }
                }
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (!gridSet) {
                    Tile selected = grid.getTile(e.getX(), e.getY());
                    if (selected != null && SwingUtilities.isLeftMouseButton(e)) {
                            selected.becomeWall();
                    }
                }
            }
        });

        this.add(settingsPanel, BorderLayout.SOUTH);
    }

    public void startScan(String mode) {
        pathfindStart = System.currentTimeMillis();
        if (!gridSet) {
            grid.clearScan();
            tracing = false;
            if (grid.scanStart(mode)) {
                gridSet = true;
            }
        }
    }

    public void clear() {
        if (!gridSet) {
            grid.clearAll();
            tracing = false;
        }
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

    public void stopScan(Stack<Coordinate> path) {
        totalruntime = (System.currentTimeMillis() - pathfindStart) / 1000;
        settingsPanel.runtimeLabel.setText("Runtime: " + totalruntime + "s");
        gridSet = false;
        tracing = true;
        this.pathLine = new ArrayList<Coordinate>();
        while (!path.empty()) {
            pathLine.add(path.pop());
        }
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
        gfx.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON
        );

        // Clear screen
        gfx.setColor(Constants.BACKGROUND_COLOR);
        gfx.fillRect(0, 0, getWidth(), getHeight());
        // Render next frame
        grid.draw(gfx);
        // Trace path line
        if (tracing) {
            gfx.setColor(Constants.PATH_COLOR);
            gfx.setStroke(new BasicStroke(2));
            for (int i = 1; i < pathLine.size(); i++) {
                Coordinate p = pathLine.get(i - 1);
                Coordinate n = pathLine.get(i);
                gfx.drawLine(
                        (Constants.TILESIZE + Constants.MARGIN) * p.x + (Constants.TILESIZE / 2) + Constants.MARGIN,
                        (Constants.TILESIZE + Constants.MARGIN) * p.y + (Constants.TILESIZE / 2) + Constants.MARGIN,
                        (Constants.TILESIZE + Constants.MARGIN) * n.x + (Constants.TILESIZE / 2) + Constants.MARGIN,
                        (Constants.TILESIZE + Constants.MARGIN) * n.y + (Constants.TILESIZE / 2) + Constants.MARGIN);
            }
        }
    }
}
