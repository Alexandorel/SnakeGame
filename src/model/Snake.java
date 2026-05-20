package model;

import java.awt.Point;
import java.util.ArrayList;

public class Snake {
    public ArrayList<Point> corp;
    public int size = 40;

    public Snake() {
        corp = new ArrayList<>();
    }

    public void adaugaSegment(int x, int y) {
        corp.add(new Point(x, y));
    }
}
