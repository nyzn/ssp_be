package com.example.ssp.service;

import com.example.ssp.model.Game;
import com.example.ssp.model.HandSign;
import com.example.ssp.model.History;
import com.example.ssp.model.Winner;
import com.example.ssp.repository.GameRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;

    public History getHistory() {
        long playerCount = gameRepository.countByWinner(Winner.PLAYER);
        long npcCount = gameRepository.countByWinner(Winner.NPC);
        long ties = gameRepository.countByWinner(Winner.TIE);
        List<Game> games = gameRepository.findAll();
        return new History(games, playerCount, npcCount, ties);

    }

    public Game saveResult(HandSign playerHandSign) {
        Game game = new Game();
        game.setPlayer(playerHandSign);

        HandSign npcHandSign = HandSign.randomGameType();
        game.setNpc(npcHandSign);

        game.setWinner(calculateWinner(playerHandSign, npcHandSign));
        return gameRepository.save(game);
    }
    public void deleteAll() {
        gameRepository.deleteAll();
    }
    private Winner calculateWinner(HandSign playerGameType, HandSign npcHandSign) {
        if(playerGameType.equals(npcHandSign)) {
            return Winner.TIE;
        }
        if(playerWins(playerGameType, npcHandSign)) {
            return Winner.PLAYER;
        }

        return Winner.NPC;
    }
    private boolean playerWins(HandSign player, HandSign npc) {
        if (player.equals(HandSign.ROCK)) {
            return npc.equals(HandSign.SCISSOR);
        } else if (player.equals(HandSign.PAPER)) {
            return npc.equals(HandSign.ROCK);
        } else {
            return npc.equals(HandSign.PAPER);
        }
    }


}
