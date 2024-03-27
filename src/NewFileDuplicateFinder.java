import java.io.File;
import java.util.*;

public class NewFileDuplicateFinder {

    private ComparingTwoFiles comparingTwoFiles;

    public NewFileDuplicateFinder() {
        this.comparingTwoFiles = new ComparingTwoFiles();
    }



    public List<List<File>> findDuplicates(List<File> fileList) {
        Map<Integer, List<File>> contentHashGroups = new HashMap<>();
        List<List<File>> duplicateGroups = new ArrayList<>();

        for (File file : fileList) {
            int contentHash = comparingTwoFiles.calculateContentHash(file);
            if (contentHashGroups.containsKey(contentHash)) {
                contentHashGroups.get(contentHash).add(file);
            } else {
                List<File> newGroup = new ArrayList<>();
                newGroup.add(file);
                contentHashGroups.put(contentHash, newGroup);
            }
        }

        return filterAndSortDuplicateGroups(contentHashGroups);
    }


    // из HashMap все группы (> 1 файла) в List<List<File>> и сортируем
    private List<List<File>> filterAndSortDuplicateGroups(Map<Integer, List<File>> contentHashGroups) {
        List<List<File>> duplicateGroups = new ArrayList<>();

        for (List<File> group : contentHashGroups.values()) {
            if (group.size() > 1) {
                duplicateGroups.add(group);
            }
        }

        Collections.sort(duplicateGroups, new FileListSizeComparator());

        return duplicateGroups;
    }


    public static void main(String[] args) {

        long startTime = System.nanoTime();

        String[] paths = new String[5];
        paths[0] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11";
        paths[1] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12";
        paths[2] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12/test13";
        paths[3] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21";
        //paths[4] = "/home/alek7ey/snap/telegram-desktop";
        paths[4] = "/home";

        // проверим валидность аргументов
        ArgumentsProcessor processor = new ArgumentsProcessor();
        List<String> validPaths = processor.processArguments(paths);

        NewFileDuplicateFinder fdf = new NewFileDuplicateFinder();
        AllFilesDirectory allFD = new AllFilesDirectory();

        allFD.findAllFilesInDirectories(validPaths);
        List<File> fileList = allFD.getFileList();
        List<List<File>> duplicates = fdf.findDuplicates(fileList);

        PrintFileGroup printFileGroup = new PrintFileGroup();
        printFileGroup.printDuplicateGroups(duplicates);


        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Перевод наносекунд в миллисекунды
        System.out.println("Время выполнения программы: " + duration + " мс");
    }

}
