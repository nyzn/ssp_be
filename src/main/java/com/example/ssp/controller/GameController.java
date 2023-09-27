package com.example.ssp.controller;

import com.example.ssp.model.Game;
import com.example.ssp.model.HandSign;
import com.example.ssp.model.History;
import com.example.ssp.service.GameService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "/game")
public class GameController {
    private final GameService gameService;

    @GetMapping(path = "")
    public ResponseEntity<History> getHistory() {
        return ResponseEntity.ok().body(gameService.getHistory());
    }

    @PostMapping(path = "/result")
    public ResponseEntity<Game> saveResult(@RequestBody HandSign userGameType) {
        return new ResponseEntity<Game>(gameService.saveResult(userGameType), HttpStatus.CREATED);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll() {
        gameService.deleteAll();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
