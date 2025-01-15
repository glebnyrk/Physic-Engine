package ru.nyrk.collision;

import ru.nyrk.maths.Vector3;

import java.util.*;

public class Polytope {
    private List<Vector3> points = new ArrayList<>();
    private List<int[]> faces = new ArrayList<>();

    public Polytope(Simplex simplex) {
        // собираем политоп из 4-симплекса (добавляем точки и распределяем грани)
        if (simplex.size() != 4) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            points.add(simplex.get(i));
        }
        faces.add(new int[]{0, 1, 2});
        faces.add(new int[]{1, 2, 3});
        faces.add(new int[]{2, 3, 0});
        faces.add(new int[]{3, 0, 1});
    }

    public boolean add(Vector3 point) {
        for (Vector3 v : points) {
            //проверяем, есть ли точка в политопе с большой погрешностью
            if (v.equals(point, 0.01f)) {
                return true;
            }
        }
        //добавляем точку
        points.add(point);
        int pointIndex = points.size() - 1;
        //находим грани для удаления и добавляем их в словарь
        Map<IntPair, Integer> edgesStat = new HashMap<>();
        for (int i = 0; i < faces.size(); i++) {
            if (faceSees(i, point)) {
                int[] face = faces.get(i);
                {
                    IntPair key = new IntPair(face[0], face[1]);
                    edgesStat.put(key,edgesStat.getOrDefault(key, 0) + 1);
                }
                {
                    IntPair key = new IntPair(face[1], face[2]);
                    edgesStat.put(key,edgesStat.getOrDefault(key, 0) + 1);
                }
                {
                    IntPair key = new IntPair(face[2], face[0]);
                    edgesStat.put(key,edgesStat.getOrDefault(key, 0) + 1);
                }
                faces.remove(i);
            }
        }
        List<IntPair> edges = new ArrayList<>();
        Set<Map.Entry<IntPair,Integer>> edgesStatsSet = edgesStat.entrySet();
        for (Map.Entry<IntPair, Integer> edge : edgesStatsSet) {
            if (edge.getValue() == 1) {
                edges.add(edge.getKey());
            }
        }
        for (IntPair key : edges) {
            faces.add(new int[]{pointIndex,key.first,key.second});
        }
        return false;
    }

    public Vector3 facePosition(int faceID) {
        int[] face = faces.get(faceID);
        Vector3 a = points.get(face[0]);
        Vector3 b = points.get(face[1]);
        Vector3 c = points.get(face[2]);
        Vector3 max = a.max(b).max(c);
        Vector3 min = a.min(b).min(c);
        return max.add(min).mul(0.5f);
    }

    public Vector3 faceNormal(int id) {
        int[] face = faces.get(id);
        Vector3 a = points.get(face[0]);
        Vector3 b = points.get(face[1]);
        Vector3 c = points.get(face[2]);
        Vector3 ab = b.sub(a);
        Vector3 ac = c.sub(a);
        Vector3 normal = ab.mul(ac).normalize();
        if (normal.scalar(a) > 0) {
            return normal.mul(-1);
        } else {
            return normal;
        }
    }

    public int[] getFace(int id) {
        return faces.get(id).clone();
    }

    public Vector3[] getFacePoints(int id) {
        int[] face = faces.get(id);
        return new Vector3[]{points.get(face[0]), points.get(face[1]), points.get(face[2])};
    }

    public Vector3 getPoint(int id) {
        return points.get(id);
    }

    public int size() {
        return points.size();
    }

    public int nearestFace(Vector3 point) {
        int nearest = -1;
        float distance = Float.POSITIVE_INFINITY;
        for (int i = 0; i < faces.size(); i++) {
            Vector3 normal = faceNormal(i);
            Vector3 a = points.get(faces.get(i)[0]);
            float newDistance = Math.abs(normal.scalar(a) - point.scalar(normal));
            if (newDistance < distance) {
                distance = newDistance;
                nearest = i;
            }
        }
        return nearest;
    }

    public int nearestPoint(Vector3 point) {
        int nearest = -1;
        float distance = Float.POSITIVE_INFINITY;
        for (int i = 0; i < points.size(); i++) {
            float newDistance = points.get(i).distance(point);
            if (newDistance < distance) {
                distance = newDistance;
                nearest = i;
            }
        }
        return nearest;
    }

    @Override
    public String toString() {
        return "Polytope{" +
                "points=" + points +
                '}';
    }

    public Vector3 projectionPoint(Vector3 point, int face) {
        Vector3 normal = faceNormal(face);
        Vector3 a = points.get(faces.get(face)[0]);
        Vector3 ap = point.sub(a);
        float distance = ap.scalar(normal);
        return point.sub(normal.mul(distance));
    }

    public boolean faceSees(int face, Vector3 point) {
        Vector3 t = projectionPoint(point, face);
        Vector3 normal = faceNormal(face);
        return point.sub(t).scalar(normal) > 0;
    }
}
