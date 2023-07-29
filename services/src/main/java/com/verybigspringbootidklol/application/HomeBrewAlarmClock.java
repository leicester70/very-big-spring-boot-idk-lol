package com.verybigspringbootidklol.application;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

public class HomeBrewAlarmClock {
    private boolean alarmTriggered = false; // Flag to indicate if an alarm is triggered
    private static final DateTimeZone singaporeTimeZone = DateTimeZone.forID("Asia/Singapore");
    private ArrayList<DateTime> alarmList = new ArrayList<>();
    private DateTime currentTime = DateTime.now(singaporeTimeZone);

//    public static void main(String[] args) throws Exception {
//        //  @TODO maybe do something about args idklol
//        HomeBrewAlarmClock alarmClock = new HomeBrewAlarmClock();
//        alarmClock.addAlarm(23,0);
//        System.out.println("main() starting alarm");
//        alarmClock.startAlarmClock();
//    }

    public static void main(String[] args) throws Exception {
        //  @TODO maybe do something about args idklol
        HomeBrewAlarmClock alarmClock = new HomeBrewAlarmClock();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the hour and minute for the alarm (in 24 hour format HHMM, e.g. 1645 for 4:45 PM): ");
        String input = scanner.nextLine();
        if (input.length() != 4) {
            throw new Exception("you gave shit inputs, HomeBrewAlarmClock refused to go on");
        }
        int hour = Integer.parseInt(input.substring(0, 2));
        int minute = Integer.parseInt(input.substring(2, 4));
        try {
            alarmClock.addAlarm(hour, minute);
            System.out.println("main() trying to starting alarm...");
            alarmClock.startAlarmClock();
        } catch (Exception e) {
            System.out.println("Error adding alarm: " + e.getMessage());
        }
    }

    private void triggerAlarm(DateTime alarmTime) {
        alarmTriggered = true;
        alarmList.remove(alarmTime);
//        openBrowserAndPlayMusic("https://www.youtube.com/watch?v=js7mx3EgiDU");
    }

    public void addAlarm(int HH, int MM) {
        int iDontCareNobodyDoes = 0;
        DateTime alarmTime = DateTime.now(singaporeTimeZone).withTime(HH, MM, iDontCareNobodyDoes, iDontCareNobodyDoes);
        System.out.printf("%s added to %s\n", alarmTime.toString(), alarmList.toString());
        alarmList.add(alarmTime);
        System.out.println("alarmList after addition: " + alarmList.toString().toString());
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
                    TimeUnit.MILLISECONDS.sleep(delayMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void startAlarmClock() throws Exception {
        DateTime nextAlarmTime = calculateNextAlarmTime();

        if (alarmList.isEmpty()) {
            throw new Exception("HomeBrewAlarmClock refused to start, there are no alarms set.");
        }
        if (!currentTime.isBefore(nextAlarmTime)) {
            throw new Exception("HomeBrewAlarmClock refused to start, currentTime has passed the latest added alarm time.");
        }

        while (!alarmList.isEmpty() && !alarmTriggered) {
            while (nextAlarmTime != null && currentTime.isBefore(nextAlarmTime)) {
                long millisDifference = nextAlarmTime.getMillis() - currentTime.getMillis();
                int secondsLeft = (int) (millisDifference / 1000);
                System.out.printf("[%s] Alarm set for %s, %d seconds left.%n", currentTime, nextAlarmTime, secondsLeft);
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("Alarm clock stopped.");
                    System.exit(0);
                }
                currentTime = DateTime.now(singaporeTimeZone);
            }
            if (alarmList.isEmpty() && !alarmTriggered) {
                System.out.printf("[%s] Alarm set for %s, about to call the video!%n", currentTime, nextAlarmTime);
                triggerAlarm(nextAlarmTime);
            }
            // Wait for the next alarm to go off
            waitForNextAlarm(nextAlarmTime);
        }
        System.out.println("All alarms have been triggered. Alarm clock stopped.");
    }
}
