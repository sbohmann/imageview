package at.yeoman.imageview;

import java.awt.image.BufferedImage;
import java.util.Collections;
import java.util.List;

import javax.swing.*;

import at.yeoman.imageview.dataStorage.DataStorage;

class MainClass {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainClass::run);
    }

    private static void run() {
        setLookAndFeel();
        List<BufferedImage> images = loadImages();
        new MainWindow(images).show();
    }

    private static void setLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    private static List<BufferedImage> loadImages() {
        try {
            return DataStorage.load();
        } catch (Exception exception) {
            exception.printStackTrace();
            return Collections.emptyList();
        }
    }
}
