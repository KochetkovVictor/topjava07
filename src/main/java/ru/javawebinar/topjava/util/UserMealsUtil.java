package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static final List<UserMeal> MEAL_LIST=new ArrayList<>();
    static
    {
        MEAL_LIST.add(new UserMeal(1, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        MEAL_LIST.add(new UserMeal(2,LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        MEAL_LIST.add(new UserMeal(3,LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        MEAL_LIST.add(new UserMeal(4,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        MEAL_LIST.add(new UserMeal(5,LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        MEAL_LIST.add(new UserMeal(6,LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
    }

    public static void main(String[] args) {

    }

    public static List<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream()
                .collect(
                        Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories))
                );

        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> createWithExceed(um, caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    public static List<UserMealWithExceed> getFilteredWithExceededByCycle(Collection<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        final Map<LocalDate, Integer> caloriesSumPerDate = new HashMap<>();
        mealList.forEach(meal -> caloriesSumPerDate.merge(meal.getDateTime().toLocalDate(), meal.getCalories(), Integer::sum));

        final List<UserMealWithExceed> mealExceeded = new ArrayList<>();
        mealList.forEach(meal -> {
            final LocalDateTime dateTime = meal.getDateTime();
            if (TimeUtil.isBetween(dateTime.toLocalTime(), startTime, endTime)) {
                mealExceeded.add(createWithExceed(meal, caloriesSumPerDate.get(dateTime.toLocalDate()) > caloriesPerDay));
            }
        });
        return mealExceeded;
    }

    public static UserMealWithExceed createWithExceed(UserMeal um, boolean exceeded) {
        return new UserMealWithExceed(um.getId(),um.getDateTime(), um.getDescription(), um.getCalories(), exceeded);
    }
}