import java.io.File;
import java.util.*;

// Клас для пошуку дублікатів файлів:
//- Методи для пошуку дублікатів файлів у зазначеній директорії та її піддиректоріях.
//- Методи для порівняння файлів на основі вмісту.
//- Методи для угруповання та виведення результатів.
    public class FileDuplicateFinder {
        private FileUtils fileUtils;

        public FileDuplicateFinder() {
            this.fileUtils = new FileUtils();
        }

    public List<List<File>> findDuplicates(String directoryPath) {
        File directory = new File(directoryPath);
        List<File> fileList = listFilesInTheDirectory(directory);
        List<List<File>> duplicateGroups = new ArrayList<>();
        Map<String, List<File>> contentMap = new HashMap<>();

        for (File file : fileList) {
            String content = fileUtils.readFile(file.getAbsolutePath());
            if (contentMap.containsKey(content)) {
                contentMap.get(content).add(file);
            } else {
                List<File> group = new ArrayList<>();
                group.add(file);
                contentMap.put(content, group);
            }
        }

        for (List<File> group : contentMap.values()) {
            if (group.size() > 1) {
                duplicateGroups.add(group);
            }
        }

        return duplicateGroups;
    }

        // Метод обходить директорію та всі її піддиректорії,
        // збираючи всі файли в цих директоріях в один список
        private List<File> listFilesInTheDirectory(File directory) {
            //System.out.println(" перевірка директорія - " + directory);
            List<File> fileList = new ArrayList<>();
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        fileList.addAll(listFilesInTheDirectory(file));
                    } else {
                        fileList.add(file);
                    }
                }
            }
            return fileList;
        }

}
