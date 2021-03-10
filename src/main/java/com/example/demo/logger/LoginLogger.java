package com.example.demo.logger;

import com.example.demo.models.Profile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LoginLogger implements Runnable{

    private final File file;
    private final Profile user;

    public LoginLogger(Profile user) {
        file = new File("src/main/resources/logs/login.log");
        this.user = user;
    }

    @Override
    public void run() {
        Date currentDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd 'at' HH:mm:ss");
        String logMsg = "";
        if (user.getRoles().get(0).getRoleName().equals("ROLE_ADMIN")) {
            logMsg = "[Admin has entered the website " + dateFormat.format(currentDate)
                    + " " + user.getUsername()
                    + " " + user.getRoles().get(0).getRoleName()
                    + "]";
        } else {
            logMsg = "[User has entered the website " + dateFormat.format(currentDate)
                    + " " + user.getGroupId().getGroupName()
                    + " " + user.getUsername()
                    + " " + user.getRoles().get(0).getRoleName()
                    + "]";
        }


        LogWriter.writeLog(file, logMsg);
    }

}
