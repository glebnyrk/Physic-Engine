package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        PhysicsScene scene = new OverWorld();
        Hitbox[] hitBoxes = new Hitbox[]{new BoxHitbox(Vector3.ZERO)};

        PhysicsBody o1 = new PhysicsBodyBuilder().setHitBoxes(hitBoxes).setVelocity(new Vector3(0,10,0)).createPhysicsBody();

        hitBoxes[0] = new BoxHitbox(o1);
        scene.addObject(o1);


        Hitbox[] hitBoxes2 = new Hitbox[]{new BoxHitbox(Vector3.ZERO)};
        PhysicsBody o2 = new PhysicsBodyBuilder().setHitBoxes(hitBoxes2).setPos(new Vector3(0, -50, 0)).setSize(new Vector3(10, 10, 10)).setStatic().createPhysicsBody();
        hitBoxes2[0] = new BoxHitbox(o2);
        long start = System.currentTimeMillis();
        scene.addObject(o2);


        scene.resume();
        float highest = -Float.MAX_VALUE;
        while (true) {
//            if (highest < o1.getCenter().getY()){
//                highest = o1.getCenter().getY();
//                System.out.println(highest);
//            }
            System.out.println("Position: " + o1.getCenter().getY() + "; Velocity: " + o1.getVelocity().getY() + " Time: " + ((System.currentTimeMillis() - start) / 1000f));
            Thread.sleep(10);
        }
    }

}