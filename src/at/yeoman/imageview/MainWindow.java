package at.yeoman.imageview;

import java.awt.image.BufferedImage;
import java.util.List;

import javax.swing.*;

class MainWindow {
    private final List<BufferedImage> images;
    private JFrame jframe;

    MainWindow(List<BufferedImage> images) {
        this.images = images;
    }

    void show() {
        createJframe();
        showJframe();
    }

    private void createJframe() {
        jframe = new JFrame("Image View");
        MainView mainView = new MainView(images);
        jframe.setContentPane(mainView.getComponent());
    }

    private void showJframe() {
        jframe.pack();
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
    }
}
