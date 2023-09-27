package com.example.ssp.repository;

import com.example.ssp.model.Game;
import com.example.ssp.model.Winner;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GameRepository extends MongoRepository<Game, String> {
   @Query(value = "{winner: ?0}", count = true)
   long countByWinner(Winner winner);

}
