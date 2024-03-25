import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ComparingTwoFiles {

    public static final int NUM_THREADS = calculateOptimalNumThreads();  // оптимальное кол-во потоков

    public static int calculateOptimalNumThreads() {
        int processors = Runtime.getRuntime().availableProcessors();
        int optimalNumThreads;
        if (processors < 8) {
            optimalNumThreads = processors;
        } else {
            optimalNumThreads = Math.max(1, processors / 6);
        }
        return optimalNumThreads;
    }


    public boolean areFilesEqual(File file1, File file2) {
        if (!isValidFile(file1) || !isValidFile(file2)) {
            return false;
        }

        byte[] content1;
        byte[] content2;
        try {
            content1 = Files.readAllBytes(file1.toPath());
            content2 = Files.readAllBytes(file2.toPath());
        } catch (IOException | UncheckedIOException e) {
            System.out.println("Помилка читання файлу: " + e.getMessage());
            return false;
        }

        if (content1.length != content2.length) {
            return false;
        }

        ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);
        List<Future<Boolean>> futures = new ArrayList<>();

        int blockSize = content1.length / NUM_THREADS;
        for (int i = 0; i < NUM_THREADS; i++) {
            int start = i * blockSize;
            int end = (i == NUM_THREADS - 1) ? content1.length : (i + 1) * blockSize;
            byte[] block1 = new byte[end - start];
            byte[] block2 = new byte[end - start];
            System.arraycopy(content1, start, block1, 0, block1.length);
            System.arraycopy(content2, start, block2, 0, block2.length);

            Callable<Boolean> task = () -> compareFilesContent(block1, block2);
            futures.add(executor.submit(task));
        }

        for (Future<Boolean> future : futures) {
            try {
                if (!future.get()) {
                    executor.shutdown();
                    return false;
                }
            } catch (InterruptedException | ExecutionException e) {
                System.out.println("Помилка порівняння файлів: " + e.getMessage());
                return false;
            }
        }

        executor.shutdown();
        return true;
    }

    private boolean compareFilesContent(byte[] content1, byte[] content2) {
        for (int i = 0; i < content1.length; i++) {
            if (content1[i] != content2[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isValidFile(File file) {
        if (!file.isFile()) {
            System.out.println("File " + file.getAbsolutePath() + " не є файлом.");
            return false;
        }
        if (!file.canRead()) {
            System.out.println("File " + file.getAbsolutePath() + " пошкоджений.");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {
        File file1 = new File("/home/alek7ey/Загрузки/photo_2021-12-09_16-12-54 (копия).jpg");
        File file2 = new File("/home/alek7ey/Загрузки/photo_2021-12-09_16-12-54.jpg");
        ComparingTwoFiles comparator = new ComparingTwoFiles();
        boolean result = comparator.areFilesEqual(file1, file2);
        //System.out.println("UM_THREADS - " + FileUtils.NUM_THREADS);
        System.out.println("Файлы равны: " + result);
    }
}