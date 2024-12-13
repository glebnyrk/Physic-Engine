package org.example;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        ImpulseMap impulseMap = new ImpulseMapBuilder().setImpulse(new Vector3(1,5,-2)).createImpulseCluster();
//        ImpulseMap impulseMap2 = new ImpulseMapBuilder().setImpulse(new Vector3(3,1,0)).createImpulseCluster();
//        impulseMap.add(new Vector3(3,1,0));
//        System.out.println(impulseMap.getImpulse());
        PhysicsScene scene = new OverWorld();
        Hitbox[] hitBoxes = new Hitbox[]{new BoxHitbox(Vector3.ZERO)};

        PhysicsBody o1 = new PhysicsBodyBuilder().setHitBoxes(hitBoxes).setPos(new Vector3(0,1000,0)).setImpulse(new Vector3(0,0,0)).createPhysicsBody();

        hitBoxes[0] = new BoxHitbox(o1);
        scene.addObject(o1);


//        Hitbox[] hitBoxes2 = new Hitbox[]{new BoxHitbox(Vector3.ZERO)};
//        PhysicsBody o2 = new PhysicsBodyBuilder().setHitBoxes(hitBoxes2).setPos(new Vector3(0, -10, 0)).setSize(new Vector3(10, 10, 10)).setStatic().createPhysicsBody();
//        hitBoxes2[0] = new BoxHitbox(o2);
//        scene.addObject(o2);
        long start = System.currentTimeMillis();


        scene.resume();
//        float highest = -Float.MAX_VALUE;
        while (true) {
//            if (highest < o1.getCenter().getY()){
//                highest = o1.getCenter().getY();
//                System.out.println(highest);
//            }
            System.out.println("Position: " + o1.getCenter().getY() + "; Velocity: " + o1.getImpulse().mul(1f/o1.getMass()) + " Time: " + ((System.currentTimeMillis() - start) / 1000f));
            Thread.sleep(10);
    }
    }
}
