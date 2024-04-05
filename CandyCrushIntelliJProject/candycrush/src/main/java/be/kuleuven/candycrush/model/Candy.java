package be.kuleuven.candycrush.model;

import javafx.scene.paint.Color;

public sealed interface Candy permits Candy.Bounty, Candy.Mars, Candy.NormalCandy, Candy.Snickers, Candy.Twix {
    record NormalCandy(int color) implements Candy {
        public NormalCandy {
            if (color < 0 || color > 3) {
                throw new IllegalArgumentException("Color value should be between 0 and 3");
            }
        }

        public Color getColor() {
            return switch (color) {
                case 0 -> Color.RED;
                case 1 -> Color.BLUE;
                case 2 -> Color.GREEN;
                case 3 -> Color.YELLOW;
                default -> throw new IllegalStateException("Unexpected value: " + color);
            };
        }
    }

    record Mars() implements Candy { }
    record Snickers() implements Candy {}
    record Bounty() implements Candy {}
    record Twix() implements Candy {}
}