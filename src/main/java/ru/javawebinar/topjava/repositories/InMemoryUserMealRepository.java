package ru.javawebinar.topjava.repositories;

import ru.javawebinar.topjava.model.UserMeal;

public interface InMemoryUserMealRepository {
    UserMeal add(UserMeal um);
    void delete(int id);
    UserMeal update(int id);
    UserMeal get(int id);
}
