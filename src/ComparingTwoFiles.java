import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;

public class ComparingTwoFiles {

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