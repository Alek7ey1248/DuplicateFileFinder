import java.io.File;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

            String[] paths = new String[1];
    //        paths[0] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11";
    //        paths[1] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12";
    //        paths[2] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12/test13";
    //        paths[3] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21";
            paths[0] = "/home/alek7ey/Рабочий стол";
            //paths[0] = "/home/alek7ey/snap/telegram-desktop";
            //paths[0] = "/home";
            //paths[4] = "/home/alek7ey/Загрузки";

            // проверим валидность аргументов
            CheckValid isValid = new CheckValid();
            List<String> validPaths = isValid.getValidDirectoryPaths(paths);

            FileDuplicateFinder fdf = new FileDuplicateFinder();
            AllFilesDirectory allFD = new AllFilesDirectory();

            List<File> fileList = allFD.findAllFilesInDirectories(validPaths);
            List<List<File>> duplicates = fdf.findDuplicates(fileList);

            PrintFileGroup printFileGroup = new PrintFileGroup();
            printFileGroup.printDuplicateGroups(duplicates);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Перевод наносекунд в миллисекунды
        System.out.println("Время выполнения программы: " + duration + " мс");



    }
}
