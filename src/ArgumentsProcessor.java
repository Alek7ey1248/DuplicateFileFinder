import java.io.File;
import java.util.*;

// Обработка на валидность, введенных как аргументы путей
public class ArgumentsProcessor {
    public List<String> processArguments(String[] parts) {
        Set<String> validPaths = new HashSet<>(Arrays.asList(parts));

        if (parts.length == 0) {
            System.err.println("Нічого не введено як аргументи.");
            List<String> list = new ArrayList<>(validPaths);
            return list;
        }

        for (String arg : parts) {
            File file = new File(arg);
            if (!file.exists()) {
                System.err.println("Путь " + arg + " не існує. Пропускаємо.");
                continue;
            }

            if (!file.isDirectory()) {
                System.err.println("Путь " + arg + " не є каталогом. Пропускаємо.");
                continue;
            }

            validPaths.add(file.getAbsolutePath());
        }
        List<String> list = new ArrayList<>(validPaths);

        if (list.size() == 0) {
            System.err.println("Немає коректних аргументів.");
        }

        return list;
    }
}
