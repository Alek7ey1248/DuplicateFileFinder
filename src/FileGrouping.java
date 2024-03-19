import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
public class FileGrouping {
    public static List<List<File>> groupFiles(List<File> files) throws InterruptedException, ExecutionException {
        List<List<File>> duplicateGroups = new ArrayList<>();

        // Создаем пул потоков для выполнения задач
        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        // Создаем список задач для каждого файла
        List<Callable<List<File>>> tasks = new ArrayList<>();
        int i;
        for ( i = 0; i < files.size(); i++) {
            final File file = files.get(i);
            final int ii = i;
            tasks.add(() -> {
                List<File> duplicateGroup = new ArrayList<>();
                duplicateGroup.add(file);

                // Проверяем каждый файл в группе на идентичность по содержимому
                for (int j = ii + 1; j < files.size(); j++) {
                    File otherFile = files.get(j);
                    FileUtils fu = new FileUtils();
                    if (fu.areFilesEqual(file, otherFile)) {
                        duplicateGroup.add(otherFile);
                    }
                }

                return duplicateGroup;
            });
        }

        // Запускаем задачи и получаем результаты
        List<Future<List<File>>> results = executor.invokeAll(tasks);

        // Извлекаем результаты из Future и добавляем их в список групп
        for (Future<List<File>> result : results) {
            duplicateGroups.add(result.get());
        }

        // Завершаем работу пула потоков
        executor.shutdown();

        return duplicateGroups;
    }
}

