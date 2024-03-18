import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//  Утилітарний клас для роботи з файлами
// Методи для читання файлів та порівняння їх вмісту.
public class FileUtils {
//
        public String readFile(String filePath) {
            StringBuilder content = new StringBuilder();
            try (FileInputStream fis = new FileInputStream(filePath)) {
                int data;
                while ((data = fis.read()) != -1) {
                    content.append((char) data);
                }
            } catch (IOException e) {
                System.err.println("Помилка читання файлу методом readFile: " + e.getMessage());
            }
            return content.toString();
        }

        public boolean compareFilesContent(byte[] content1, byte[] content2) {
            if (content1.length != content2.length) {
                return false;
            }
            for (int i = 0; i < content1.length; i++) {
                if (content1[i] != content2[i]) {
                    return false;
                }
            }
            return true;
        }

        public boolean areFilesEqual(File file1, File file2) {
            byte[] content1 = readFile(file1.getAbsolutePath()).getBytes();
            byte[] content2 = readFile(file2.getAbsolutePath()).getBytes();
            return compareFilesContent(content1, content2);
        }

}
