package xyz.xpecya.math.test;

public final class TestConfig {

    /**
     * 每个测试循环执行10次
     */
    public static final int REPEATED = 10;

    /**
     * 由于double值运算存在误差 因此测试时需要指定误差范围
     * 实测目前在10的-15次方下可以通过所有测试
     */
    public static final double DEVIATION = Math.pow(10d, -15);
}
