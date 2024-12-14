package ru.nyrk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrientationReturnTest {
    private float r(){
        return (float) Math.random();
    }
    @Test
    public void localGlobalCheck(){
        BoxHitbox hitbox = new BoxHitbox(new Vector3(r(),r(),r()),Vector3.ONE,Quaternion.ZERO);
        Vector3 p = new Vector3(r(),r(),r());
        Assertions.assertTrue(hitbox.translateToGlobal(hitbox.translateToLocal(p)).equals(p));
    }
}