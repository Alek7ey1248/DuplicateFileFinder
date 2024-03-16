import java.io.File;

public class TestFileUtils {

    FileUtils fu = new FileUtils();

    void testReadFile() {
        //String file = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/.bashrc (копия)";
        //String file = "/home/alek7ey/Рабочий стол/Courses Coursera/Землетрясения: интерфейсы/Интерфейс Comparable/Стартовая программа эффективной сортировки/EfficientSortStarterProgram/DifferentSorters.java";
        //String file = "/home/alek7ey/.bash_history";
        //String file = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/photo_2021-12-09_16-13-39 (копия).jpg";
        String file = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/test11/test12/c1.txt";

        String strFile = fu.readFile(file);
        System.out.println(strFile);
    }

    void TestAreFilesEqual() {
//        File file1 = new File("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/file1.txt");
//        File file2 = new File("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/file2.txt");
        File file1 = new File("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/photo_2021-12-09_16-13-39.jpg");
        File file2 = new File("/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/photo_2021-12-09_16-13-39 (копия).jpg");
        boolean equal = fu.areFilesEqual(file1, file2);
        if (equal) System.out.println(" файлы " + file1.getName() + " и " + file2.getName() + " - однакові");
        else System.out.println(" файлы " + file1.getName() + " и " + file2.getName() + " - різні");
    }

    public static void main(String[] args) {

        System.out.println("Тест методов FileUtils");
        TestFileUtils tfu = new TestFileUtils();

        System.out.println();
        System.out.println("      Метод readFile. Прочитанный методом файл");
        tfu.testReadFile();

//        System.out.println();
//        System.out.println("Метод areFilesEqual(File file1, File file2) и вспомогательный в нем метод compareFilesContent(byte[] content1, byte[] content2)");
//        tfu.TestAreFilesEqual();


    }

}
