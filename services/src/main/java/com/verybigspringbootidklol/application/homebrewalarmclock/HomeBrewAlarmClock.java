package com.verybigspringbootidklol.application.homebrewalarmclock;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class HomeBrewAlarmClock {
    private DateTime currentTime;
    private ArrayList<DateTime> alarmList = new ArrayList<>();
    private String YOUTUBE_URL = "https://www.youtube.com/watch?v=js7mx3EgiDU";

    public void addAlarm(DateTime alarmTime) {
        alarmList.add(alarmTime);
    }

    private void openBrowserAndPlayMusic(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
        }
    }

    private DateTime calculateNextAlarmTime() {
        DateTime nextAlarmTime = null;
        for (DateTime alarm : alarmList) {
            long millisDifference = alarm.getMillis() - currentTime.getMillis();
            if (millisDifference > 0) {
                nextAlarmTime = nextAlarmTime == null ? alarm : (alarm.isBefore(nextAlarmTime) ? alarm : nextAlarmTime);
            }
        }
        return nextAlarmTime;
    }

    private void waitForNextAlarm(DateTime nextAlarmTime) {
        if (nextAlarmTime != null) {
            long delayMillis = nextAlarmTime.getMillis() - currentTime.getMillis();
            if (delayMillis > 0) {
                try {
                    // Introduce a delay until the next alarm time
                    TimeUnit.MILLISECONDS.sleep(delayMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void triggerAlarm(DateTime alarmTime) {
        // Play the video when the alarm time is reached
        openBrowserAndPlayMusic(YOUTUBE_URL);

        // Remove the alarm from the list as it has already been triggered
        alarmList.remove(alarmTime);
    }

    public void startAlarmClock() {
        DateTimeZone singaporeTimeZone = DateTimeZone.forID("Asia/Singapore");
        currentTime = DateTime.now(singaporeTimeZone);

        while (!alarmList.isEmpty()) {
            // Calculate the next alarm time
            DateTime nextAlarmTime = calculateNextAlarmTime();

            // Wait for the next alarm
            waitForNextAlarm(nextAlarmTime);

            // Update the current time
            currentTime = DateTime.now(singaporeTimeZone);

            // Check for alarms and trigger if necessary
            for (DateTime alarm : alarmList) {
                if (currentTime.isAfter(alarm)) {
                    System.out.println("[" + currentTime + "] Alarm set for " + alarm + ", about to call the video!");
                    triggerAlarm(alarm);
                    break; // Break the loop after triggering the first alarm
                } else {
                    long millisDifference = alarm.getMillis() - currentTime.getMillis();
                    int secondsLeft = (int) (millisDifference / 1000);
                    System.out.println("[" + currentTime + "] Alarm set for " + alarm + ", " + secondsLeft + " seconds left.");
                }
            }
        }
    }

    public static void main(String[] args) {
        HomeBrewAlarmClock alarmClock = new HomeBrewAlarmClock();

        DateTimeZone singaporeTimeZone = DateTimeZone.forID("Asia/Singapore");
        DateTime alarmTime = DateTime.now(singaporeTimeZone).withTime(7, 53, 0, 0);
        alarmClock.addAlarm(alarmTime);

        // Start the alarm clock
        alarmClock.startAlarmClock();
    }
}
