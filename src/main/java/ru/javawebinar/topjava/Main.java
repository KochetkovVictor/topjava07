package ru.javawebinar.topjava;

import ru.javawebinar.topjava.repositories.InMemoryUserMealRepository;
import ru.javawebinar.topjava.repositories.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalTime;

/**
 * User: gkislin
 * Date: 05.08.2015
 *
 * @link http://caloriesmng.herokuapp.com/
 * @link https://github.com/JavaOPs/topjava
 */
public class Main {
    static UserMealRepository repo=new InMemoryUserMealRepository();
    public static void main(String[] args) {
        System.out.format("Hello Topjava Enterprise!");
       repo.getAll().forEach(meal-> System.out.println(meal.getId()));
    }
}
