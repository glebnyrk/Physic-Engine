package ru.nyrk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nyrk.maths.Quaternion;

class QuaternionTest {
    @Test
    void sumTest(){
        {
            Quaternion q1 = new Quaternion(0,1,2,3);
            Quaternion q2 = new Quaternion(0,1,2,3);
            Quaternion q3 = new Quaternion(0,2,4,6);
            Assertions.assertEquals(q1.add(q2),q3);
        }
        {
            Quaternion q1 = new Quaternion(6,15,-2,9);
            Quaternion q2 = new Quaternion(7,3,6,8);
            Quaternion q3 = new Quaternion(13,18,4,17);
            Assertions.assertEquals(q1.add(q2),q3);
        }
    }
    @Test
    void subtractTest(){
        {
            Quaternion q1 = new Quaternion(0,1,2,3);
            Quaternion q2 = new Quaternion(0,1,2,3);
            Quaternion q3 = new Quaternion(0,2,4,6);
            Assertions.assertEquals(q3.sub(q2),q1);
            Assertions.assertEquals(q3.sub(q1),q2);
        }
        {
            Quaternion q1 = new Quaternion(6,15,-2,9);
            Quaternion q2 = new Quaternion(7,3,6,8);
            Quaternion q3 = new Quaternion(13,18,4,17);
            Assertions.assertEquals(q3.sub(q2),q1);
            Assertions.assertEquals(q3.sub(q1),q2);
        }
    }
    @Test
    void multiplyTest(){
        {
            Quaternion q1 = new Quaternion(8,7,6,2);
            Quaternion q2 = new Quaternion(7,2,9,4);
            Quaternion q3 = new Quaternion(-20,71,90,97);
            Assertions.assertEquals(q1.mul(q2),q3);
        }
        {
            Quaternion q1 = new Quaternion(3,6,2,0);
            Quaternion q2 = new Quaternion(4,7,3,1);
            Quaternion q3 = new Quaternion(-36,47,11,7);
            Assertions.assertEquals(q1.mul(q2),q3);
        }
    }

}