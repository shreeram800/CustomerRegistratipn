package com.shreeram800.springtut;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TestContainerTest extends AbstractTestContainerunitTest {
    @Test
    void canStartDB() {
        assertThat(postgres.isCreated()).isTrue();
        assertThat(postgres.isRunning()).isTrue();

    }
}

