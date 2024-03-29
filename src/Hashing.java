import java.io.*;
import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class Hashing {

    private final CheckValid checkValid;

    public Hashing() {
        this.checkValid = new CheckValid();
    }


    //     расчет хеша файла
//     хеши файлов представляют собой определенное числовое значение,
//     которое вычисляется на основе содержимого файла. Этот хеш является
//     своеобразным "отпечатком" файла и позволяет быстро сравнивать файлы
//     на равенство без необходимости сравнивать их содержимое побитово
//     каждый раз.
//
//    Таким образом, хеш содержимого файла представляет собой компактное
//     числовое представление содержимого файла, которое используется для
//     быстрого сравнения файлов на их эквивалентность.
    public int calculateHashWithSize(File file) {

        if (!checkValid.isValidFile(file))  return -1;
        System.out.println(file.getAbsolutePath() );

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                digest.update(buffer, 0, bytesRead);
            }
            fis.close();

            // Добавим размер файла к хешу
            long fileSize = file.length();
            byte[] sizeBytes = ByteBuffer.allocate(Long.BYTES).putLong(fileSize).array();
            digest.update(sizeBytes);

            byte[] hashBytes = digest.digest();
            int hash = 0;
            for (byte b : hashBytes) {
                hash = (hash << 8) + (b & 0xff);
            }
            return hash;
        } catch (IOException | UncheckedIOException e) {
            System.err.println("Ошибка чтения файла " + file.getName() + " в методе calculateContentHash: " + e.getMessage());
            return -1;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }





    //    public int calculateHashWithSize(File file) {
//        if (!checkValid.isValidFile(file)) {
//            return -1;
//        }
//        System.out.println(file.getAbsolutePath() );
//
//        byte[] contentHash = calculateContentHashFromFile(file);
//        long fileSize = file.length();
//        ByteBuffer buffer = ByteBuffer.allocate(Long.BYTES);
//        buffer.putLong(fileSize);
//        byte[] sizeBytes = buffer.array();
//
//        MessageDigest digest;
//        try {
//            digest = MessageDigest.getInstance("SHA-256");
//            digest.update(contentHash);
//            digest.update(sizeBytes);
//            byte[] hashBytes = digest.digest();
//
//            int hash = 0;
//            for (byte b : hashBytes) {
//                hash = (hash << 8) + (b & 0xff);
//            }
//            return hash;
//        } catch (NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public byte[] calculateContentHashFromFile(File file) {
//        try {
//            MessageDigest digest = MessageDigest.getInstance("SHA-256");
//            FileInputStream fis = new FileInputStream(file);
//            byte[] buffer = new byte[8192];
//            int bytesRead;
//            while ((bytesRead = fis.read(buffer)) != -1) {
//                digest.update(buffer, 0, bytesRead);
//            }
//            fis.close();
//            return digest.digest();
//        } catch (IOException | NoSuchAlgorithmException e) {
//            System.err.println("Ошибка при вычислении хеша содержимого файла " + file.getName() + ": " + e.getMessage());
//            byte[] err = new byte[1];
//            err[0] = (byte) -1;
//            return err;
//        }
//    }

}