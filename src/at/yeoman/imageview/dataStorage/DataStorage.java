package at.yeoman.imageview.dataStorage;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class DataStorage {
    private static final DataStorageImplementation instance = createDataStorage();

    public static void save(List<BufferedImage> images)
            throws IOException {
        if (instance != null) {
            instance.save(images);
        }
    }

    public static List<BufferedImage> load()
            throws IOException, ClassNotFoundException {
        if (instance != null) {
            return instance.load();
        } else {
            return Collections.emptyList();
        }
    }

    private static DataStorageImplementation createDataStorage() {
        try {
            return new DataStorageImplementation();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
