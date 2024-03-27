import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllFilesDirectory {

    // поиск всех файлов в нескольких директориях
    public List<File> findAllFilesInDirectories(List<String> pathDirectories){

        Set<File> fileSet = new HashSet<>();

        for (int i = 0; i < pathDirectories.size(); i++) {
            File currentDirectory = new File(pathDirectories.get(i));
            fileSet.addAll(findFilesInDirectory(currentDirectory));
        }
        System.out.println(" кол-во всех файлов в директориях - " + fileSet.size());

        return new ArrayList<>(fileSet);
    }


    // поиск всех файлов в указаной директории
    private Set<File> findFilesInDirectory(File directory) {
        Set<File> setOfFiles = new HashSet<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    setOfFiles.addAll(findFilesInDirectory(file));
                } else {
                    setOfFiles.add(file);
                }
            }
        }
        return setOfFiles;
    }
}
