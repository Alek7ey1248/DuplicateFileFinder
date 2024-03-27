
import java.io.File;
import java.util.*;

// Методы вывода в консоль
public class PrintFileGroup {

    // метод сортировки и вывода рез в консоль
    public void printDuplicateGroups(List<List<File>> duplicateGroups) {
        // Сортировка списка списков файлов по размеру от больших к меньшим
        //Collections.sort(duplicateGroups, new FileListSizeComparator());
        for (List<File> group : duplicateGroups) {
            if (!group.isEmpty()) {
                System.out.println();
                boolean bool = true;
                for (File file : group) {
                    if (bool) System.out.println("  Группа одинаковых файлов типа - " + file.getName() + ",  размера - " + file.length() + " -------------------");
                    bool = false;
                    System.out.println(file.getAbsolutePath());
                }
                System.out.println();
            }
        }
    }

}