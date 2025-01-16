package com.libcentro.demo.services;

import com.libcentro.demo.config.AppConfig;
import com.libcentro.demo.services.interfaces.IbackupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SQLiteBackupService implements IbackupService {

    private final String databasePath = "database_sqlite/libreria_db.db";
    private final String backupDirectory = AppConfig.backup_path;

    @Override
    public List<String[]> getBackupList() {
        File backupDir = new File(backupDirectory);
        if (!backupDir.exists()) {
            backupDir.mkdirs();
        }

        File[] backupFiles = backupDir.listFiles((dir, name) -> name.endsWith(".db"));
        if (backupFiles != null) {
            return Stream.of(backupFiles)
                    .map(file -> new String[]{
                            file.getName(),
                            new java.text.SimpleDateFormat("dd-MM-yyyy HH:mm:ss").format(new java.util.Date(file.lastModified()))
                    })
                    .collect(Collectors.toList());
        }

        return new ArrayList<>();
    }

    @Override
    public void backup() {
        String backupFileName = "backup_" + System.currentTimeMillis() + ".db";
        String backupFilePath = Paths.get(backupDirectory, backupFileName).toString();

        File sourceFile = new File(databasePath);
        File backupFile = new File(backupFilePath);

        try {
            Files.copy(sourceFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Backup completado: " + backupFilePath);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al crear el backup.");
        }
    }

    @Override
    public void delete(String backupName) {
        String backupPath = Paths.get(backupDirectory, backupName).toString();
        File backupFile = new File(backupPath);

        if (backupFile.exists()) {
            if (backupFile.delete()) {
                System.out.println("Backup eliminado: " + backupPath);
            } else {
                System.out.println("No se pudo eliminar el backup.");
            }
        } else {
            System.out.println("El archivo de backup no existe: " + backupPath);
        }
    }

    @Override
    public boolean backupControl() {
        File backupDir = new File(backupDirectory);

        if (!backupDir.exists() || !backupDir.isDirectory()) {
            return false;
        }

        File[] backupFiles = backupDir.listFiles((dir, name) -> name.endsWith(".db"));
        if (backupFiles == null || backupFiles.length == 0) {
            return false;
        }

        long lastModified = 0;
        for (File file : backupFiles) {
            if (file.lastModified() > lastModified) {
                lastModified = file.lastModified();
            }
        }
        Date lastBackupDate = new Date(lastModified);
        Date currentDate = new Date();

        long diffInMillies = Math.abs(currentDate.getTime() - lastBackupDate.getTime());
        double diffInDays = (double) diffInMillies / (1000 * 60 * 60 * 24);

        return diffInDays > 2;
    }
}