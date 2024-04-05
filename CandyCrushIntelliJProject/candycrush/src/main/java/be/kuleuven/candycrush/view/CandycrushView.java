package be.kuleuven.candycrush.view;

import be.kuleuven.candycrush.model.Candy;
import be.kuleuven.candycrush.model.CandycrushModel;
import be.kuleuven.candycrush.model.Position;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.Iterator;

public class CandycrushView extends Region {
    private CandycrushModel model;
    private int widthCandy;
    private int heigthCandy;

    public CandycrushView(CandycrushModel model) {
        this.model = model;
        widthCandy = 30;
        heigthCandy = 30;
        update();
        getStyleClass().add("custom-grid-view");

    }

    public void update() {
        getChildren().clear();
        int i = 0;
        int height = 0;
        Iterator<Candy> iterCandy = model.getSpeelbord().iterator();
        while (iterCandy.hasNext()) {
            Candy candy = iterCandy.next();
            Rectangle rectangle = new Rectangle(i * widthCandy, height * heigthCandy, widthCandy, heigthCandy);
            rectangle.setFill(Color.TRANSPARENT);
            rectangle.setStroke(Color.BLACK);
            Node candyShape = makeCandyShape(Position.fromIndex(i, model.getBoardSize()), candy); // Use the candy obtained from iterCandy.next()

            candyShape.setTranslateX(rectangle.getX() + (rectangle.getWidth() - candyShape.getBoundsInLocal().getWidth()) / 2);
            candyShape.setTranslateY(rectangle.getY() + (rectangle.getHeight() - candyShape.getBoundsInLocal().getHeight()) / 2);

            getChildren().addAll(rectangle, candyShape);
            rectangle.getStyleClass().add("grid-rectangle");
            if (i == model.getWidth() - 1) {
                i = 0;
                height++;
            } else {
                i++;
            }
        }
    }

    public int getIndexOfClicked(MouseEvent me) {
        int index = -1;
        Position clickedOnPosition = getClickedPosition(me);
        //System.out.println(me.getX()+" - "+me.getY()+" - "+row+" - "+column);
        if (clickedOnPosition.row() < model.getWidth() && clickedOnPosition.column() < model.getHeight()) {
            index = clickedOnPosition.toIndex();
        }
        return index;
    }

    public Position getClickedPosition(MouseEvent me) {
        int row = (int) me.getY() / heigthCandy;
        int column = (int) me.getX() / widthCandy;
        Position position = new Position(model.getBoardSize(), row, column);
        return position;
    }

    Node makeCandyShape(Position position, Candy candy) {
        switch (candy.getClass().getSimpleName()) {
            case "Mars":
                return createRectangle(Color.BLACK);
            case "Bounty":
                return createRectangle(Color.LIGHTBLUE);
            case "Twix":
                return createRectangle(Color.GOLD);
            case "Snickers":
                return createRectangle(Color.BROWN);
            case "NormalCandy":
                return createCircle(((Candy.NormalCandy) candy).getColor());
            default:
                return null; // Handle the default case if needed
        }
    }


private Circle createCircle(Color color){
        Circle circle = new Circle(10);
        circle.setCenterX(10);
        circle.setCenterY(10);
        circle.setFill(color);
        return  circle;
}
private Rectangle createRectangle(Color color){
        Rectangle rectangle = new Rectangle(15,15);
        rectangle.setFill(color);
        return  rectangle;
}
}