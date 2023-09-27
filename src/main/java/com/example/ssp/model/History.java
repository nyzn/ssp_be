package com.example.ssp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private List<Game> games;
    private Long playerWins;
    private Long npcWins;
    private Long ties;
}
