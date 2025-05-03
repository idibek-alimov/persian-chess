package tj.avlimov.game.logic;

import java.security.SecureRandom;
import java.util.List;

public class GameNameGenerator {
    private static final List<String> ADJECTIVES = List.of(
            "Swift", "Fierce", "Brave", "Silent", "Clever", "Mighty", "Bold", "Sneaky", "Wild", "Lucky");

    private static final List<String> ANIMALS = List.of(
            "Tiger", "Eagle", "Fox", "Panther", "Wolf", "Hawk", "Lion", "Bear", "Shark", "Falcon");

    private static final SecureRandom random = new SecureRandom();

    public static String generateGameName(){
        String adjective = ADJECTIVES.get(random.nextInt(ADJECTIVES.size()));
        String animal = ANIMALS.get(random.nextInt(ANIMALS.size()));

        int number = random.nextInt(90) + 10;

        return adjective + animal + number;
    }
}


