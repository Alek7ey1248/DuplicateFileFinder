import java.io.File;
import java.util.*;


public class Main {

    public static void main(String[] args) {

        long startTime = System.nanoTime();

//        List<String> paths = new ArrayList<String>();
//        for (int i=0; i < args.length; i++) {
//            paths.add(args[i]);
//        }
//
//        // проверим валидность аргументов
//        ArgumentsProcessor processor = new ArgumentsProcessor();
//        List<String> validPaths = processor.processArguments(paths);
//
//        Set<File> setAllFiles = new HashSet<>();
//        FileDuplicateFinder finder = new FileDuplicateFinder();
//
//        // все файлы из директориев в один спмсок
//        for (int i = 0; i < validPaths.size(); i++) {
//            File currentDirectory = new File(validPaths.get(i));
//            setAllFiles.addAll(finder.listFilesInTheDirectory(currentDirectory));
//        }
//        //System.out.println(" кол-во всех файлов в директориях - " + setAllFiles.size()); ;
//
//        // полученый список файлов упорядочиваем для оптимизации поиска
//        // ищем группы дубликатов в resGroup
//        List<Set<File>> resGroup = finder.groupingAndFindDuplicates(setAllFiles);
//
//        // вывод в консоль списков групп упорядоченных по размеру предыдущим методом
//        PrintFileGroup dfg = new PrintFileGroup();
//        dfg.printDuplicateGroups(resGroup);
//
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime) / 1_000_000; // Перевод наносекунд в миллисекунды
//        System.out.println("Время выполнения программы: " + duration + " мс");

        TestMain testMain = new TestMain();
        testMain.test();

    }
}
