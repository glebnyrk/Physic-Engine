package ru.nyrk.opengl;

import ru.nyrk.hitboxes.BoxHitBox;
import ru.nyrk.hitboxes.HitBox;
import ru.nyrk.maths.Matrix4f;
import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;
import ru.nyrk.opengl.geometry.AxesHelper;
import ru.nyrk.opengl.geometry.BoxGeometry;
import ru.nyrk.opengl.geometry.Geometry;
import ru.nyrk.opengl.material.Material;
import ru.nyrk.opengl.material.SurfaceMaterial;
import ru.nyrk.opengl.math.Matrix;
import ru.nyrk.opengl.math.Vector;
import ru.nyrk.orientation_providers.MutableOrientation;
import ru.nyrk.orientation_providers.OrientationReturn;
import ru.nyrk.physics.OverWorld;
import ru.nyrk.physics.PhysicsBody;
import ru.nyrk.physics.PhysicsBodyBuilder;
import ru.nyrk.physics.PhysicsScene;

import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_E;

public class Test41 extends Base {
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public List<Mesh> meshes = new ArrayList<>();
    public PhysicsScene physicsScene = new OverWorld();
    MutableOrientation boxOrientation = new MutableOrientation();
    public BoxHitBox boxHitBox = new BoxHitBox(boxOrientation);
    public static void main(String[] args) {
        new Test41().run();
    }
    @Override
    public void initialize() {
        renderer = new Renderer();
        scene = new Scene();

        camera = new Camera();
        camera.setPosition(new Vector(0.5, 1, 4));
        PhysicsBody cube = cube(Vector3.ZERO);
        cube.setStatic(true);
        addPhysicsBody(cube);
        addPhysicsBody(cube(new Vector3(0,4f,0)));
        scene.add(new AxesHelper());
        physicsScene.resume();
    }
    public void addPhysicsBody(PhysicsBody physicsBody) {
        SurfaceMaterial material = new SurfaceMaterial();
        material.uniforms.get("useVertexColors").data = 1;
        material.renderSettings.get("lineWidth").data = 3;
        meshes.add(new Mesh(new BoxGeometry(1,1,1), material));
        physicsScene.addObject(physicsBody);
        scene.add(meshes.get(meshes.size() - 1));
    }
    public PhysicsBody cube(Vector3 center){
        List<HitBox> hitBoxes = new ArrayList<>();
        PhysicsBody physicsBody = new PhysicsBodyBuilder().setHitBoxes(hitBoxes).setPos(center).createPhysicsBody();
        HitBox h1 = new BoxHitBox(physicsBody);
        hitBoxes.add(h1);
        return physicsBody;
    }
    @Override
    public void update() {
        List<PhysicsBody> bodies = physicsScene.getObjects();
        int size = bodies.size();
        for (int i = 0; i < size; i++) {
            setOrientation(meshes.get(i), bodies.get(i));
            if (input.isKeyPressed(GLFW_KEY_SPACE)) {
                bodies.get(i).addImpulse(new Vector3(0,1,0));
            }
        }
        renderer.render(scene, camera);
    }
    public void setOrientation(Mesh mesh, OrientationReturn orientationReturn){
        Matrix4f matrix4f = orientationReturn.getRotation().quaternionToMatrix();
        mesh.transform = Matrix.of(matrix4f);
        Vector3 pos = orientationReturn.getCenter();
        mesh.translate(pos.getX(), pos.getY(), pos.getZ(),false);
    }
}
