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
    private final Boolean isLoop = false;

    public void addAlarm(DateTime alarmTime) {
        alarmList.add(alarmTime);
    }

    public void checkAlarms() {
        for (DateTime alarmTime : alarmList) {
            if (alarmTime.equals(currentTime)) {
                openBrowserAndPlayMusic("https://www.youtube.com/watch?v=js7mx3EgiDU",false);
                try {
                    Desktop.getDesktop().browse(new URI(youtubeUrl));
                } catch (IOException | URISyntaxException e) {
                    e.printStackTrace();
                };
                break;
            }
        }
    }

    private static void openBrowserAndPlayMusic(String url, boolean isLoop){
        String youtubeUrl = "https://www.youtube.com/watch?v=js7mx3EgiDU";
        if (isLoop){youtubeUrl+="&loop=1";}
        try {
            Desktop.getDesktop().browse(new URI(youtubeUrl));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        if (isLoop){YOUTUBE_URL+="&loop=1";}
        HomeBrewAlarmClock alarmClock = new HomeBrewAlarmClock();

        // Set the alarm time for 7:00 AM Singapore time
        DateTimeZone singaporeTimeZone = DateTimeZone.forID("Asia/Singapore");
        DateTime alarmTime = DateTime.now(singaporeTimeZone).withTime(7, 44, 0, 0);
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
                    openBrowserAndPlayMusic("https://www.youtube.com/watch?v=js7mx3EgiDU",false);
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
