package ru.nyrk.opengl;

import ru.nyrk.hitboxes.BoxHitBox;
import ru.nyrk.maths.Matrix4f;
import ru.nyrk.maths.Quaternion;
import ru.nyrk.maths.Vector3;
import ru.nyrk.opengl.geometry.BoxGeometry;
import ru.nyrk.opengl.geometry.Geometry;
import ru.nyrk.opengl.material.Material;
import ru.nyrk.opengl.material.SurfaceMaterial;
import ru.nyrk.opengl.math.Matrix;
import ru.nyrk.opengl.math.Vector;

public class Test41 extends Base {
    public Renderer renderer;
    public Scene scene;
    public Camera camera;
    public Mesh mesh;
    public BoxHitBox boxHitBox;
    private Quaternion quaternion;

    public static void main(String[] args) {
        new Test41().run();
    }

    @Override
    public void initialize() {
        renderer = new Renderer();
        scene = new Scene();

        camera = new Camera();
        camera.setPosition(new Vector(0, 0, 3));


        boxHitBox = new BoxHitBox(Vector3.ZERO);
        Geometry geometry = new BoxGeometry(boxHitBox);


        Material material = new SurfaceMaterial();
        material.uniforms.get("useVertexColors").data = 1;
        material.renderSettings.get("lineWidth").data = 3;

        mesh = new Mesh(geometry, material);
        quaternion = boxHitBox.getRotation();
        scene.add(mesh);
    }

    @Override
    public void update() {
        quaternion = quaternion.rotate(new Quaternion(1,new Vector3(1,1,1)));
        Matrix4f matrix4f = quaternion.quaternionToMatrix();
        mesh.transform = Matrix.of(matrix4f);
//        mesh.rotateY(0.0123f, true);
//        mesh.rotateX(0.0237f, true);

        renderer.render(scene, camera);
    }
}
