package com.example.ssp.service;

import com.example.ssp.model.Game;
import com.example.ssp.model.HandSign;
import com.example.ssp.model.Winner;
import com.example.ssp.repository.GameRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockedStatic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

public class GameServiceTest {

    private GameService sut;
    private GameRepository gameRepository;
    private static MockedStatic<HandSign> mockedHandSign;

    @BeforeEach
    public void setup() {
        gameRepository = mock(GameRepository.class);
        mockedHandSign = mockStatic(HandSign.class);

        sut = new GameService(gameRepository);
    }

    @AfterEach
    public void close() {
        mockedHandSign.close();
    }

    @Test
    public void should_get_history() {
        sut.getHistory();
        verify(gameRepository).findAll();
    }

    @Test
    public void should_save_result_with_winner_player() {
        HandSign playerHandSign = HandSign.ROCK;
        HandSign npcHandSign = HandSign.SCISSOR;

        given(HandSign.randomGameType()).willReturn(npcHandSign);
        sut.saveResult(playerHandSign);

        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).save(gameArgumentCaptor.capture());

        Game result = gameArgumentCaptor.getValue();

        assertEquals(playerHandSign, result.getPlayer());
        assertEquals(npcHandSign, result.getNpc());
        assertEquals(Winner.PLAYER, result.getWinner());
    }

    @Test
    public void should_save_result_with_winner_npc() {
        HandSign playerHandSign = HandSign.SCISSOR;
        HandSign npcHandSign = HandSign.ROCK;

        given(HandSign.randomGameType()).willReturn(npcHandSign);

        sut.saveResult(playerHandSign);

        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).save(gameArgumentCaptor.capture());

        Game result = gameArgumentCaptor.getValue();

        assertEquals(playerHandSign, result.getPlayer());
        assertEquals(npcHandSign, result.getNpc());
        assertEquals(Winner.NPC, result.getWinner());
    }

    @Test
    public void should_save_result_with_tie() {
        HandSign playerHandSign = HandSign.SCISSOR;
        HandSign npcHandSign = HandSign.SCISSOR;

        given(HandSign.randomGameType()).willReturn(npcHandSign);

        sut.saveResult(playerHandSign);

        ArgumentCaptor<Game> gameArgumentCaptor = ArgumentCaptor.forClass(Game.class);
        verify(gameRepository).save(gameArgumentCaptor.capture());

        Game result = gameArgumentCaptor.getValue();

        assertEquals(playerHandSign, result.getPlayer());
        assertEquals(npcHandSign, result.getNpc());
        assertEquals(Winner.TIE, result.getWinner());
    }
}
