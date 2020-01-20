package com.laplas.orm.cache;

import com.laplas.orm.entity.Holiday;
import com.laplas.orm.service.HolidayService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.util.Pair;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class FirstLevelCacheTest {

    @Autowired
    private HolidayService holidayService;
    private Holiday holiday;

    @BeforeEach
    void setUp() {
        holiday = holidayService.create("Christmas");
    }

    @Test
    void shouldHitDatabaseSingleTime() {
        Pair<Holiday, Holiday> holidayPair = holidayService.getCheckingTwice(this.holiday.getId());
        assertThat(holidayPair.getFirst()).isSameAs(holidayPair.getSecond());
    }

    @Test
    void shouldHitDatabaseTwoTimes() {
        Holiday holidayFetchedFirst = holidayService.get(this.holiday.getId());
        Holiday holidayFetchedSecond = holidayService.get(this.holiday.getId());
        assertThat(holidayFetchedFirst).isNotSameAs(holidayFetchedSecond);
    }
}
