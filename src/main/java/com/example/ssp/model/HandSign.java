package com.example.ssp.model;

import java.util.Random;

public enum HandSign {

    ROCK, PAPER, SCISSOR;

    private static final Random random = new Random();

    public static HandSign randomGameType()  {
        HandSign[] handSign = values();
        return handSign[random.nextInt(handSign.length)];
    }
}
