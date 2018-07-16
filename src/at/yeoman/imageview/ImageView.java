package at.yeoman.imageview;

import java.awt.*;
import java.awt.image.BufferedImage;

import javax.swing.*;

class ImageView extends JPanel {
    private final BufferedImage image;
    private double zoom;

    ImageView(BufferedImage image) {
        this.image = image;
        zoom = 1.0;
        setPreferredSize();
    }

    private void setPreferredSize() {
        int width = zoomedSize(image.getWidth());
        int height = zoomedSize(image.getHeight());
        setPreferredSize(new Dimension(width, height));
    }

    private int zoomedSize(int width) {
        return (int) Math.ceil(zoom * width);
    }

    @Override
    public void paint(Graphics basicGraphics) {
        Graphics2D g = (Graphics2D) basicGraphics;
        int width = getWidth();
        int height = getHeight();
        int imageWidth = image.getWidth(null);
        int imageHeight = image.getHeight(null);
        if (imageWidth < width || imageHeight < height) {
            g.setColor(Color.black);
            g.fillRect(0, 0, width, height);
        }
        int x = width / 2 - imageWidth / 2;
        int y = height / 2 - imageHeight / 2;
        g.drawImage(image, x, y, null);
    }

    void zoomIn() {
        zoom *= 2.0;
    }

    void zoomOut() {
        zoom /= 2.0;
    }
}
