package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;
import java.util.List;


public interface ProxyUserMealRepository extends JpaRepository<UserMeal,Integer> {


    @Query(name=UserMeal.ALL_SORTED)
    List<UserMeal> findAll(@Param("userId") int userId );

    @Override
    @Modifying
    UserMeal  save(UserMeal userMeal);


    @Query(name=UserMeal.GET)
    UserMeal findOne(@Param("id") int id, @Param("userId") int userId);


    @Modifying
    @Query("DELETE FROM UserMeal um WHERE um.user.id=:userId AND um.id=:id")
    @Transactional
    int delete(@Param("id")int id, @Param("userId") int userId);

    @Query(name=UserMeal.GET_BETWEEN)
    List<UserMeal> getBetween(@Param("startDate") LocalDateTime startDate,@Param("endDate") LocalDateTime endDate,@Param("userId") int userId);
}
