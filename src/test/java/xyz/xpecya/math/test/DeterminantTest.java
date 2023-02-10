package xyz.xpecya.math.test;

import xyz.xpecya.math.ComplexNumber;
import xyz.xpecya.math.Determinant;

import java.util.Random;

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
     * 测试方法为，将计算结果带入方程，看结果是否正确
     */
    public abstract void solve() throws NoSuchFieldException, IllegalAccessException;

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

    protected double[] randomDoubleArray(int length) {
        double[] result = new double[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            result[i] = random.nextDouble();
        }
        return result;
    }

    protected ComplexNumber[] randomComplexArray(int length) {
        ComplexNumber[] result = new ComplexNumber[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            double real = random.nextDouble();
            double imaginary = random.nextDouble();
            result[i] = new ComplexNumber(real, imaginary);
        }
        return result;
    }
}
