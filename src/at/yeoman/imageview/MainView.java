package at.yeoman.imageview;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.swing.*;

import at.yeoman.imageview.dataStorage.DataStorage;

class MainView {
    private JPanel panel;
    private JTabbedPane tabbedPane;

    private List<BufferedImage> images;

    MainView(List<BufferedImage> images) {
        this.images = new ArrayList<>(images);
        createPanel();
    }

    JComponent getComponent() {
        return panel;
    }

    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setPreferredSize(new Dimension(800, 600));
        createToolbar();
        createTabbedPane();
        addExistingImages();
        setupKeyboardHandling();
    }

    private void createToolbar() {
        ImageViewToolBar toolbar = new ImageViewToolBar();
        panel.add(toolbar.getComponent(), BorderLayout.NORTH);
        toolbar.setPasteHandler(this::paste);
    }

    private void paste() {
        ImageFromClipboard.load(this::addImage);
    }

    private void createTabbedPane() {
        tabbedPane = new JTabbedPane();
        panel.add(tabbedPane, BorderLayout.CENTER);
    }

    private void addImage(BufferedImage image) {
        images.add(image);
        addImageTab(image);
        saveImages();
    }

    private void saveImages() {
        SwingUtilities.invokeLater(() -> {
            try {
                DataStorage.save(images);
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        });
    }

    private void addExistingImages() {
        for (BufferedImage image : images) {
            addImageTab(image);
        }
    }

    private void addImageTab(BufferedImage image) {
        tabbedPane.addTab("Pasted", new JScrollPane(new ImageView(image)));
    }

    private void setupKeyboardHandling() {
        MainViewKeyboardHandler handler = new MainViewKeyboardHandler(panel, tabbedPane);
        handler.setZoomIn(this::zoomIn);
        handler.setZoomOut(this::zoomOut);
    }

    private void zoomIn() {
        callSelectedImageView(ImageView::zoomIn);
    }

    private void zoomOut() {
        callSelectedImageView(ImageView::zoomOut);
    }

    private void callSelectedImageView(Consumer<ImageView> code) {
        if (tabbedPane.getSelectedComponent() instanceof ImageView) {
            code.accept((ImageView) tabbedPane.getSelectedComponent());
        }
    }
}
