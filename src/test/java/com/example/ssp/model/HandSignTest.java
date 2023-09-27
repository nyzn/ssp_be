package com.example.ssp.model;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HandSignTest {

    @Test
    public void givenEnumType_whenUsingStaticMethod_valueIsRandomlyGenerated() {
        HandSign handSign = HandSign.randomGameType();
        assertThat(handSign).isNotNull();
        assertThat(handSign instanceof HandSign);
    }
}
