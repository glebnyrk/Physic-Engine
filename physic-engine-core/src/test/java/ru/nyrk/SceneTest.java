package ru.nyrk;
public class SceneTest {
    private long window;
    private float cameraX = 0f;
    private float cameraY = 0f;
    private float cameraZ = 0f; // Камера начнёт на расстоянии 5 единиц от центра
    private float pitch = (float) Math.toRadians(0);   // Угол наклона вверх/вниз
    private float yaw = (float) Math.toRadians(5);

    public static void main(String[] args) {
//        int n = 100;
//        curandGenerator generator = new curandGenerator();
//        float hostData[] = new float[n];
//        Pointer deviceData = new Pointer();
//        cudaMalloc(deviceData, n * Sizeof.FLOAT);
//        curandCreateGenerator(generator, CURAND_RNG_PSEUDO_DEFAULT);
////        curandSetPseudoRandomGeneratorSeed(generator, 1234);
////        curandGenerateUniform(generator, deviceData, n);
////        cudaMemcpy(Pointer.to(hostData), deviceData,
////                n * Sizeof.FLOAT, cudaMemcpyDeviceToHost);
////        System.out.println(Arrays.toString(hostData));
////        curandDestroyGenerator(generator);
////        cudaFree(deviceData);
    }
}
