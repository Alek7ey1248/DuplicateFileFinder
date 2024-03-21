import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestMain {
    public void test() {
        long startTime = System.nanoTime();

        List<String> paths = new ArrayList<String>();
//        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11");
//        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12");
//        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12/test13");
//testlllllllll
//        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/a1.txt");
//        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21");
//        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder");
        //paths.add("/home/alek7ey/Рабочий стол");   // 88 файлов - 7160 мс
        //paths.add("/home/alek7ey/snap/telegram-desktop"); //- очень много, 5414 файлов но справляется, но очень долго
        //paths.add("/home/alek7ey/IdeaProjects");   // 1109 файлов за 40126 мс
        // paths.add("/home/alek7ey"); - суппер много - мой комп не справляется
        paths.add("/home/alek7ey/Android");

        // проверим валидность аргументов
        ArgumentsProcessor processor = new ArgumentsProcessor();
        List<String> validPaths = processor.processArguments(paths);

        Set<File> setAllFiles = new HashSet<>();
        FileDuplicateFinder finder = new FileDuplicateFinder();

        for (int i = 0; i < validPaths.size(); i++) {
            // все файлы в спмсок
            File currentDirectory = new File(validPaths.get(i));
            setAllFiles.addAll(finder.listFilesInTheDirectory(currentDirectory));
        }
        System.out.println(" кол-во всех файлов в директориях - " + setAllFiles.size()); ;

        // полученый список файлов упорядочиваем для оптимизации поиска
        // ищем группы дубликатов в resGroup
        // finder.groupingAndFindDuplicates - 9438 мс - это в 64 раза быстрее чем finder.findDuplicates
        // когда добваил потоки то по времени - 7160 мс - трошки уменьшилось
        List<Set<File>> resGroup = finder.groupingAndFindDuplicates(setAllFiles);

        // finder.findDuplicates - 608567 мс
//        List<File> listAllFiles = new ArrayList<>(setAllFiles);
//        List<Set<File>> resGroup = finder.findDuplicates(listAllFiles);

        // вывод в консоль списков групп упорядоченных по размеру предыдущим методом
        PrintFileGroup dfg = new PrintFileGroup();
        dfg.printDuplicateGroups(resGroup);






        // !!!!!! тут все под вопросом - это использование ускоряющего класса FileGrouping !!!!
//        FileGrouping fileGrouping = new FileGrouping();
//        List<List<File>> duplicateGroups = FileGrouping.groupFiles(listAllFiles);

        // сортировка и вывод в консоль
//        DuplicateFileGroup group = new DuplicateFileGroup(duplicateGroups);
//        group.printDuplicateGroups();




        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Перевод наносекунд в миллисекунды
        System.out.println("Время выполнения программы: " + duration + " мс");
    }
}
