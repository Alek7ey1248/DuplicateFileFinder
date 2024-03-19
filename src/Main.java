import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        long startTime = System.nanoTime();

        List<String> paths = new ArrayList<String>();
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11");
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12");
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12/test13");

        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/a1.txt");
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21");
        paths.add("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder");
        paths.add("/home/alek7ey/Рабочий стол");
        paths.add("/home/alek7ey");

        // проверим валидность аргументов
        ArgumentsProcessor processor = new ArgumentsProcessor();
        List<String> validPaths = processor.processArguments(paths);
        Set<File> setAllFiles = new HashSet<>();

        FileDuplicateFinder finder = new FileDuplicateFinder();
        for (int i = 0; i < validPaths.size(); i++) {

            // все файлы в спмсок
            File currentDirectory = new File(validPaths.get(i));
            setAllFiles.addAll(finder.listFilesInTheDirectory(currentDirectory));

            System.out.println("--после директория -- " + currentDirectory);
            for (File f : setAllFiles) {
                System.out.println(f.getAbsolutePath());
            }
        }
        // конвертировать в List
        List<File> listAllFiles = new ArrayList<>(setAllFiles);
        System.out.println("--после конвертации -- ");
        for (File f : listAllFiles) {
            System.out.println(f.getAbsolutePath());
        }

        // упорядочить по размеру
        Collections.sort(listAllFiles, new Comparator<File>() {
            public int compare(File file1, File file2) {
                return Long.compare(file2.length(), file1.length());
            }
        });
        System.out.println("--после упорядочения -- ");
        for (File f : listAllFiles) {
            System.out.println(f.getName() + " - " + f.length());
        }


        // распределение по группам файлов одного размера
        List<Set<File>> groupsBySize = new ArrayList<>();
        Set<File> setF = new HashSet<>();
        File previousFile = new File("");
        setF.add(previousFile);
        for (int i = 0; i < listAllFiles.size(); i++) {
            File currFile = listAllFiles.get(i);
            if (currFile.length() == previousFile.length()) {
                setF.add(currFile);
            } else {
                setF = new HashSet<>();
                groupsBySize.add(setF);
                setF.add(currFile);
            }
            previousFile = currFile;
        }

        // убираем группы где по одному файлу
        List<Set<File>> newGroupsBySize = new ArrayList<>();
        for (Set<File> list : groupsBySize) {
            if (list.size() > 1) newGroupsBySize.add(list);
        }

        // в каждой группе ищем группы дубликатов и добавляем в resGroup
        List<Set<File>> resGroup = new ArrayList<>();
        for (Set<File> list : newGroupsBySize) {
            List<File> lf = new ArrayList<>(list);
            List<Set<File>> ls = finder.findDuplicates(lf);
            resGroup.addAll(ls);
        }

        // вывод в консоль списков групп упорядоченных по размеру
        for (Set<File> set : resGroup) {
            System.out.println();
            int k = 0;
            for (File file : set) {
                if (k == 0) System.out.println(" --- одинаковые файлы типа - " + file.getName() + ", размера - " + file.length());
                k = 1;
                System.out.println(file + " размер - " + file.length());
            }
        }






        // !!!!!! тут все под вопросом - это использование ускоряющего класса FileGrouping !!!!
//        FileGrouping fileGrouping = new FileGrouping();
//        List<List<File>> duplicateGroups = FileGrouping.groupFiles(listAllFiles);

        // сортировка и вывод в консоль
//        DuplicateFileGroup group = new DuplicateFileGroup(duplicateGroups);
//        group.printDuplicateGroups();


        // поиск дубликатов и добавление в общий список
//        List<Set<File>> duplicateGroups = finder.findDuplicates(listAllFile);
//        List<List<File>> listDuplicateGroups = new ArrayList<>();
//        for (Set<File> fileSet : duplicateGroups) {
//            List<File> fileList = new ArrayList<>(fileSet);
//            listDuplicateGroups.add(fileList);
//        }

        // сортировка и вывод в консоль
//        DuplicateFileGroup group = new DuplicateFileGroup(listDuplicateGroups);
//        group.printDuplicateGroups();

        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // Перевод наносекунд в миллисекунды
        System.out.println("Время выполнения программы: " + duration + " мс");
    }

}
