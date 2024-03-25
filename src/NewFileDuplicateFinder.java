import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.*;

public class NewFileDuplicateFinder {

    private ComparingTwoFiles fileUtils;

    public NewFileDuplicateFinder() {
        this.fileUtils = new ComparingTwoFiles();
    }

    // группировка файлов в HashMap по размеру
    public HashMap<Long, List<File>> filesGroupedByLength(Set<File> files) {
        HashMap<Long, List<File>> map = new HashMap<>();
        for (File file : files) {
                Long fileLength = file.length();
                if (map.containsKey(fileLength)) {
                    map.get(fileLength).add(file);
                } else {
                    List<File> fileList = new ArrayList<>();
                    fileList.add(file);
                    map.put(fileLength, fileList);
                }
        }
        return map;
    }


    // поиск всех файлов в нескольких директориях при помощи метода setFilesInTheDirectory
    public Set<File> setFilesInTheDirectories(String[] pathDirectories){
        Set<File> setAllFiles = new HashSet<>();
        for (int i = 0; i < pathDirectories.length; i++) {
            File currentDirectory = new File(pathDirectories[i]);
            setAllFiles.addAll(setFilesInTheDirectory(currentDirectory));
        }
        System.out.println(" кол-во всех файлов в директориях - " + setAllFiles.size());
        return setAllFiles;
    }


    // поиск всех файлов в указаной директории
    private Set<File> setFilesInTheDirectory(File directory) {
        Set<File> setOfFiles = new HashSet<>();
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    setOfFiles.addAll(setFilesInTheDirectory(file));
                } else {
                    setOfFiles.add(file);
                }
            }
        }
        return setOfFiles;
    }

    public static void main(String[] args) {
        String[] arrayPath = new String[5];
        arrayPath[0] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11";
        arrayPath[1] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12";
        arrayPath[2] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12/test13";
        arrayPath[3] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21";
        arrayPath[4] = "/home/alek7ey/snap/telegram-desktop";
        NewFileDuplicateFinder fdf = new NewFileDuplicateFinder();
        Set<File> sf = fdf.setFilesInTheDirectories(arrayPath);
        System.out.println(" Кол-во файлов " + sf.size());
//        for (File f : sf) {
//            System.out.println(f.getName());
//            System.out.println(f.getAbsolutePath());
//        }
        HashMap<Long, List<File>> map = fdf.filesGroupedByLength(sf);
        for (Map.Entry<Long, List<File>> entry : map.entrySet()) {
            Long key = entry.getKey();
            List<File> value = entry.getValue();

            //System.out.println("Файлы типа : " + entry.getValue().get(0));
            System.out.println("----------------------");
            for (File file : value) {
                System.out.println(file.getName());
            }
        }
    }

}
