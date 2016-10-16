package com.tsystems.javaschool.logiweb.service.helper;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class WorkingHoursCalc {

    /**
     * Returns how many hours drivers should drive in this month with give start and end times of duty.
     *
     * @param dutyStart Beginning of duty
     * @param dutyEnd Ending of duty
     * @return
     */
    public static double getRequiredWorkHoursInCurrentMonth(LocalDateTime dutyStart, LocalDateTime dutyEnd) {
        double requiredWorkHours;

        if (dutyStart.getMonthValue() == dutyEnd.getMonthValue()) {
            requiredWorkHours = ChronoUnit.HOURS.between(dutyStart, dutyEnd);
        } else {
            // In case a trip passes the month border, we just care about current month
            // Let's see how many hours left till month end
            LocalDateTime endOfMonth = LocalDateTime.of(
                    dutyStart.getYear(),
                    dutyStart.getMonth().plus(1),
                    1,
                    0, 0, 0);
            requiredWorkHours = ChronoUnit.HOURS.between(dutyStart, endOfMonth);
        }
        return requiredWorkHours;
    }
}
