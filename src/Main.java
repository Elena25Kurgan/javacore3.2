import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static final String FILE_PATH = "D://Games/savegames/";

    public static void saveGame(String filePath, GameProgress gameProgress) {
        try (FileOutputStream fos = new FileOutputStream(filePath);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gameProgress);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void zipFiles (String fileZip, ArrayList<String> filesPath) {
        try (FileOutputStream fos = new FileOutputStream(fileZip);
            ZipOutputStream zout = new ZipOutputStream(fos)) {
            for (String filePath: filesPath) {
                FileInputStream fis = new FileInputStream(FILE_PATH + filePath);
                ZipEntry entry = new ZipEntry(filePath);
                zout.putNextEntry(entry);
                byte[] buffer = new byte[fis.available()];
                fis.read(buffer);
                zout.write(buffer);
                fis.close();
            }
            zout.closeEntry();
        }
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public static void main(String[] args) {
        GameProgress gameProgress1 = new GameProgress(10,40,50, 90.99);
        GameProgress gameProgress2 = new GameProgress(20, 45, 79, 57.8);
        GameProgress gameProgress3 = new GameProgress(45, 89, 34, 87.33);

        ArrayList<String> filesPath = new ArrayList<>();
        filesPath.add("save1.dat");
        filesPath.add("save2.dat");
        filesPath.add("save3.dat");

        saveGame(FILE_PATH + filesPath.get(0), gameProgress1);
        saveGame(FILE_PATH + filesPath.get(1), gameProgress2);
        saveGame(FILE_PATH + filesPath.get(2), gameProgress3);

        zipFiles("D://Games/savegames/zip.zip", filesPath);

        for (String filePath: filesPath) {
            File fileDel = new File(FILE_PATH + filePath);
            try {
                fileDel.delete();
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


}
