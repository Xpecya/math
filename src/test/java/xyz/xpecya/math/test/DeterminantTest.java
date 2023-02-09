package xyz.xpecya.math.test;

import xyz.xpecya.math.Determinant;

/**
 * 行列式测试
 */
public abstract class DeterminantTest {

    /**
     * create方法测试
     */
    public abstract void create();

    /**
     * calculateDouble方法测试
     */
    public abstract void calculateDouble();

    /**
     * calculateComplex方法测试
     */
    public abstract void calculateComplex();

    /**
     * toMatrix方法测试
     */
    public abstract void toMatrix();

    /**
     * solve方法测试
     */
    public abstract void solve();

    /**
     * triangle方法测试
     */
    public abstract void triangle() throws NoSuchFieldException, IllegalAccessException;

    /**
     * diagonal方法测试
     * 首先，除了主对角线以外的值必须为0
     * 其次，diagonal方法生成的行列式的值必须和原行列式相同
     */
    public abstract void diagonal() throws NoSuchFieldException, IllegalAccessException;

    /**
     * getLength方法测试
     * 检查getLength方法的返回值和numberArrays.length是否相等
     */
    public abstract void getLength() throws NoSuchFieldException, IllegalAccessException;

    public abstract Determinant getInstance();
}
