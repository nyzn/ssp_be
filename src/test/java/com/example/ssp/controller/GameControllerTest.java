package com.example.ssp.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.ssp.service.GameService;
import com.example.ssp.model.HandSign;
import com.example.ssp.model.Game;

@RunWith(SpringRunner.class)
@WebMvcTest(value = GameController.class, excludeAutoConfiguration = SecurityAutoConfiguration.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Before
    public void setUp() throws Exception {
    }

    @Test 
    public void should_call_get_History() throws Exception {
        this.mockMvc.perform(
                get("/game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isOk());

        verify(gameService, VerificationModeFactory.times(1)).getHistory();
        reset(gameService);
    }


    @Test
    public void should_call_save_result_Rock() throws Exception {
        HandSign handSign = HandSign.ROCK;
        Game savedGame = new Game();
        savedGame.setPlayer(handSign);

        given(gameService.saveResult(handSign)).willReturn(savedGame);

        this.mockMvc.perform(
                        post("/game/result")
                                .content(JsonUtil.toJson(handSign))
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());

        verify(gameService, VerificationModeFactory.times(1)).saveResult(handSign);
        reset(gameService);
    }

    @Test
    public void should_call_deleteAll() throws Exception {

        this.mockMvc.perform(
                        delete("/game")
                                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNoContent());

        verify(gameService, VerificationModeFactory.times(1)).deleteAll();
        reset(gameService);
    }

}
