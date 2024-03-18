import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

        List<String> paths = new ArrayList<String>();
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11");
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/a1.txt");
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21");
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder");
        //paths.add("/home/alek7ey/Рабочий стол");
        //paths.add("/home/alek7ey");

        // проверим валидность аргументов
        ArgumentsProcessor processor = new ArgumentsProcessor();
        List<String> validPaths = processor.processArguments(paths);
        List<File> listAllFile = new ArrayList<File>();

        FileDuplicateFinder finder = new FileDuplicateFinder();
        for (int i = 0; i < validPaths.size(); i++) {
            System.out.println("================================================================");
            System.out.println(" группы одинаковых файлов от каталога - " + validPaths.get(i));
            System.out.println("");

            // все файлы в спмсок
            File currentFile = new File(validPaths.get(i));
            List<File> currentListFile = finder.listFilesInTheDirectory(currentFile);
            listAllFile.addAll(currentListFile);
        }

        // поиск дубликатов и добавление в общий список
        List<Set<File>> duplicateGroups = finder.findDuplicates(listAllFile);
        List<List<File>> listDuplicateGroups = new ArrayList<>();
        for (Set<File> fileSet : duplicateGroups) {
            List<File> fileList = new ArrayList<>(fileSet);
            listDuplicateGroups.add(fileList);
        }

        // сортировка и вывод в консоль
        DuplicateFileGroup group = new DuplicateFileGroup(listDuplicateGroups);
        group.printDuplicateGroups();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Перевод наносекунд в миллисекунды
        System.out.println("Время выполнения программы: " + duration + " мс");
    }

}
