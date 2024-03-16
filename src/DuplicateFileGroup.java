
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Класс для группировки дубликатов файлов:
//Представляет группу файлов с одинаковым содержимым.
//Методы для добавления файлов в группу и вывода информации о группе.
public class DuplicateFileGroup {

    private List<List<File>> duplicateGroups;

    public DuplicateFileGroup(List<List<File>> duplicateGroups) {
        this.duplicateGroups = duplicateGroups;
    }

    public List<List<File>> getDuplicateGroups() {
        return duplicateGroups;
    }

    public void printDuplicateGroups() {
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

    public static void main(String[] arg) {

        String[] args = new String[2];
        args[0] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11";
        args[1] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21";

        FileDuplicateFinder finder = new FileDuplicateFinder();
        for (int i = 0; i < args.length; i++) {
            System.out.println("================================================================");
            System.out.println(" группы одинаковых файлов от каталога - " + args[i]);
            System.out.println("");
            if (args.length > 0) {

                // поиск дубликатов
                List<List<File>> exampleGroups = finder.findDuplicates(args[i]);

                // сортировка и вывод в консоль
                DuplicateFileGroup group = new DuplicateFileGroup(exampleGroups);
                group.printDuplicateGroups();

            } else {
                System.err.println("Вкажіть шлях до каталогу - " + args[i] + " як аргумент..");
            }
        }

        List<List<File>> exampleGroups = new ArrayList<>();
        // Заполнение exampleGroups данными
        DuplicateFileGroup group = new DuplicateFileGroup(exampleGroups);
        group.printDuplicateGroups();
    }
}