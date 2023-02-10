package xyz.xpecya.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import xyz.xpecya.math.Determinant;
import xyz.xpecya.math.SimpleDeterminant;

import java.lang.reflect.Field;

import static xyz.xpecya.math.test.TestConfig.DELTA;
import static xyz.xpecya.math.test.TestConfig.RANDOM_REPEATED;

/**
 * 基于double数组的行列式测试
 */
public abstract class SimpleDeterminantTest extends DeterminantTest {

    @Override
    public void solve() throws NoSuchFieldException, IllegalAccessException {
        solveDouble();
        solveComplex();
    }

    @Override
    @RepeatedTest(RANDOM_REPEATED)
    public void triangle() throws NoSuchFieldException, IllegalAccessException {
        SimpleDeterminant simpleDeterminant = getSimpleInstance();
        SimpleDeterminant triangleDeterminant = (SimpleDeterminant) simpleDeterminant.triangle();
        Field field = SimpleDeterminant.class.getDeclaredField("numberArrays");
        field.setAccessible(true);
        double[][] numberArrays = (double[][]) field.get(triangleDeterminant);
        // 检查三角形
        int length = numberArrays.length;
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                Assertions.assertEquals(numberArrays[i][j], 0d);
            }
        }
        // 检查运算值
        Assertions.assertEquals(triangleDeterminant.calculateDouble(), simpleDeterminant.calculateDouble());
    }

    @Override
    @RepeatedTest(RANDOM_REPEATED)
    public void diagonal() throws NoSuchFieldException, IllegalAccessException {
        SimpleDeterminant simpleDeterminant = getSimpleInstance();
        SimpleDeterminant diagonalDeterminant = (SimpleDeterminant) simpleDeterminant.diagonal();
        Field field = SimpleDeterminant.class.getDeclaredField("numberArrays");
        field.setAccessible(true);
        double[][] numberArrays = (double[][]) field.get(diagonalDeterminant);
        // 检查对角线
        int length = numberArrays.length;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i == j) {
                    continue;
                }
                Assertions.assertEquals(numberArrays[i][j], 0d);
            }
        }
        // 检查运算值
        Assertions.assertEquals(diagonalDeterminant.calculateDouble(), simpleDeterminant.calculateDouble());
    }

    @Override
    @RepeatedTest(RANDOM_REPEATED)
    public void getLength() throws NoSuchFieldException, IllegalAccessException {
        SimpleDeterminant simpleDeterminant = getSimpleInstance();
        int length = simpleDeterminant.getLength();
        Field field = SimpleDeterminant.class.getDeclaredField("numberArrays");
        field.setAccessible(true);
        double[][] numberArrays = (double[][]) field.get(simpleDeterminant);
        Assertions.assertEquals(length, numberArrays.length);
    }

    @Override
    public Determinant getInstance() {
        return getSimpleInstance();
    }

    public abstract SimpleDeterminant getSimpleInstance();

    private void solveDouble() throws NoSuchFieldException, IllegalAccessException {
        SimpleDeterminant simpleDeterminant = getSimpleInstance();
        int length = simpleDeterminant.getLength();
        double[] testArray = randomDoubleArray(length);
        double[] solveResult = simpleDeterminant.solve(testArray);

        Field field = SimpleDeterminant.class.getDeclaredField("numberArrays");
        field.setAccessible(true);
        double[][] numberArrays = (double[][]) field.get(simpleDeterminant);
        for (int i = 0; i < length; i++) {
            double[] numberArray = numberArrays[i];
            double result = 0;
            for (int j = 0; j < length; j++) {
                result = result + solveResult[j] * numberArray[j];
            }
            Assertions.assertEquals(result, testArray[i], DELTA);
        }
    }

    private void solveComplex() {

    }
}
