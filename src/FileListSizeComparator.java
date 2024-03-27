import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

// определения интерфейса Comparator как сортировать списки по размеру вложеных в них файлов
// Используется в классе DuplicateFileGroup в методе printDuplicateGroups при помощи Collections.sort(...
public class FileListSizeComparator implements Comparator<List<File>> {
    @Override
    public int compare(List<File> fileList1, List<File> fileList2) {
        long size1 = fileList1.get(0).length();
        long size2 = fileList2.get(0).length();
        return Long.compare(size2, size1);
    }
}
