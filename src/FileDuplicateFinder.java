import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Set<File> checkedFiles  = new HashSet<>();

        for (int i = 0; i < fileList.size(); i++) {
            if (checkedFiles .contains(fileList.get(i))) {
                continue; // Пропускаем файл, если уже был добавлен в другую группу
            }

            List<File> group = new ArrayList<>();
            group.add(fileList.get(i));
            checkedFiles .add(fileList.get(i));

            for (int j = i + 1; j < fileList.size(); j++) {
                if (fileUtils.areFilesEqual(fileList.get(i), fileList.get(j))) {
                    group.add(fileList.get(j));
                    checkedFiles .add(fileList.get(j));
                }
            }

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
