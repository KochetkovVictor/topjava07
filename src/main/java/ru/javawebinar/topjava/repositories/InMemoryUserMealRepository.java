package ru.javawebinar.topjava.repositories;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Collection;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;


public class InMemoryUserMealRepository implements UserMealRepository {

    public static Map<Integer, UserMeal> mealsMap = new ConcurrentHashMap<>();
    public static AtomicInteger counter = new AtomicInteger();


    {
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        save(new UserMeal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    @Override
    public UserMeal save(UserMeal um) {

        if (um.getId() == null) {
            um.setId(counter.incrementAndGet());
            return mealsMap.put(counter.get(), um);

        } else {
            return mealsMap.put(um.getId(), um);
        }
    }

    @Override
    public void remove(int id) {
        mealsMap.remove(id);
    }

    @Override
    public Collection<UserMeal> getAll() {
        return mealsMap.values();
    }

    @Override
    public UserMeal get(int id) {
        return mealsMap.get(id);
    }
}
