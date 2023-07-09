package com.robsil.rovies;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TMDBServiceTest {

    @Test
    void testProcessMovies() {
        Map<String, Object> input = new HashMap<>();
        input.put("genre_ids", new ArrayList<>(List.of(28L, 12L)));
        input.put("title", "Shawshank Redemption");
        input.put("overview", "about white man escaping jail");

        var flux = Flux.just(input);

        StepVerifier
                .create()
    }

}
