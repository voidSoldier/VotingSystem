package ru.votingsystems.restraurantvotingsystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;
import ru.votingsystems.restraurantvotingsystem.model.User;

import java.util.Collection;
import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);


//    @Transactional
//    @Modifying
//    @Query("UPDATE Restaurant r SET r.menu = :menu WHERE r.id=:id")
//    void updateMenu(@Param("id") int id, @Param("menu") Collection<Dish> menu);


    @Transactional
    @Modifying
    @Query("UPDATE Restaurant r SET r.rating = :rating WHERE r.id=:id")
    void updateRating(@Param("id") int id, @Param("rating") int rating);


    //    https://stackoverflow.com/a/46013654/548473
//    @EntityGraph(attributePaths = {"menu"}, type = EntityGraph.EntityGraphType.LOAD)
    @Query("SELECT r FROM Restaurant r")
    List<Restaurant> getAllWithMenu();
}
