package ru.nyrk;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.nyrk.maths.Vector3;

class Vector3Test {
    @Test
    void lengthTest(){
        Assertions.assertEquals(new Vector3(1,1,1).length(), Math.sqrt(3),0.0001f);
        Assertions.assertEquals(15, new Vector3(15,0,0).length(),0.0001f);
        Assertions.assertEquals(5, new Vector3(3,4,0).length(),0.0001f);
    }
    @Test
    void summTest(){
        Assertions.assertTrue(new Vector3(61,21,15).equals(new Vector3(32,5,10).add(new Vector3(29,16,5)),0.0001f));
        Assertions.assertTrue(new Vector3(90,1420,198).equals(new Vector3(72,742,123).add(new Vector3(18,678,75)),0.0001f));
    }
    @Test
    void mulTest(){
        Assertions.assertTrue(new Vector3(5,6,1).mul(new Vector3(2,7,2)).equals(new Vector3(5,-8,23),0.0001f));
        Assertions.assertTrue(new Vector3(4,3,1).mul(new Vector3(5,7,6)).equals(new Vector3(11,-19,13),0.0001f));
    }
    @Test
    void scalarTest(){
        Assertions.assertEquals(new Vector3(1,1,1).scalar(new Vector3(1,1,1)),3);
        Assertions.assertEquals(new Vector3(4,-4,3).scalar(new Vector3(2,6,7)),5);
    }
    @Test
    void moreThanTest(){
        Assertions.assertTrue(new Vector3(3,4,5).moreThan(new Vector3(2,3,4)));
        Assertions.assertFalse(new Vector3(3,4,5).moreThan(new Vector3(3,4,5)));
        Assertions.assertFalse(new Vector3(2,3,4).moreThan(new Vector3(3,4,5)));
        Assertions.assertFalse(new Vector3(2,4,5).moreThan(new Vector3(3,4,5)));
    }
    @Test
    void lessThanTest(){
        Assertions.assertFalse(new Vector3(3,4,5).lessThan(new Vector3(2,3,4)));
        Assertions.assertFalse(new Vector3(3,4,5).lessThan(new Vector3(3,4,5)));
        Assertions.assertTrue(new Vector3(2,3,4).lessThan(new Vector3(3,4,5)));
    }
}