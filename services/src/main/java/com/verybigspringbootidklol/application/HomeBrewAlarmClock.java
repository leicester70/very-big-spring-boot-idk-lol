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
    private final ArrayList<DateTime> alarmList = new ArrayList<>();

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
                    TimeUnit.MILLISECONDS.sleep(delayMillis);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void triggerAlarm(DateTime alarmTime) {
        openBrowserAndPlayMusic("https://www.youtube.com/watch?v=js7mx3EgiDU");
        alarmList.remove(alarmTime);
    }

    public void startAlarmClock() {
        DateTimeZone singaporeTimeZone = DateTimeZone.forID("Asia/Singapore");
        currentTime = DateTime.now(singaporeTimeZone);

        System.out.println("Alarm clock started. Current time: " + currentTime);

        while (!alarmList.isEmpty()) {
            DateTime nextAlarmTime = calculateNextAlarmTime();
            while (nextAlarmTime != null && currentTime.isBefore(nextAlarmTime)) {
                long millisDifference = nextAlarmTime.getMillis() - currentTime.getMillis();
                int secondsLeft = (int) (millisDifference / 1000);
                System.out.println(String.format("[%s] Alarm set for %s, %d seconds left.", currentTime, nextAlarmTime, secondsLeft));
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                currentTime = DateTime.now(singaporeTimeZone);
            }

            if (!alarmList.isEmpty()) {
                System.out.println(String.format("[%s] Alarm set for %s, about to call the video!", currentTime, nextAlarmTime));
                triggerAlarm(nextAlarmTime);
            }
        }
        System.out.println("All alarms have been triggered. Alarm clock stopped.");
    }

    public static void main(String[] args) {
        HomeBrewAlarmClock alarmClock = new HomeBrewAlarmClock();

        DateTimeZone singaporeTimeZone = DateTimeZone.forID("Asia/Singapore");
        DateTime alarmTime = DateTime.now(singaporeTimeZone).withTime(1, 30, 0, 0);
        alarmClock.addAlarm(alarmTime);

        alarmClock.startAlarmClock();
    }
}
