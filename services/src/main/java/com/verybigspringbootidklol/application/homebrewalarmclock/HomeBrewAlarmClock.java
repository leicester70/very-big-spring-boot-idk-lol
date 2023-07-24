package com.verybigspringbootidklol.application.homebrewalarmclock;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

public class HomeBrewAlarmClock {
    private DateTime currentTime;
    private ArrayList<DateTime> alarmList = new ArrayList<>();
    private String YOUTUBE_URL = "https://www.youtube.com/watch?v=js7mx3EgiDU";
    private final Boolean isLoop = true;

    public void addAlarm(DateTime alarmTime) {
        alarmList.add(alarmTime);
    }

    public void checkAlarms() {
        for (DateTime alarmTime : alarmList) {
            if (alarmTime.equals(currentTime)) {
                openBrowserAndPlayMusic(YOUTUBE_URL, isLoop);
                break;
            }
        }
    }

    private static void openBrowserAndPlayMusic(String url, boolean isLoop) {
        if (isLoop) {
            url += "&loop=1";
        }
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        HomeBrewAlarmClock alarmClock = new HomeBrewAlarmClock();

        // Set the alarm time for 7:44 AM Singapore time
        DateTimeZone singaporeTimeZone = DateTimeZone.forID("Asia/Singapore");
        DateTime alarmTime = DateTime.now(singaporeTimeZone).withTime(7, 53, 0, 0);
        alarmClock.addAlarm(alarmTime);

        // Keep the alarm clock running indefinitely
        while (true) {
            // Set the current time to the time when you run the application
            DateTime currentTime = DateTime.now();
            alarmClock.currentTime = currentTime;

            // Check for alarms and print information
            System.out.println("[" + currentTime + "] Current time");
            for (DateTime alarm : alarmClock.alarmList) {
                long millisDifference = alarm.getMillis() - currentTime.getMillis();
                if (millisDifference > 0) {
                    int secondsLeft = (int) (millisDifference / 1000);
                    System.out.println("[" + currentTime + "] Alarm set for " + alarm + ", " + secondsLeft + " seconds left.");
                } else {
                    System.out.println("[" + currentTime + "] Alarm set for " + alarm + ", about to call the video!");
                    alarmClock.openBrowserAndPlayMusic(alarmClock.YOUTUBE_URL, alarmClock.isLoop);
                }
            }

            // Check for alarms
            alarmClock.checkAlarms();

            // Introduce a delay of 1 second before the next check
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
