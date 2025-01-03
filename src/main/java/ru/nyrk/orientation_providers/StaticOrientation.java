package ru.nyrk.orientation_providers;

import org.jetbrains.annotations.NotNull;
import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;

public class StaticOrientation extends OrientationReturn {
    final private Vector3 position;
    final private Vector3 size;
    final private Quaternion orientation;

    public StaticOrientation(@NotNull Vector3 position, @NotNull Vector3 size, @NotNull Quaternion orientation) {
        this.position = position;
        this.size = size;
        this.orientation = orientation;
    }
    public StaticOrientation(@NotNull Vector3 position, @NotNull Vector3 size) {
        this(position,size,Quaternion.ZERO);
    }
    public StaticOrientation(@NotNull Vector3 position) {
        this(position,Vector3.ONE);
    }
    public StaticOrientation(){
        this(Vector3.ZERO);
    }
    @Override
    public Vector3 getCenter() {
        return position;
    }

    @Override
    public Vector3 getSize() {
        return size;
    }

    @Override
    public Quaternion getRotation() {
        return orientation;
    }
}
