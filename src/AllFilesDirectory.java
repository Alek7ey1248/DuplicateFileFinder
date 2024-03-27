import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AllFilesDirectory {

    private List<File> fileList;
    private Set<File> fileSet;

    public AllFilesDirectory() {
        fileList = new ArrayList<>();
        fileSet = new HashSet<>();
    }

    public List<File> getFileList() {
        return new ArrayList<>(getFileSet());
    }

    public Set<File> getFileSet() {
        return fileSet;
    }

    // поиск всех файлов в нескольких директориях
    public void findAllFilesInDirectories(List<String> pathDirectories){
        for (int i = 0; i < pathDirectories.size(); i++) {
            File currentDirectory = new File(pathDirectories.get(i));
            fileSet.addAll(findFilesInDirectory(currentDirectory));
        }
        System.out.println(" кол-во всех файлов в директориях - " + fileSet.size());
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


    public static void main(String[] args) {
        String[] paths = new String[5];
        paths[0] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11";
        paths[1] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12";
        paths[2] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12/test13";
        paths[3] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21";
        paths[4] = "/home/alek7ey/snap/telegram-desktop";
        //paths[4] = "/home";

        // проверим валидность аргументов
        ArgumentsProcessor processor = new ArgumentsProcessor();
        List<String> validPaths = processor.processArguments(paths);

        AllFilesDirectory allFilesDirectory = new AllFilesDirectory();
        allFilesDirectory.findAllFilesInDirectories(validPaths);
        List<File> files = allFilesDirectory.getFileList();
        System.out.println(" кол- во файлов - " + files.size());
        for (File f : files) {
            System.out.println(f.getName());
        }
    }
}
