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
//        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/a1.txt");
//        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21");
        //paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder");
        //paths.add("/home/alek7ey/Рабочий стол");   // 88 файлов - 7160 мс
        //paths.add("/home/alek7ey/snap/telegram-desktop"); //- очень много, 5414 файлов - 25972 мс
        //paths.add("/home/alek7ey/IdeaProjects");   // 1109 файлов за 40126 мс
        // paths.add("/home/alek7ey"); - суппер много - мой комп не справляется
//        paths.add("/home/alek7ey/Android");    //
        paths.add("/home");

        // проверим валидность аргументов
//        ArgumentsProcessor processor = new ArgumentsProcessor();
//        List<String> validPaths = processor.processArguments(paths);

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Перевод наносекунд в миллисекунды
        System.out.println("Время выполнения программы: " + duration + " мс");
    }
}
