public class TestFileUtils {

    FileUtils fu = new FileUtils();

    void testReadFile() {
        //String file = "/home/alek7ey/Рабочий стол/TestsDuplicateFileFinder/file1.txt";
        //String file = "/home/alek7ey/Рабочий стол/Courses Coursera/Землетрясения: интерфейсы/Интерфейс Comparable/Стартовая программа эффективной сортировки/EfficientSortStarterProgram/DifferentSorters.java";
        String file = "/home/alek7ey/.bash_history";

        String strFile = fu.readFile(file);
        System.out.println(strFile);
    }

    public static void main(String[] args) {

        System.out.println("Тест методов FileUtils");
        TestFileUtils tfu = new TestFileUtils();

        System.out.println();
        System.out.println("      Метод readFile");
        tfu.testReadFile();


    }

}
