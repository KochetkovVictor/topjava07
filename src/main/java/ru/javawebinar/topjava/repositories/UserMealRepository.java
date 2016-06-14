package ru.javawebinar.topjava.repositories;

import ru.javawebinar.topjava.model.UserMeal;

import java.util.Collection;
import java.util.List;

public interface UserMealRepository {
    UserMeal save(UserMeal um);
    void remove(int id);
    Collection<UserMeal> getAll();
    UserMeal get(int id);
}
