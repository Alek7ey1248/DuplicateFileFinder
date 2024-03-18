import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main {

    public static void main(String[] args) {

        List<String> paths = new ArrayList<String>();
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11");
        //paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/a1.txt");
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21");
        //paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21");

        // проверим валидность аргументов
        ArgumentsProcessor processor = new ArgumentsProcessor();
        List<String> validPaths = processor.processArguments(paths);

//        for (String path : validPaths) {
//            System.out.println("Valid path: " + path);
//        }

        FileDuplicateFinder finder = new FileDuplicateFinder();
        for (int i = 0; i < validPaths.size(); i++) {
            System.out.println("================================================================");
            System.out.println(" группы одинаковых файлов от каталога - " + validPaths.get(i));
            System.out.println("");

            // поиск дубликатов
            List<List<File>> exampleGroups = finder.findDuplicates(validPaths.get(i));

            // сортировка и вывод в консоль
            DuplicateFileGroup group = new DuplicateFileGroup(exampleGroups);
            group.printDuplicateGroups();
        }
        List<List<File>> exampleGroups = new ArrayList<>();
        // Заполнение exampleGroups данными
        DuplicateFileGroup group = new DuplicateFileGroup(exampleGroups);
        group.printDuplicateGroups();
    }

}
