import java.io.File;
import java.util.ArrayList;
import java.util.List;

// Клас для пошуку дублікатів файлів:
//- Методи для пошуку дублікатів файлів у зазначеній директорії та її піддиректоріях.
//- Методи для порівняння файлів на основі вмісту.
//- Методи для угруповання та виведення результатів.
    public class FileDuplicateFinder {
        private FileUtils fileUtils;

        public FileDuplicateFinder() {
            this.fileUtils = new FileUtils();
        }

        public List<List<File>> findDuplicates(String directoryPath) {
            File directory = new File(directoryPath);
            if (!directory.exists() || !directory.isDirectory()) {
                System.err.println("Неправильний шлях до каталогу.");
                return null;
            }
            List<File> fileList = listFiles(directory);
            List<List<File>> duplicateGroups = new ArrayList<>();

            for (int i = 0; i < fileList.size(); i++) {
                List<File> group = new ArrayList<>();
                if (!duplicateGroups.contains(group)) {
                    group.add(fileList.get(i));
                    for (int j = i + 1; j < fileList.size(); j++) {
                        if (fileUtils.areFilesEqual(fileList.get(i), fileList.get(j))) {
                            group.add(fileList.get(j));
                        }
                    }
                    if (group.size() > 1) {
                        duplicateGroups.add(group);
                    }
                }
            }

//            for (List<File> group : duplicateGroups) {
//                System.out.println("Група дублікатів:");
//                for (File file : group) {
//                    System.out.println(file.getAbsolutePath());
//                }
//                System.out.println();
//            }
            return duplicateGroups;
        }


        private List<File> listFiles(File directory) {
            //System.out.println(" перевірка директорія - " + directory);
            List<File> fileList = new ArrayList<>();
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        fileList.addAll(listFiles(file));
                    } else {
                        fileList.add(file);
                    }
                }
            }
            return fileList;
        }




        public static void main(String[] arg) {
            String[] args = new String[2];
            args[0] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11";
            args[1] = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test21";
            FileDuplicateFinder finder = new FileDuplicateFinder();
            for (int i = 0; i < args.length; i++) {
                System.out.println();
                System.out.println(" аргумент - " + i);
                System.out.println();
                if (args.length > 0) {
                    finder.findDuplicates(args[i]);
                } else {
                    System.err.println("Вкажіть шлях до каталогу - " + args[i] + " як аргумент..");
                }
            }
        }







    //Вариант в терминале
//        public static void main(String[] args) {
//            FileDuplicateFinder finder = new FileDuplicateFinder();
//            for (int i = 0; i < args.length; i++) {
//                System.out.println(" аргумент - " + i);
//                if (args.length > 0) {
//                    finder.findDuplicates(args[i]);
//                } else {
//                    System.err.println("Вкажіть шлях до каталогу - " + args[i] + " як аргумент..");
//                }
//            }
//        }

}
