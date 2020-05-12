package ru.votingsystems.restraurantvotingsystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.votingsystems.restraurantvotingsystem.model.Restaurant;

import java.util.List;

@Transactional(readOnly = true)
public interface RestaurantRepository extends JpaRepository<Restaurant, Integer> {

    @Transactional
    @Modifying
    int deleteRestaurantById(int id);

    @Transactional
    @Modifying
    @Query("UPDATE Restaurant r SET r.rating = r.rating + 1 WHERE r.id=:id")
    void incrementRating(@Param("id") int id);

    @Transactional
    @Modifying
    @Query("UPDATE Restaurant r SET r.rating = r.rating - 1 WHERE r.id=:id")
    void decrementRating(@Param("id") int id);

}
