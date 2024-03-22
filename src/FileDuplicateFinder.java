import java.io.File;
import java.util.*;

// Клас для пошуку дублікатів файлів:
//- Методи для пошуку дублікатів файлів у зазначеній директорії та її піддиректоріях.
//- Методи для порівняння файлів на основі вмісту.
//- Методи для угруповання та виведення результатів.


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// Клас для пошуку файлів у зазначеній директорії та її піддиректоріях
// та пошуку груп дублікатів файлів.
public class FileDuplicateFinder {
    private ComparingTwoFiles fileUtils;

    public FileDuplicateFinder() {
        this.fileUtils = new ComparingTwoFiles();
    }

    // Основной метод поиска групп дубликатов
    // полученый список файлов упорядочиваем для оптимизации поиска
    // и методом findDuplicates ищем группы дубликатов
    public List<Set<File>> groupingAndFindDuplicates(Set<File> setAllFiles) {

        // конвертировать в List
        List<File> listAllFiles = new ArrayList<>(setAllFiles);

        // упорядочить по размеру
        sortBySize(listAllFiles);
//        System.out.println("--после упорядочения -- ");
//        for (File f : listAllFiles) {
//            System.out.println(f.getName() + " - " + f.length());
//        }

        // распределение по группам файлов одного размера и удаление групп с одним файлом
        List<Set<File>> groupsBySize = groupsBySize(listAllFiles);
        int totalFilesCount = groupsBySize.stream()
                .mapToInt(Set::size)
                .sum();
        System.out.println("всего сгруппированных файлов без уникальных по размеру: " + totalFilesCount);

        // в каждой группе файлов с одинаковым размером ищем группы дубликатов и добавляем в resGroup
//        List<Set<File>> resGroup = new ArrayList<>();
//        for (Set<File> set : groupsBySize) {
//            List<File> lf = new ArrayList<>(set);
//            System.out.println(" проверяются файлы размера - " + + lf.get(0).length() + " - " + lf.get(0).getName());
//            List<Set<File>> lsf = findDuplicates(lf);
//            resGroup.addAll(lsf);
//        }
        //-------------------------------------------------- быстрее чем верхняя реализ в 2, 3 раза
        List<Set<File>> resGroup = groupsBySize.parallelStream()
                .map(set -> {
                    List<File> lf = new ArrayList<>(set);
                    System.out.println(" проверяются файлы размера - " + + lf.get(0).length() + " - " + lf.get(0).getName());
                    return findDuplicates(lf);
                })
                .flatMap(List::stream)
                .collect(Collectors.toList());
        //------------------------------------------------
        return resGroup;
    }


    // Сортировка файлов по размеру от большего к меньшему.
    // Вспомогательный метод.
    private List<File> sortBySize(List<File> list) {
        Collections.sort(list, new Comparator<File>() {
            public int compare(File file1, File file2) {
                return Long.compare(file2.length(), file1.length());
            }
        });
        return list;
    }

    // Распределение по группам файлов одного размера и удаление групп с одним элементом.
    // Вспомогательный метод.
    private List<Set<File>> groupsBySize(List<File> sortListAllFiles) {
        List<Set<File>> groups = new ArrayList<>();
        Set<File> setF = new HashSet<>();
        File previousFile = new File("");
        setF.add(previousFile);
        for (int i = 0; i < sortListAllFiles.size(); i++) {
            File currFile = sortListAllFiles.get(i);
            if (currFile.length() == previousFile.length()) {
                setF.add(currFile);
            } else {
                setF = new HashSet<>();
                groups.add(setF);
                setF.add(currFile);
            }
            previousFile = currFile;
        }

        // убираем группы где по одному файлу
        List<Set<File>> optimGroups = new ArrayList<>();
        for (Set<File> list : groups) {
            if (list.size() > 1) optimGroups.add(list);
        }
        return optimGroups;
    }


    // Метод - поиск групп дубликатов файлов из списка файлов.
    // Вспомогательный. Но может быть как основной метод( очень медленный и неоптимальный).
    // ----------------------------
    public List<Set<File>> findDuplicates(List<File> fileList) {
        Map<File, Boolean> checkedFiles = new HashMap<>();
        Set<Set<File>> duplicateGroups = new HashSet<>();

        for (File file : fileList) {
            if (checkedFiles.containsKey(file)) {
                continue; // Пропускаем файл, если уже был добавлен в другую группу
            }

            Set<File> group = new HashSet<>();
            group.add(file);
            for (File otherFile : fileList) {
                if (file.equals(otherFile) || checkedFiles.containsKey(otherFile)) {
                    continue;
                }
                if (fileUtils.areFilesEqual(file, otherFile)) {
                    group.add(otherFile);
                    checkedFiles.put(otherFile, true);
                }
            }

            if (group.size() > 1) {
                duplicateGroups.add(group);
            }
        }

        return new ArrayList<>(duplicateGroups);
    }
    // ----------------------------
//    public List<Set<File>> findDuplicates(List<File> fileList) {
//
//        List<Set<File>> duplicateGroups = new ArrayList<>();
//        List<File> checkedDuplicateGroups = new ArrayList<>();
//
//        for (int i = 0; i < fileList.size(); i++) {
//            if (checkedDuplicateGroups.contains(fileList.get(i))) {
//                continue; // Пропускаем файл, если уже был добавлен в другую группу
//            }
//            Set<File> group = new HashSet<>();
//            if (!duplicateGroups.contains(group)) {
//                group.add(fileList.get(i));
//                for (int j = i + 1; j < fileList.size(); j++) {
//                    if (fileUtils.areFilesEqual(fileList.get(i), fileList.get(j))) {
//                        group.add(fileList.get(j));
//                        checkedDuplicateGroups.add(fileList.get(j));
//                    }
//                }
//                if (group.size() > 1) {
//                    duplicateGroups.add(group);
//                }
//            }
//        }
//        return duplicateGroups;
//
//    }

    // поиск всех файлов в указаной директории
    public Set<File> listFilesInTheDirectory(File directory) {
        Set<File> fileList = new HashSet<>();
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

