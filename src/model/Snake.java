package model;

import movement.Direction;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private final ArrayList<Point> body;

    public Snake(int startX, int startY) {
        body = new ArrayList<>();
        body.add(new Point(startX, startY));
    }

    public Point getHead() {
        return body.get(0);
    }

    public int size() {
        return body.size();
    }

    public List<Point> getBody() {
        return body;
    }

    public void move(Direction dir, int step) {
        Point previousHead = new Point(body.get(0));

        switch (dir) {
            case UP -> body.get(0).y -= step;
            case DOWN -> body.get(0).y += step;
            case LEFT -> body.get(0).x -= step;
            case RIGHT -> body.get(0).x += step;
        }

        for (int i = 1; i < body.size(); i++) {
            Point temp = new Point(body.get(i));
            body.set(i, previousHead);
            previousHead = temp;
        }
    }

    public void grow(Direction dir, int step) {
        Point last = body.get(body.size() - 1);
        int newX = last.x;
        int newY = last.y;

        switch (dir) {
            case UP -> newY += step;
            case DOWN -> newY -= step;
            case LEFT -> newX += step;
            case RIGHT -> newX -= step;
        }
        body.add(new Point(newX, newY));
    }

    public boolean collidesWithSelf() {
        Point head = body.get(0);
        for (int i = 1; i < body.size(); i++) {
            if (head.x == body.get(i).x && head.y == body.get(i).y) {
                return true;
            }
        }
        return false;
    }

    public boolean bodyContains(int x, int y) {
        for (int i = 1; i < body.size(); i++) {
            if (body.get(i).x == x && body.get(i).y == y) {
                return true;
            }
        }
        return false;
    }

    public void wrapAround(int maxPos) {
        for (Point segment : body) {
            if (segment.x < 0) segment.x = maxPos;
            if (segment.x > maxPos) segment.x = 0;
            if (segment.y < 0) segment.y = maxPos;
            if (segment.y > maxPos) segment.y = 0;
        }
    }
}
