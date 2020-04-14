package ru.votingsystems.restraurantvotingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.votingsystems.restraurantvotingsystem.model.Dish;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {


    @Transactional
    @Modifying
    @Query("DELETE FROM Restaurant r WHERE r.id=:id")
    int delete(@Param("id") int id);

//    public void inputNewRestaurant(Restaurant newRestaurant){}

    @Transactional
    @Modifying
    @Query("Restaurant r WHERE r.id=:id") // set r.menu = menu
    void setNewMenu(@Param("id") int id, @Param("menu")List<Dish> menu);


    @Transactional
    @Modifying
    @Query("Restaurant r WHERE r.id=:id") // set r.menu = menu
    void updateRating(@Param("id") int id, @Param("rating") int rating);
}
