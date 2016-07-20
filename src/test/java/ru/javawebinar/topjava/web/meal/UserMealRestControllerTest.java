package ru.javawebinar.topjava.web.meal;

import org.hibernate.mapping.Array;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;


import java.time.LocalDateTime;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


public class UserMealRestControllerTest extends AbstractControllerTest{

    private static final String REST_URL = UserMealRestController.REST_URL + '/';

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get(REST_URL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(UserMealsUtil.getWithExceeded(USER_MEALS,UserMealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    public void tesGet() throws Exception {
        mockMvc.perform(get(REST_URL+"100007"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL6));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL+"100007"))
                .andDo(print())
                .andExpect(status().isOk());
                MATCHER.assertCollectionEquals(USER_MEALS1, mealService.getAll(100000));
    }

    @Test
    public void testUpdate() throws Exception {
        UserMeal updated=MEAL1;
        updated.setCalories(230);
        updated.setDescription("Легкий завтрак");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());
       MATCHER.assertEquals(updated,mealService.get(MEAL1_ID,USER_ID));
    }

    @Test
    public void testGetBetween() throws Exception {
        mockMvc.perform(get(REST_URL+"between?startDateTime=2015-05-30T10:00&endDateTime=2015-05-30T14:00"))
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                MATCHER.assertCollectionEquals(
                        Arrays.asList(MEAL2,MEAL1),
                        mealService.getBetweenDateTimes(TimeUtil.parseLocalDateTime("2015-05-30 10:00"),
                                                        TimeUtil.parseLocalDateTime("2015-05-30 14:00"),
                                                        USER_ID));

    }

    @Test
    public void testCreateWithLocation() throws Exception {
        UserMeal expected = new UserMeal(LocalDateTime.now(), "newPass", 1488);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        UserMeal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertCollectionEquals(Arrays.asList(expected,MEAL6,MEAL5,MEAL4,MEAL3,MEAL2,MEAL1), mealService.getAll(USER_ID));
    }

}