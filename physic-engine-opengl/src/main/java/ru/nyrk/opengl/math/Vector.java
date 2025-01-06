package ru.nyrk.opengl.math;

import ru.nyrk.maths.Vector3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

public class Vector {
    public final double[] values;

    // initialize zero vector with given size
    public Vector(int size) {
        values = new double[size];
    }

    public Vector(double... v) {
        values = Arrays.copyOf(v, v.length);
    }

    public static double dot(Vector v, Vector w) {
        double c = 0;
        for (int i = 0; i < v.values.length; i++)
            c += v.values[i] * w.values[i];
        return c;
    }

    public static Vector of(Vector3 vector3) {
        return new Vector(vector3.getX(), vector3.getY(), vector3.getZ());
    }

    public String toString() {
        StringJoiner stringJoiner = new StringJoiner("", "[", "]");

        for (int i = 0; i < values.length; i++) {
            stringJoiner.add(String.format("%6.2f", values[i]));
        }
        return stringJoiner.toString();
    }

    public static float[] flattenList(List<Vector> vecList) {
        int listSize = vecList.size();
        int vecSize = vecList.get(0).values.length;
        float[] flattened = new float[listSize * vecSize];
        for (int vecNumber = 0; vecNumber < listSize; vecNumber++) {
            Vector v = vecList.get(vecNumber);
            for (int i = 0; i < vecSize; i++)
                flattened[vecNumber * vecSize + i] = (float) v.values[i];
        }
        return flattened;
    }

    public static List<Vector> unflattenList(float[] flatArray,
                                             int vecSize) {
        List<Vector> vecList = new ArrayList<Vector>(vecSize);
        double[] tempData = new double[vecSize];
        for (int i = 0; i < flatArray.length; i += vecSize) {
            for (int j = 0; j < vecSize; j++)
                tempData[j] = flatArray[i + j];
            vecList.add(new Vector(tempData));
        }
        return vecList;
    }

    // resize values array (can be larger or smaller)
    public Vector resize(int newSize) {
        return new Vector(Arrays.copyOf(values, newSize));
    }
}
