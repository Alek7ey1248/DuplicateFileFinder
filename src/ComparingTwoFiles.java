import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;

public class ComparingTwoFiles {

    // расчет хеша файла
    // хеши файлов представляют собой определенное числовое значение,
    // которое вычисляется на основе содержимого файла. Этот хеш является
    // своеобразным "отпечатком" файла и позволяет быстро сравнивать файлы
    // на равенство без необходимости сравнивать их содержимое побитово
    // каждый раз.
    //
    //Таким образом, хеш содержимого файла представляет собой компактное
    // числовое представление содержимого файла, которое используется для
    // быстрого сравнения файлов на их эквивалентность.
    public int calculateContentHash(File file) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(file);
            // Значение 8192 (или 8 килобайт) является распространенным выбором для размера буфера при чтении данных из файла в Java. Этот размер обычно обеспечивает хорошую производительность при чтении и обработке данных.
            byte[] buffer = new byte[8192];
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            fis.close();

            byte[] hashBytes = digest.digest();

            // Преобразование байтов хеша в целочисленное значение
            int hash = 0;
            for (byte b : hashBytes) {
                hash = (hash << 8) + (b & 0xff);
            }

            return hash;
        } catch (IOException | UncheckedIOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
            return 0;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }




    public boolean areFilesEqual(File file1, File file2) {
        if (!isValidFile(file1) || !isValidFile(file2)) {
            return false;
        }

        byte[] content1;
        byte[] content2;
        try {
            content1 = Files.readAllBytes(file1.toPath());
            content2 = Files.readAllBytes(file2.toPath());
        } catch (IOException | UncheckedIOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
            return false;
        }

        if (content1.length != content2.length) {
            return false;
        }

        return compareFilesContent(content1, content2);
    }


    private boolean compareFilesContent(byte[] content1, byte[] content2) {
        for (int i = 0; i < content1.length; i++) {
            if (content1[i] != content2[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidFile(File file) {
        if (!file.isFile()) {
            System.out.println("File " + file.getAbsolutePath() + " не є файлом.");
            return false;
        }
        if (!file.canRead()) {
            System.out.println("File " + file.getAbsolutePath() + " пошкоджений.");
            return false;
        }
        return true;
    }

//    public static void main(String[] args) {
//        File file1 = new File("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12/photo_2021-12-09_16-12-54 (копия).jpg");
//        File file2 = new File("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/photo_2021-12-09_16-12-54.jpg");
//        ComparingTwoFiles comparator = new ComparingTwoFiles();
//        boolean result = comparator.areFilesEqual(file1, file2);
//        //System.out.println("UM_THREADS - " + FileUtils.NUM_THREADS);
//        System.out.println("Файлы равны: " + result);
//    }
}