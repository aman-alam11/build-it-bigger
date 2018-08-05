package com.example.libraryjokesjava;
// http://www.laughfactory.com/jokes/latest-jokes

import java.util.ArrayList;
import java.util.List;

public class JokeProviderClass {
    private List<String> jokesList = new ArrayList<>();

    public JokeProviderClass() {
        jokesList.add("Yo momma so dumb, she tried to surf the microwave");
        jokesList.add("Me: Would you like to be the sun in my life?\n" +
                "Her: Awww... Yes!!!\n" +
                "Me: Good then stay 92.96 million miles away from me");
        jokesList.add("Teacher: How much is a gram?\n" +
                "Tyronne: Uhmm, depends on what you need");
        jokesList.add("Why are frogs always so happy? They eat what ever bugs them");
        jokesList.add("Yo mama is so ugly she made my happy meal cry");
        jokesList.add("I couldn't figure out why the baseball kept getting larger. Then it hit me.");
        jokesList.add("Yo mama so fat, she doesn't need internet, she's already worldwide.");
    }

    public String deliverJoke() {
        return jokesList.get((int) (Math.random() * (6) + 1));
    }
}
