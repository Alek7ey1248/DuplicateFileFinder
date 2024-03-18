import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Обработка на валидность, введенных как аргументы путей
public class ArgumentsProcessor {
    public List<String> processArguments(List<String> args) {
        Set<String> validPaths = new HashSet<>();

        if (args.size() == 0) {
            System.err.println("Нічого не введено як аргументи.");
            List<String> list = new ArrayList<>(validPaths);
            return list;
        }

        for (String arg : args) {
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
