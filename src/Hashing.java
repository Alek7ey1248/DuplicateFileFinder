import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.security.MessageDigest;
import java.io.FileInputStream;
import java.security.NoSuchAlgorithmException;

public class Hashing {

    private CheckValid checkValid;

    public Hashing() {this.checkValid = new CheckValid();}

    // расчет хеша файла
    // хеши файлов представляют собой определенное числовое значение,
    // которое вычисляется на основе содержимого файла. Этот хеш является
    // своеобразным "отпечатком" файла и позволяет быстро сравнивать файлы
    // на равенство без необходимости сравнивать их содержимое побитово
    // каждый раз.
    //
    //Таким образом, хеш содержимого файла представляет собой компактное
    // числовое представление содержимого файла, которое используется для
    // быстрого сравнения файлов на их эквивалентность.
    public int calculateContentHash(File file) {

        if (!checkValid.isValidFile(file)) {
            return -1;
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(file);

            byte[] buffer = new byte[8192];  // Значение 8192 (или 8 килобайт) является распространенным выбором для размера буфера при чтении данных из файла в Java. Этот размер обычно обеспечивает хорошую производительность при чтении и обработке данных.
            int bytesRead;

            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }

            fis.close();

            byte[] hashBytes = digest.digest();

            // Преобразование байтов хеша в целочисленное значение
            int hash = 0;
            for (byte b : hashBytes) {
                hash = (hash << 8) + (b & 0xff);
            }

            return hash;

        } catch (IOException | UncheckedIOException e) {
            System.err.println(" Помилка читання файлу " + file.getName() + " в методе calculateContentHash: " + e.getMessage());
            return -1;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}