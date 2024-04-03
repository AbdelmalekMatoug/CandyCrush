package be.kuleuven.candycrush.model;

public sealed interface Candy permits NormalCandy,Mars,Bounty,Twix,Snickers{

}

record NormalCandy(int color) implements Candy {
    public NormalCandy {
        if (color < 0 || color > 3) {
            throw new IllegalArgumentException("Color value should be between 0 and 3");
        }
    }
}



record Mars() implements Candy {

}
record Snickers() implements Candy {}
record Bounty() implements Candy {}
record Twix() implements Candy {}