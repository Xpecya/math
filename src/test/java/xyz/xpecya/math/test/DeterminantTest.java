package xyz.xpecya.math.test;

import xyz.xpecya.math.Determinant;

/**
 * 行列式测试
 */
public abstract class DeterminantTest {

    /**
     * create方法测试
     */
    public abstract void createTest();

    /**
     * calculateDouble方法测试
     */
    public abstract void calculateDoubleTest();

    /**
     * calculateComplex方法测试
     */
    public abstract void calculateComplexTest();

    /**
     * toMatrix方法测试
     */
    public abstract void toMatrixTest();

    /**
     * solve方法测试
     */
    public abstract void solveTest();

    /**
     * triangle方法测试
     */
    public abstract void triangleTest() throws NoSuchFieldException, IllegalAccessException;

    /**
     * diagonal方法测试
     * 首先，除了主对角线以外的值必须为0
     * 其次，diagonal方法生成的行列式的值必须和原行列式相同
     */
    public abstract void diagonalTest() throws NoSuchFieldException, IllegalAccessException;

    /**
     * getLength方法测试
     * 检查getLength方法的返回值和numberArrays.length是否相等
     */
    public abstract void getLengthTest() throws NoSuchFieldException, IllegalAccessException;

    public abstract Determinant getInstance();
}
