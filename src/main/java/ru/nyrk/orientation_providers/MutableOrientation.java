package ru.nyrk.orientation_providers;

import org.jetbrains.annotations.NotNull;
import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;

public class MutableOrientation implements OrientationReturn{
    private Vector3 position;
    private Vector3 size;
    private Quaternion rotation;

    public MutableOrientation(@NotNull Vector3 position, @NotNull Vector3 size, @NotNull Quaternion rotation) {
        this.position = position;
        this.size = size;
        this.rotation = rotation;
    }
    public MutableOrientation(@NotNull Vector3 position, @NotNull Vector3 size) {
        this(position,size,Quaternion.ZERO);
    }
    public MutableOrientation(@NotNull Vector3 position) {
        this(position,Vector3.ONE);
    }
    public MutableOrientation(){
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
        return rotation;
    }
    public void setPosition(Vector3 position) {
        this.position = position;
    }
    public void setSize(Vector3 size) {
        this.size = size;
    }
    public void setRotation(Quaternion rotation) {
        this.rotation = rotation;
    }
}
