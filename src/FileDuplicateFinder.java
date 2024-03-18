import java.io.File;
import java.util.*;

// Клас для пошуку дублікатів файлів:
//- Методи для пошуку дублікатів файлів у зазначеній директорії та її піддиректоріях.
//- Методи для порівняння файлів на основі вмісту.
//- Методи для угруповання та виведення результатів.


import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Клас для пошуку дублікатів файлів:
//- Методи для пошуку дублікатів файлів у зазначеній директорії та її піддиректоріях.
//- Методи для порівняння файлів на основі вмісту.
//- Методи для угруповання та виведення результатів.
public class FileDuplicateFinder {
    private FileUtils fileUtils;

    public FileDuplicateFinder() {
        this.fileUtils = new FileUtils();
    }

    public List<Set<File>> findDuplicates(List<File> fileList) {

        List<Set<File>> duplicateGroups = new ArrayList<>();
        List<File> checkedDuplicateGroups = new ArrayList<>();

        for (int i = 0; i < fileList.size(); i++) {
            if (checkedDuplicateGroups.contains(fileList.get(i))) {
                continue; // Пропускаем файл, если уже был добавлен в другую группу
            }
//            List<File> group = new ArrayList<>();
            Set<File> group = new HashSet<>();
            if (!duplicateGroups.contains(group)) {
                group.add(fileList.get(i));
                for (int j = i + 1; j < fileList.size(); j++) {
                    if (fileUtils.areFilesEqual(fileList.get(i), fileList.get(j))) {
                        group.add(fileList.get(j));
                        checkedDuplicateGroups.add(fileList.get(j));
                    }
                }
                if (group.size() > 1) {
                    duplicateGroups.add(group);
                }
            }
        }
        return duplicateGroups;

    }

    public List<File> listFilesInTheDirectory(File directory) {
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








    //Вариант в терминале
//        public static void main(String[] args) {
//            FileDuplicateFinder finder = new FileDuplicateFinder();
//            for (int i = 0; i < args.length; i++) {
//                System.out.println(" аргумент - " + i);
//                if (args.length > 0) {
//                    finder.findDuplicates(args[i]);
//                } else {
//                    System.err.println("Вкажіть шлях до каталогу - " + args[i] + " як аргумент..");
//                }
//            }
//        }

}

