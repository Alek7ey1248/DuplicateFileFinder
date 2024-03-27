
import java.io.File;
import java.util.*;

// Методы вывода в консоль
public class PrintFileGroup {

    // метод вывода рез в консоль
    public void printDuplicateGroups(List<List<File>> duplicateGroups) {

        for (List<File> group : duplicateGroups) {

            if (!group.isEmpty()) {
                System.out.println();
                boolean bool = true;

                for (File file : group) {
                    if (bool) {
                        System.out.println("  Группа одинаковых файлов типа - " + file.getName() + ",  размера - " + file.length() + " -------------------");
                        bool = false;
                    }

                    System.out.println(file.getAbsolutePath());
                }
                System.out.println();
            }
        }
    }

}