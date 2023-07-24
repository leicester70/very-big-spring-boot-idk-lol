package com.verybigspringbootidklol.application.homebrewalarmclock;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.awt.*;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;

public class HomeBrewAlarmClock {
    private ArrayList<DateTime> alarmList = new ArrayList<>();
    private String VIDEO_FILE_PATH = "/services/mp4/I'll Make a Man Out of You METAL COVER - Mulan.mp4";
    private final int COOLDOWN_TIME_SECONDS = 30; // Set the cooldown time to 30 seconds

    public void addAlarm(DateTime alarmTime) {
        alarmList.add(alarmTime);
    }

    public void playVideoFile(String filePath) {
        URL videoUrl = getClass().getResource(filePath);
        if (videoUrl != null) {
            try {
                String path = Paths.get(videoUrl.toURI()).toString();
                Desktop.getDesktop().open(Paths.get(path).toFile());
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("File not found: " + filePath);
        }
    }

    public static void main(String[] args) {
        HomeBrewAlarmClock alarmClock = new HomeBrewAlarmClock();

        DateTimeZone singaporeTimeZone = DateTimeZone.forID("Asia/Singapore");
        DateTime alarmTime = DateTime.now(singaporeTimeZone).withTime(1, 1, 0, 0);
        alarmClock.addAlarm(alarmTime);

        while (true) {
            DateTime currentTime = DateTime.now();
            System.out.println("[" + currentTime + "] Current time");

            for (DateTime alarm : alarmClock.alarmList) {
                long millisDifference = alarm.getMillis() - currentTime.getMillis();
                if (millisDifference <= 0) {
                    System.out.println("[" + currentTime + "] Alarm set for " + alarm + ", about to call the video!");
                    alarmClock.playVideoFile(alarmClock.VIDEO_FILE_PATH);
                    break;
                } else {
                    int secondsLeft = (int) (millisDifference / 1000);
                    System.out.println("[" + currentTime + "] Alarm set for " + alarm + ", " + secondsLeft + " seconds left.");
                }
            }

            try {
                Thread.sleep(alarmClock.COOLDOWN_TIME_SECONDS * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}