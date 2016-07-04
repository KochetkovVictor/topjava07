package ru.javawebinar.topjava.service;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.UserMealRestController;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN;

import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({"classpath:spring/spring-app.xml","classpath:spring/spring-db.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    private UserMealRestController controller;

    @Autowired
    protected UserMealService service;

    @Autowired
    private UserMealRepository repository;

    @Autowired
    private DbPopulator populator;

    @Before
    public void setUp() throws Exception {
        repository.getAll(USER_ID).forEach(um -> repository.delete(um.getId(),USER_ID));
        repository.getAll(ADMIN_ID).forEach(um -> repository.delete(um.getId(),ADMIN_ID));

        populator.execute();
    }

    @Test
    public void testGet() throws Exception {

        Assert.assertEquals(MEAL1,controller.get(100002));
    }

    @Test
    public void testDelete() throws Exception {
        repository.delete(100002,100000);
        List<UserMealWithExceed> meals=controller.getAll();
        Assert.assertEquals(meals.size(),5);
        MATCHER_WITH_EXCEED.assertCollectionEquals(meals,UserMealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2),2000));
    }


    @Test
    public void testGetAll() throws Exception {
        List<UserMealWithExceed> meals=controller.getAll();
        MATCHER_WITH_EXCEED.assertCollectionEquals(meals,UserMealsUtil.getWithExceeded(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2,MEAL1),2000));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal meal=repository.get(100_002,USER_ID);
        meal.setDescription("Changed meal");
        controller.update(meal,meal.getId());
        Assert.assertEquals(controller.get(100_002).getDescription(),"Changed meal");
    }

    @Test
    public void testSave() throws Exception {
        UserMeal newMeal=new UserMeal();
        newMeal.setDescription("toast save");
        newMeal.setCalories(700);
        newMeal.setDateTime(LocalDateTime.now());
        controller.create(newMeal);
        List<UserMealWithExceed> meals=controller.getAll();
        MATCHER_WITH_EXCEED.assertCollectionEquals(meals,UserMealsUtil.getWithExceeded(Arrays.asList(newMeal,MEAL6, MEAL5, MEAL4, MEAL3, MEAL2,MEAL1),2000));
    }

    @Test(expected = NotFoundException.class)
    public void upDateNotFound() throws Exception{
        UserMeal userMeal=repository.get(MEAL1_ID,USER_ID);
        service.update(userMeal,ADMIN_ID);
    }
}