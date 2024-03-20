
import java.io.File;
import java.util.*;

// Методы вывода в консоль
public class PrintFileGroup {

    // вывод в консоль списков групп упорядоченных ранее по размеру
    // актуальный метод
    public void printDuplicateGroups(List<Set<File>> resGroup) {
        for (Set<File> set : resGroup) {
            System.out.println();
            int k = 0;
            for (File file : set) {
                if (k == 0) System.out.println(" --- одинаковые файлы типа - " + file.getName() + ", размера - " + file.length());
                k = 1;
                System.out.println(file + " размер - " + file.length());
            }
        }
    }


    // устаревший метод сортировки и вывода рез в консоль
    public void printDuplicateGroupsOld(List<List<File>> duplicateGroups) {
        // Сортировка списка списков файлов по размеру от больших к меньшим
        Collections.sort(duplicateGroups, new FileListSizeComparator());
        for (List<File> group : duplicateGroups) {
            if (!group.isEmpty()) {
                File firstFile = group.get(0);
                System.out.println("  Файлы такиеже как ----- " + firstFile.getName() +  " ---------------");
                for (File file : group) {
                    System.out.println(file.getAbsolutePath());
                }
                System.out.println();
            }
        }
    }
}