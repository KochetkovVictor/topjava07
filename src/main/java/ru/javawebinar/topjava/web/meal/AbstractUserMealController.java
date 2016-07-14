package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public abstract class AbstractUserMealController {
    private static final Logger LOG = LoggerFactory.getLogger(AbstractUserMealController.class);

    @Autowired
    UserMealService service;

    public List<UserMealWithExceed> getAll() {
        LOG.info("getAll for User {}", AuthorizedUser.id());
        return UserMealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), AuthorizedUser.getCaloriesPerDay());
    }

    public UserMeal get(int id) {
        LOG.info("get the meal number {} for User {}", id, AuthorizedUser.id());
        return service.get(id, AuthorizedUser.id());
    }

    public List<UserMealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime)
    {
        LOG.info("getBetween dates {} - {} for time {} - {} for User {}", startDate, endDate, startTime, endTime, AuthorizedUser.id());
        return UserMealsUtil.getFilteredWithExceeded(
                service.getBetweenDates(
                        startDate != null ? startDate : TimeUtil.MIN_DATE, endDate != null ? endDate : TimeUtil.MAX_DATE, AuthorizedUser.id()),
                startTime != null ? startTime : LocalTime.MIN, endTime != null ? endTime : LocalTime.MAX, AuthorizedUser.getCaloriesPerDay()
        );
    }
    public void delete(int id) {
        int userId = AuthorizedUser.id();
        LOG.info("delete meal {} for User {}", id, userId);
        service.delete(id, userId);
    }
    public void update(UserMeal meal, int id) {
        meal.setId(id);
        int userId = AuthorizedUser.id();
        LOG.info("update {} for User {}", meal, userId);
        service.update(meal, userId);
    }

    public UserMeal create(UserMeal meal) {
        meal.setId(null);
        int userId = AuthorizedUser.id();
        LOG.info("create {} for User {}", meal, userId);
        return service.save(meal, userId);
    }

}
