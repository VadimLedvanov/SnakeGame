package com.javarush.games.snake;

import com.javarush.engine.cell.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

public class Snake {
    private List<GameObject> snakeParts = new ArrayList<>();
    private static final String HEAD_SIGN = "\uD83D\uDC0D";
    private static final String BODY_SIGN = "⚫";
    public boolean isAlive = true;
    private Direction direction = Direction.LEFT;
    public Snake(int x, int y) {
        GameObject p1 = new GameObject(x, y);
        GameObject p2 = new GameObject(x + 1, y);
        GameObject p3 = new GameObject(x + 2, y);
        snakeParts.add(p1);
        snakeParts.add(p2);
        snakeParts.add(p3);
    }

    public void setDirection(Direction direction) {
        if (direction == Direction.LEFT && snakeParts.get(0).y == snakeParts.get(1).y)
            return;
        if (direction == Direction.RIGHT && snakeParts.get(0).y == snakeParts.get(1).y)
            return;
        if (direction == Direction.UP && snakeParts.get(0).x == snakeParts.get(1).x)
            return;
        if (direction == Direction.DOWN && snakeParts.get(0).x == snakeParts.get(1).x)
            return;
        if (Math.abs(direction.ordinal() - this.direction.ordinal()) != 2)
            this.direction = direction;
    }

    public GameObject createNewHead() {
        GameObject head = snakeParts.get(0);
        if (direction == Direction.LEFT)
            return new GameObject(head.x - 1, head.y);
        else if (direction == Direction.DOWN)
            return new GameObject(head.x, head.y+1);
        else if (direction == Direction.UP)
            return new GameObject(head.x, head.y-1);
        else
            return new GameObject(head.x + 1, head.y);
    }

    public void removeTail() {
        snakeParts.remove(snakeParts.size() - 1);
    }

    public void move(Apple apple) {
        GameObject res = createNewHead();

        if (res.x >= SnakeGame.WIDTH || res.x < 0 ||
            res.y >= SnakeGame.HEIGHT || res.y < 0)
                isAlive = false;
        else if (apple.x == res.x && apple.y == res.y)
        {
            apple.isAlive = false;
            int xCord = snakeParts.get(snakeParts.size() - 1).x;
            int yCord = snakeParts.get(snakeParts.size() - 1).y;
            if (checkCollision(res)) {
                isAlive = false;
            } else {
                snakeParts.add(new GameObject(xCord, yCord));
                snakeParts.add(0, res);
            }
        }
        else
        {
            if (checkCollision(res)) {
                isAlive = false;
            } else {
                snakeParts.add(0, res);
                removeTail();
            }
        }

    }
    public void draw(Game game) {
        game.setCellValueEx(snakeParts.get(0).x, snakeParts.get(0).y, Color.NONE,HEAD_SIGN, (isAlive ? Color.BLACK : Color.RED), 75);
        for (int i = 1; i < snakeParts.size(); i++) {
            game.setCellValueEx(snakeParts.get(i).x, snakeParts.get(i).y, Color.NONE, BODY_SIGN, (isAlive ? Color.BLACK : Color.RED), 75);
        }
    }

    public boolean checkCollision(GameObject game) {
        for (GameObject part : snakeParts) {
            if (part.x == game.x && part.y == game.y)
                return true;
        }
        return false;
    }

    public int getLength() {
        return snakeParts.size();
    }
}
