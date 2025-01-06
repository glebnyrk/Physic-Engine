package ru.nyrk.BVH;

import org.jetbrains.annotations.NotNull;
import ru.nyrk.hitboxes.AABB;
import ru.nyrk.physics.PhysicsBody;
import ru.nyrk.maths.Vector3;

import java.util.List;

public interface BVHChild extends AABLike {
    float getAABBVolume();

    AABB getAABB();

    int getDeep();

    List<PhysicsBody> rawCollides(@NotNull PhysicsBody body);

    String toString(int indent);

    int getCount();

    Vector3 getVelocity();

    default Vector3 getMin() {
        return getAABB().getMin();
    }

    default Vector3 getMax() {
        return getAABB().getMax();
    }

    BVHTreePart getParent();

    void setParent(BVHTreePart parent);
    void yieldDelete();
    boolean isInDeleteOrder();
}
