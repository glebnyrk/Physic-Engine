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
    public void push(Vector3 point) {
        if(size >= 4){
            points.remove(0);
        }
        size++;
        points.add(point);
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
    public Vector3 getNormal(int index){
        if(size != 4){
            return null;
        }
        Vector3 a = points.get(0);
        Vector3 b = points.get(1);
        Vector3 c = points.get(2);
        Vector3 d = points.get(3);
        return switch (index){
            case 0 -> VirtualFace.normal(a, b, c);
            case 1 -> VirtualFace.normal(b,c,d);
            case 2 -> VirtualFace.normal(c,d,a);
            case 3 -> VirtualFace.normal(d,a,b);
            default -> null;
        };
    }
}
