package xyz.xpecya.math.test;

import xyz.xpecya.math.Determinant;
import xyz.xpecya.math.SimpleDeterminant;

import java.util.Random;

public class SimpleLengthInitValueDeterminantTest extends SimpleDeterminantTest {

    @Override
    public void createTest() {

    }

    @Override
    public void calculateDoubleTest() {

    }

    @Override
    public void calculateComplexTest() {

    }

    @Override
    public void toMatrixTest() {

    }

    @Override
    public void solveTest() {

    }

    @Override
    public SimpleDeterminant getSimpleInstance() {
        Random random = new Random();
        int randomLength = random.nextInt(2, 10);
        double randomInitValue = random.nextDouble();
        return (SimpleDeterminant) Determinant.create(randomLength, randomInitValue);
    }
}
