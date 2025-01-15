package ru.nyrk.collision;

import ru.nyrk.maths.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Simplex {
    private final List<Vector3> points = new ArrayList<>(4);
    private int size = 0;
    public Simplex() {}
    public Simplex(Vector3... points) {
        int max = Math.min(points.length, 4);
        for (int i = 0; i < max; i++) {
            push(points[i]);
        }
    }
    public boolean push(Vector3 point) {
        if (points.contains(point)) {
            return false;
        }
        if(size >= 4){
            points.remove(0);
        }
        size++;
        points.add(point);
        return true;
    }
    public int size() {
        return size;
    }
    public Vector3 get(int index){
        return points.get(index);
    }
    @Override
    public String toString() {
        if (size == 1) {
            return "dot: (" + points.get(0).toString() + ")";
        } else if (size == 2) {
            return "line: (" + points.get(0).toString() + ", \n" + points.get(1).toString() + ")";
        }else if (size == 3) {
            return "triangle: (" + points.get(0).toString() + ", \n" + points.get(1).toString() + ", \n" + points.get(2).toString() + ")";
        } else if (size == 4) {
            return "tetrahedron: (" + points.get(0).toString() + ", \n" + points.get(1).toString() + ", \n" + points.get(2).toString() + ", \n" + points.get(3).toString() + ")";
        }
        return "empty";
    }
    Vector3 last(){
        return points.get(points.size()-1);
    }
}
