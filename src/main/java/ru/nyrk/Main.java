package ru.nyrk;

public class Main {
    public static void main(String[] args) throws InterruptedException {
//        ImpulseMap impulseMap = new ImpulseMapBuilder().setImpulse(new Vector3(1,5,-2)).createImpulseCluster();
//        ImpulseMap impulseMap2 = new ImpulseMapBuilder().setImpulse(new Vector3(3,1,0)).createImpulseCluster();
//        impulseMap.add(new Vector3(3,1,0));
//        System.out.println(impulseMap.getImpulse());
        PhysicsScene scene = new OverWorld();
        Hitbox[] hitBoxes = new Hitbox[]{new BoxHitbox(Vector3.ZERO)};

        PhysicsBody o1 = new PhysicsBodyBuilder().setHitBoxes(hitBoxes).setPos(new Vector3(0, 1000f, 0)).setRot(new Quaternion(0.885068f,0.264969f,0.366607f,0.109754f)).createPhysicsBody();

        hitBoxes[0] = new BoxHitbox(o1);
        scene.addObject(o1);


        Hitbox[] hitBoxes2 = new Hitbox[]{new BoxHitbox(Vector3.ZERO)};
        PhysicsBody o2 = new PhysicsBodyBuilder().setHitBoxes(hitBoxes2).setPos(new Vector3(0, 0, 0)).setSize(new Vector3(10, 10, 10)).setStatic().createPhysicsBody();
        hitBoxes2[0] = new BoxHitbox(o2);
        scene.addObject(o2);
        long start = System.currentTimeMillis();
        System.out.println(o1.collidesWith(o2));

        scene.resume();
        while (true) {
            System.out.println("Position: " + o1.getCenter() + "; Velocity: " + o1.getImpulse().mul(1f / o1.getMass()) + " Time: " + ((System.currentTimeMillis() - start) / 1000f));
            Thread.sleep(10);
        }
    }
}
