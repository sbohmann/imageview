package at.yeoman.imageview;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

class MainViewKeyboardHandler {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    private Runnable zoomIn;
    private Runnable zoomOut;

    MainViewKeyboardHandler(JPanel panel, JTabbedPane tabbedPane) {
        this.panel = panel;
        this.tabbedPane = tabbedPane;
        setup();
    }

    void setZoomIn(Runnable zoomIn) {
        this.zoomIn = zoomIn;
    }

    void setZoomOut(Runnable zoomOut) {
        this.zoomOut = zoomOut;
    }

    private void setup() {
        panel.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent event) {
                handleKeyType(event.getKeyCode(), event.getModifiers());
            }
        });
    }

    private void handleKeyType(int keyCode, int modifiers) {
        if (modifiers == KeyEvent.VK_CONTROL) {
            switch (keyCode) {
            case KeyEvent.VK_PLUS:
            case KeyEvent.VK_EQUALS:
                call(zoomIn);
                break;
            case KeyEvent.VK_MINUS:
                call(zoomOut);
                break;
            }
        }
    }

    private void call(Runnable handler) {
        if (handler != null) {
            handler.run();
        }
    }
}
