package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

/**
 * GKislin
 * 11.01.2015.
 */
public class UserMeal {
    protected final LocalDateTime dateTime;

    protected final String description;

    protected final int calories;

    protected Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public UserMeal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id=id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }
    public UserMeal(LocalDateTime dateTime, String description, int calories) {
      this(null,dateTime,description,calories);
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }
}
