package at.yeoman.imageview.dataStorage;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.imageio.ImageIO;

class DataStorageImplementation {
    private static final String Format = "png";

    private final File storageFile;

    DataStorageImplementation()
            throws IOException {
        File applicationDataDirectory = new File(System.getenv("APPDATA"));
        if (!applicationDataDirectory.isDirectory()) {
            throw new IOException("Not a directory [" + applicationDataDirectory + "]");
        }
        File storageDirectory = new File(applicationDataDirectory, "at.yeoman.imageview");
        if (storageDirectory.exists()) {
            if (!storageDirectory.isDirectory()) {
                throw new IOException("Not a directory [" + storageDirectory.getCanonicalPath() + "]");
            }
        } else {
            if (!storageDirectory.mkdir()) {
                throw new IOException("Unable to create directory [" + storageDirectory.getCanonicalPath() + "]");
            }
        }
        storageFile = new File(storageDirectory, "images");
    }

    void save(List<BufferedImage> images)
            throws IOException {
        List<byte[]> data = new ArrayList<>(images.size());
        for (BufferedImage image : images) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            if (!ImageIO.write(image, Format, out)) {
                throw new IOException("No writer found for format " + Format);
            }
            data.add(out.toByteArray());
        }
        writeFile(data);
    }

    private void writeFile(List<byte[]> data)
            throws IOException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(storageFile);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(data);
        }
    }

    List<BufferedImage> load()
            throws IOException, ClassNotFoundException {
        if (storageFile.exists()) {
            List<byte[]> data = readFile();
            return readImages(data);
        } else {
            return Collections.emptyList();
        }
    }

    @SuppressWarnings("unchecked")
    private List<byte[]> readFile()
            throws IOException, ClassNotFoundException {
        try (FileInputStream fileInputStream = new FileInputStream(storageFile);
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            List<byte[]> result = (List<byte[]>) objectInputStream.readObject();
            if (fileInputStream.read() != -1) {
                throw new IOException("Storage file [" + storageFile + "] was not read entirely");
            }
            return result;
        }
    }

    private List<BufferedImage> readImages(List<byte[]> data)
            throws IOException {
        List<BufferedImage> result = new ArrayList<>(data.size());
        for (byte[] imageData : data) {
            result.add(readImage(imageData));
        }
        return result;
    }

    private BufferedImage readImage(byte[] imageData)
            throws IOException {
        ByteArrayInputStream imageDataInputStream = new ByteArrayInputStream(imageData);
        return readImageFromStream(imageDataInputStream);
    }

    private BufferedImage readImageFromStream(ByteArrayInputStream imageDataInputStream)
            throws IOException {
        BufferedImage image = ImageIO.read(imageDataInputStream);
        return image;
    }
}
