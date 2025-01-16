package com.libcentro.demo.services.interfaces;

import java.util.List;

public interface IbackupService {

    public List<String[]> getBackupList();

    public void backup();

    public void delete( String backupName );

    boolean backupControl ();
}
