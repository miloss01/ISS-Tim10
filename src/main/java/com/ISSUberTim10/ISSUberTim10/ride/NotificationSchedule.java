package com.ISSUberTim10.ISSUberTim10.ride;

import com.ISSUberTim10.ISSUberTim10.appUser.account.Passenger;
import com.ISSUberTim10.ISSUberTim10.helper.StringFormatting;
import com.ISSUberTim10.ISSUberTim10.ride.dto.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class NotificationSchedule {

    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    private Map<Long, Ride> toBeReminded = new HashMap<>();
    private Map<Long, LocalDateTime> delays = new HashMap<>();

    public void addToBeReminded(Ride ride) {
        System.out.println("added to be reminded: " + ride.getId());
        this.toBeReminded.put(ride.getId(), ride);
        this.delays.put(ride.getId(), LocalDateTime.now().plusMinutes(15));
    }

    public void removeToBeReminded(Ride ride) {
        System.out.println("removed to be reminded: " + ride.getId());
        this.toBeReminded.remove(ride.getId());
    }


    @Scheduled(fixedDelay = 1000 * 40)
    private void scheduleSendingReminderNotifications() {

        LocalDateTime now = LocalDateTime.now();
        for (Map.Entry<Long, Ride> entry : toBeReminded.entrySet()) {

            if (delays.get(entry.getKey()).isAfter(now)) continue; // It's not time to send the first, 15 min notif yet
            else if (delays.get(entry.getKey()).getHour() == now.getHour() && delays.get(entry.getKey()).getMinute() == now.getMinute()) {
                System.out.println("Fixed rate first notification at: " + LocalDateTime.now().format(StringFormatting.dateTimeFormatter) + " for ride at " + entry.getValue().getStartTime().format(StringFormatting.dateTimeFormatter));
                sendToAllPassengers(entry.getValue());
                continue;
            }

            LocalDateTime startTime = entry.getValue().getStartTime();
            LocalDateTime t = LocalDateTime.of(startTime.getYear(), startTime.getMonth(), startTime.getDayOfMonth(), startTime.getHour(), startTime.getMinute());
            while (t.isAfter(now)) {
                t = t.minusMinutes(5);
                if (t.getHour() == now.getHour() && t.getMinute() == now.getMinute()) {
                    sendToAllPassengers(entry.getValue());
                }
            }
        }
    }

    private void sendToAllPassengers(Ride ride) {
        for (Passenger p : ride.getPassengers()) {
            System.out.println("Fixed rate notification at: " + LocalDateTime.now().format(StringFormatting.dateTimeFormatter) + " for ride at " + ride.getStartTime().format(StringFormatting.dateTimeFormatter));
            this.simpMessagingTemplate.convertAndSend("/ride-notification-passenger/" + p.getId(),
                    new NotificationDTO("Reminder: You have a ride booked at "
                            + ride.getStartTime().format(StringFormatting.dateTimeFormatter), ride.getId().intValue(), ""));
        }
    }

}

