package xyz.xpecya.math.test;

import xyz.xpecya.math.Determinant;
import xyz.xpecya.math.SimpleDeterminant;

import java.util.Random;

public class SimpleLengthInitValueDeterminantTest extends SimpleDeterminantTest {

    @Override
    public void create() {

    }

    @Override
    public void calculateDouble() {

    }

    @Override
    public void calculateComplex() {

    }

    @Override
    public void toMatrix() {

    }

    @Override
    public SimpleDeterminant getSimpleInstance() {
        Random random = new Random();
        int randomLength = random.nextInt(2, 10);
        double randomInitValue = random.nextDouble();
        return (SimpleDeterminant) Determinant.create(randomLength, randomInitValue);
    }
}
