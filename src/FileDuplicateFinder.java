import java.io.File;
import java.util.*;

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
        Map<Integer, List<File>> contentHashGroups = Collections.synchronizedMap(new HashMap<>());

        fileList.parallelStream().forEach(file -> {
            int hash = hashing.calculateHashWithSize(file);
            if (hash != -1) {
                synchronized (contentHashGroups) {
                    contentHashGroups.computeIfAbsent(hash, k -> new ArrayList<>()).add(file);
                }
            }
        });

        return filterAndSortDuplicateGroups(contentHashGroups);
    }

    // из HashMap все группы (> 1 файла) в List<List<File>> и сортирует
    private List<List<File>> filterAndSortDuplicateGroups(Map<Integer, List<File>> contentHashGroups) {

        List<List<File>> duplicateGroups = new ArrayList<>();

        for (List<File> group : contentHashGroups.values()) {
            if (group.size() > 1) {
                //System.out.println("Группа размер " + group.get(0).length() + "   -----------------------");
                duplicateGroups.add(group);
//                for (File f : group) {
//                    System.out.println(f.getName() + " размер - " + f.length());
//                }
            }
        }

        Collections.sort(duplicateGroups, new FileListSizeComparator());

        return duplicateGroups;
    }

//   первый вариант метода - !!! возможны одинаковые ключи изза многопоточности
//        public List<List<File>> findDuplicates(List<File> fileList) {
//
//        Map<Integer, List<File>> contentHashGroups = fileList.parallelStream()
//                .collect(Collectors.groupingByConcurrent(hashing::calculateHashWithSize));
//
//        contentHashGroups.remove(-1); // в методе calculateContentHash -1 если невалидный файл
//
//        return filterAndSortDuplicateGroups(contentHashGroups);
//    }




  //второй вариант метода чтобы небыло одинаковых ключей ( используя merge)
//    public List<List<File>> findDuplicates(List<File> fileList) {
//        ConcurrentHashMap<Integer, List<File>> contentHashGroups = new ConcurrentHashMap<>();
//
//        fileList.parallelStream().forEach(file -> {
//            int hash = hashing.calculateHashWithSize(file);
//            if (hash != -1) {
//                contentHashGroups.merge(hash, new CopyOnWriteArrayList<>(Arrays.asList(file)), (list1, list2) -> {
//                    list1.addAll(list2);
//                    return list1;
//                });
//            }
//        });
//
//        return filterAndSortDuplicateGroups(contentHashGroups);
//    }
//
}

