import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class FileDuplicateFinder {

    private Hashing hashing;

    public FileDuplicateFinder() {
        this.hashing = new Hashing();
    }

    // главный метод - разбивает по группам дубликаты по ключу который
    // вычисляется методом calculateContentHash класса hashing
    // ( хеширование содержимого файла ).
    // Потом методом filterAndSortDuplicateGroups перегоняет
    // в List<List<File>> и сортирует.
   public List<List<File>> findDuplicates(List<File> fileList) {

        Map<Integer, List<File>> contentHashGroups = fileList.parallelStream()
                .collect(Collectors.groupingByConcurrent(hashing::calculateContentHash));

        contentHashGroups.remove(-1); // в методе calculateContentHash -1 если невалидный файл

        return filterAndSortDuplicateGroups(contentHashGroups);
    }


    // из HashMap все группы (> 1 файла) в List<List<File>> и сортирует
    private List<List<File>> filterAndSortDuplicateGroups(Map<Integer, List<File>> contentHashGroups) {

        List<List<File>> duplicateGroups = new ArrayList<>();

        for (List<File> group : contentHashGroups.values()) {
            if (group.size() > 1) {
                duplicateGroups.add(group);
            }
        }

        Collections.sort(duplicateGroups, new FileListSizeComparator());

        return duplicateGroups;
    }
}
