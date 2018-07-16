package at.yeoman.imageview;

import java.awt.*;
import java.awt.image.BufferedImage;

class BufferedImageCreator {
    static BufferedImage createBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        } else {
            BufferedImage result = new BufferedImage(
                    image.getWidth(null),
                    image.getHeight(null),
                    BufferedImage.TYPE_INT_ARGB);
            Graphics2D graphics = result.createGraphics();
            graphics.drawImage(image, 0, 0, null);
            graphics.dispose();
            return result;
        }
    }
}
