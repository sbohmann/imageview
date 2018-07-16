package at.yeoman.imageview;

import static at.yeoman.imageview.BufferedImageCreator.createBufferedImage;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.function.Consumer;

class ImageFromClipboard {
    static void load(Consumer<BufferedImage> imageHandler) {
        Transferable transferable = Toolkit.getDefaultToolkit().getSystemClipboard().getContents(null);
        if (transferable != null && transferable.isDataFlavorSupported(DataFlavor.imageFlavor)) {
            try {
                Image image = (Image) transferable.getTransferData(DataFlavor.imageFlavor);
                imageHandler.accept(createBufferedImage(image));
            } catch (UnsupportedFlavorException | IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
