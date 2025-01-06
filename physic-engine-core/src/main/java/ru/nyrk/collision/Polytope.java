package ru.nyrk.collision;

import ru.nyrk.maths.Vector3;

import java.util.ArrayList;
import java.util.List;

public class Polytope {
    private List<Vector3> points;
    private List<VirtualFace> faces = new ArrayList<>();
    public Polytope(Simplex simplex) {
        if (simplex.size() != 4){
            return;
        }
    }

}
