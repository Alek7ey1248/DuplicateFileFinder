import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllFilesDirectory {

    // поиск всех файлов в нескольких директориях
    public List<File> findAllFilesInDirectories(List<String> paths){

        Set<File> fileSet = new HashSet<>();

        for (int i = 0; i < paths.size(); i++) {
            File dir = new File(paths.get(i));
            fileSet.addAll(findFilesInDirectory(dir));
        }
        System.out.println(" кол-во всех файлов в директориях - " + fileSet.size());

        return new ArrayList<>(fileSet);
    }


    // поиск всех файлов в указаной директории
    private Set<File> findFilesInDirectory(File path) {
        Set<File> fileSet = new HashSet<>();
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    fileSet.addAll(findFilesInDirectory(file));
                } else {
                    fileSet.add(file);
                }
            }
        }
        return fileSet;
    }
}
