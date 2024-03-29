import java.io.File;
import java.util.*;

// Обработка на валидность, введенных как аргументы путей
public class CheckValid {

    public List<String> getValidDirectoryPaths(String[] paths) {
        List<String> validPaths = new ArrayList<>();
        for (String path : paths) {
            if (isValidDirectoryPath(path)) {
                validPaths.add(new File(path).getAbsolutePath());
            }
        }
        if (validPaths.isEmpty()) {
            System.err.println("Немає коректних аргументів.");
        }
        return validPaths;
    }


    // проверка одного директория
    public boolean isValidDirectoryPath(String path) {
        File file = new File(path);
        if (!file.exists()) {
            System.err.println("Шлях " + path + " не існує. Пропускаємо.");
            return false;
        }
        if (!file.isDirectory()) {
            System.err.println("Шлях " + path + " не є каталогом. Пропускаємо.");
            return false;
        }
        return true;
    }


    // проверка файла
    public boolean isValidFile(File file) {

        if (!file.canRead()) {
            System.err.println("Метод isValidFile.     File " + file.getAbsolutePath() + " пошкоджений.");
            return false;
        }
        if (!file.isFile()) {
            System.err.println("Метод isValidFile.    File " + file.getAbsolutePath() + " не є файлом.");
            return false;
        }
        return true;
    }

}
