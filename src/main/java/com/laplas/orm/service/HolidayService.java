package com.laplas.orm.service;

import com.laplas.orm.entity.Holiday;
import com.laplas.orm.helper.HolidayFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@Service
@Transactional
public class HolidayService {

    private final EntityManager entityManager;

    @Autowired
    public HolidayService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Holiday create(String name) {
        Holiday holiday = HolidayFactory.createHoliday(name);
        entityManager.persist(holiday);
        return holiday;
    }

    public Holiday get(long id) {
        return entityManager.find(Holiday.class, id);
    }

    public Pair<Holiday, Holiday> getCheckingTwice(long id) {
        Holiday first = entityManager.find(Holiday.class, id);
        Holiday second = entityManager.find(Holiday.class, id);
        return Pair.of(first, second);
    }
}
