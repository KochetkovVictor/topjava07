package ru.javawebinar.topjava.web;

import com.sun.org.apache.xpath.internal.SourceTree;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;
import ru.javawebinar.topjava.repositories.InMemoryUserMealRepository;
import ru.javawebinar.topjava.repositories.UserMealRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger LOG = getLogger(UserServlet.class);
    UserMealRepository repo = new InMemoryUserMealRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("action") == null) {
            LOG.debug("redirect to mealList");
            List<UserMealWithExceed> mealWithExceedList = UserMealsUtil.getFilteredWithExceeded(repo.getAll(), LocalTime.of(7, 0), LocalTime.of(20, 0), 2000);
            request.setAttribute("mealList", mealWithExceedList);
            request.getRequestDispatcher("/mealList.jsp").forward(request, response);
        } else {
             if (request.getParameter("action").equals("delete")) {
                int id = Integer.valueOf(request.getParameter("id"));
                 LOG.debug("delete "+id);
                repo.remove(id);
                response.sendRedirect("meals");
            }
            else {
                 UserMeal meal=request.getParameter("action").equals("add") ? new UserMeal(LocalDateTime.now(),"",1000): repo.get(Integer.valueOf(request.getParameter("id")));
                 LOG.debug("add or edit "+meal.getId());
                 request.setAttribute("meal",meal);
                 request.getRequestDispatcher("mealEdit.jsp").forward(request,response);
             }
        }
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String id=request.getParameter("id");
        LOG.debug("Edit or Add "+id);
        UserMeal um=new UserMeal(id.isEmpty() ? null:Integer.valueOf(id),
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.valueOf(request.getParameter("calories")));
        repo.save(um);
        response.sendRedirect("meals");
    }
}
