package com.laplas.orm.helper;

import com.laplas.orm.entity.Holiday;

import java.util.concurrent.atomic.AtomicLong;

public class HolidayFactory {

    private static AtomicLong sequence = new AtomicLong(1L);

    public static Holiday createHoliday(String name) {
        Holiday holiday = new Holiday();
        holiday.setId(sequence.getAndIncrement());
        holiday.setName(name);
        return holiday;
    }
}
