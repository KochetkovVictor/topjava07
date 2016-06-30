package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.MealTestData.ADMIN_MEAL2;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml","spring/spring-db.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
         //   System.out.println(adminUserController.create(UserTestData.USER));
            System.out.println();

            UserMealRestController mealController = appCtx.getBean(UserMealRestController.class);
            List<UserMealWithExceed> filteredMealsWithExceeded =
                    mealController.getBetween(
                            LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
            filteredMealsWithExceeded.forEach(System.out::println);
            UserMealRepository repository=appCtx.getBean(UserMealRepository.class);
            System.out.println("User before");
            repository.getAll(USER_ID).forEach(System.out::println);
            System.out.println("ADMIN Before");
            repository.getAll(ADMIN_ID).forEach(System.out::println);
            repository.getAll(USER_ID).forEach(um -> repository.delete(um.getId(),USER_ID));
            repository.getAll(ADMIN_ID).forEach(um -> repository.delete(um.getId(),ADMIN_ID));
            System.out.println("User after");
            repository.getAll(USER_ID).forEach(System.out::println);
            System.out.println("ADMIN after");
            repository.getAll(ADMIN_ID).forEach(System.out::println);

            System.out.println("!!!!!!");

            repository.getAll(USER_ID).forEach(System.out::println);
            repository.save(MEAL1,USER_ID);
            repository.save(MEAL2,USER_ID);
            repository.save(MEAL3,USER_ID);
            repository.save(MEAL4,USER_ID);
            repository.save(MEAL5,USER_ID);
            repository.save(MEAL6,USER_ID);
            repository.save(ADMIN_MEAL,ADMIN_ID);
            repository.save(ADMIN_MEAL2,ADMIN_ID);
            System.out.println("User after");
            repository.getAll(USER_ID).forEach(System.out::println);
            System.out.println("ADMIN after");
            repository.getAll(ADMIN_ID).forEach(System.out::println);
        }
    }
}
