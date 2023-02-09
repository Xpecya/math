package xyz.xpecya.math.test;

public final class TestConfig {

    /**
     * 许多测试代码依赖随机生成的参数，这部分测试循环测试10次
     */
    public static final int RANDOM_REPEATED = 10;

    /**
     * 部分测试代码由于存在double计算误差的问题，需要判断模糊相等，这部分代码循环测试10000次
     */
    public static final int DEVIATION_REPEATED = 10000;

    /**
     * 由于double值运算存在误差 因此测试时需要指定误差范围
     * 实测目前在10的-15次方下可以通过所有测试
     */
    public static final double DEVIATION = Math.pow(10d, -15);
}
