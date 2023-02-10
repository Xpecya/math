package xyz.xpecya.math;

/**
 * 行列式
 * 不可变对象，所有运算都不会影响原行列式的元素
 */
public abstract class Determinant {

    /**
     * 创建一个由同一个浮点数构成的行列式
     *
     * @param length 行列式边长
     * @param initValue 行列式初始值
     * @return 行列式
     */
    public static Determinant create(int length, double initValue) {
        if (length < 2) {
            throw new IllegalArgumentException("length can't be less than 2! got " + length);
        }
        double[][] numberArrays = new double[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                numberArrays[i][j] = initValue;
            }
        }
        return new SimpleDeterminant(numberArrays);
    }

    /**
     * 创建一个由同一个复数构成的行列式
     *
     * @param length 行列式边长
     * @param initValue 行列式初始值
     * @return 行列式
     */
    public static Determinant create(int length, ComplexNumber initValue) {
        if (length < 2) {
            throw new IllegalArgumentException("length can't be less than 2! got " + length);
        }
        if (initValue == null) {
            throw new IllegalArgumentException("initValue can't be null!");
        }
        ComplexNumber[][] numberArrays = new ComplexNumber[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                numberArrays[i][j] = initValue.clone();
            }
        }
        return new ComplexDeterminant(numberArrays);
    }

    /**
     * 用浮点数组创建一个行列式
     * 对源数组的修改不会导致行列式的修改，反之亦然
     *
     * @param numberArrays 浮点数组
     * @return 浮点数行列式
     */
    public static Determinant create(double[][] numberArrays) {
        if (numberArrays == null) {
            throw new IllegalArgumentException("numberArrays can't be null!");
        }

        // 获取行列式长度
        int length = numberArrays.length;
        for (double[] doubles : numberArrays) {
            if (doubles != null && doubles.length > length) {
                length = doubles.length;
            }
        }
        if (length < 2) {
            throw new IllegalArgumentException("numberArray's length can't be less than 2! got " + length);
        }
        double[][] newArray = new double[length][length];

        for (int i = 0; i < length; i++) {
            double[] doubleArray = numberArrays[i];
            if (doubleArray == null) {
                continue;
            }
            System.arraycopy(numberArrays[i], 0, newArray[i], 0, doubleArray.length);
        }
        return new SimpleDeterminant(newArray);
    }

    /**
     * 用复数组创建一个行列式
     * 对源数组的修改不会导致行列式的修改，反之亦然
     *
     * @param numberArrays 复数组
     * @return 复数行列式
     */
    public static Determinant create(ComplexNumber[][] numberArrays) {
        if (numberArrays == null) {
            throw new IllegalArgumentException("numberArrays can't be null!");
        }

        // 获取行列式长度
        int length = numberArrays.length;
        for (ComplexNumber[] complexNumbers : numberArrays) {
            if (complexNumbers != null && complexNumbers.length > length) {
                length = complexNumbers.length;
            }
        }
        if (length < 2) {
            throw new IllegalArgumentException("numberArray's length can't be less than 2! got " + length);
        }
        ComplexNumber[][] newArray = new ComplexNumber[length][length];

        for (int i = 0; i < length; i++) {
            ComplexNumber[] complexNumberArray = numberArrays[i];
            if (complexNumberArray == null) {
                continue;
            }
            for (int j = 0; j < length; j++) {
                if (j < complexNumberArray.length) {
                    ComplexNumber complexNumber = complexNumberArray[j];
                    if (complexNumber == null) {
                        newArray[i][j] = new ComplexNumber();
                    } else {
                        newArray[i][j] = complexNumber.clone();
                    }
                } else {
                    newArray[i][j] = new ComplexNumber();
                }
            }
        }
        return new ComplexDeterminant(newArray);
    }

    /**
     * 计算行列式的值 以浮点数的形式返回
     * 如果行列式是复数行列式则返回计算结果的实部
     */
    public abstract double calculateDouble();

    /**
     * 计算行列式的值 以复数形式返回
     * 如果行列式是浮点数行列式则将浮点数转化为复数
     */
    public abstract ComplexNumber calculateComplex();

    /**
     * 返回由行列式中的元素构成的矩阵方阵
     */
    public abstract Matrix toMatrix();

    /**
     * 求解形如Ax=B的线性方程组
     * 其中A为当前行列式
     * B为长度等于行列式的一维数组构成的矩阵
     * 如果线性方程组无解 返回null
     * 如果有无数多个解，返回其中一组
     *
     * @return 线性方程组的解 以浮点数形式返回
     */
    public double[] solve(double[] value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null!");
        }
        int length = getLength();
        int valueLength = value.length;
        if (valueLength != length) {
            throw new IllegalArgumentException("value's length = " + valueLength
                    + ", determinant's length = " + length);
        }
        if (!solveDoubleResultExists(value)) {
            return null;
        }
        return doSolve(value);
    }

    protected abstract double[] doSolve(double[] value);

    /**
     * 求解形如Ax=B的线性方程组
     * 其中A为当前行列式
     * B为长度等于行列式的一维数组构成的矩阵
     * 如果线性方程组无解 返回null
     * 如果有无数多个解，返回其中一组
     *
     * @return 线性方程组的解 以复数形式返回
     */
    public ComplexNumber[] solve(ComplexNumber[] value) {
        if (value == null) {
            throw new IllegalArgumentException("value is null!");
        }
        int length = getLength();
        int valueLength = value.length;
        if (valueLength != length) {
            throw new IllegalArgumentException("value's length = " + valueLength
                    + ", determinant's length = " + length);
        }
        for (int i = 0; i < length; i++) {
            ComplexNumber complexNumber = value[i];
            if (complexNumber == null) {
                throw new IllegalArgumentException("value[" + i + "] is null!");
            }
        }
        if (!solveComplexResultExists(value)) {
            return null;
        }
        return doSolve(value);
    }

    protected abstract ComplexNumber[] doSolve(ComplexNumber[] value);

    /**
     * 将当前行列式转换成运算结果相同的三角行列式
     */
    public abstract Determinant triangle();

    /**
     * 将当前行列式转换成运算结果相同的对角型行列式
     */
    public abstract Determinant diagonal();

    /**
     * 获取行列式的边长
     */
    public abstract int getLength();

    // 判断线性方程组的解是否存在
    // 通过方程组矩阵和增广矩阵的轶进行比较

    private boolean solveDoubleResultExists(double[] doubles) {
        return false;
    }

    private boolean solveComplexResultExists(ComplexNumber[] complexNumbers) {
        return false;
    }
}
