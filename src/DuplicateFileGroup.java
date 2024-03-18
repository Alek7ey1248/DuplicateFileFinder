
import java.io.File;
import java.util.*;

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
}