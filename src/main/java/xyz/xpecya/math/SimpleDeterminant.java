package xyz.xpecya.math;

/**
 * 行列式 基于浮点数实现
 */
public class SimpleDeterminant extends SimpleMatrix implements Determinant {

    /**
     * 行列式的值
     */
    private Double value;

    SimpleDeterminant(double[][] numberArrays) {
        super(numberArrays);
    }

    @Override
    public double calculateDouble() {
        if (value != null) {
            // 说明已经计算过了
            return value;
        }
        if (numberArrays.length == 2) {
            // 2*2行列式 直接用定义
            value = numberArrays[0][0] * numberArrays[1][1] - numberArrays[0][1] * numberArrays[1][0];
        } else {
            SimpleDeterminant triangleDeterminant = simpleUpperTriangle();
            for (int i = 0; i < numberArrays.length; i++) {
                double data = triangleDeterminant.numberArrays[i][i];
                if (value == null) {
                    value = data;
                } else {
                    value = value * data;
                }
            }
        }
        return value;
    }

    @Override
    public ComplexNumber calculateComplex() {
        double doubleValue = calculateDouble();
        return new ComplexNumber(doubleValue);
    }

    @Override
    public double[] doSolve(double[] value) {
        double calc = calculateDouble();
        if (calc == 0d) {
            return null;
        }
        int length = numberArrays.length;
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            double[][] copy = deepCopy();
            for (int j = 0; j < length; j++) {
                copy[j][i] = value[j];
            }
            SimpleDeterminant simpleDeterminant = new SimpleDeterminant(copy);
            double determinantValue = simpleDeterminant.calculateDouble();
            result[i] = determinantValue / calc;
        }
        return result;
    }

    /**
     * 把浮点数行列式转化成复数行列式再进行计算
     */
    @Override
    public ComplexNumber[] doSolve(ComplexNumber[] value) {
        int length = numberArrays.length;
        ComplexNumber[][] complexArrays = new ComplexNumber[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                complexArrays[i][j] = new ComplexNumber(numberArrays[i][j]);
            }
        }
        ComplexDeterminant complexDeterminant = new ComplexDeterminant(complexArrays);
        return complexDeterminant.doSolve(value);
    }

    @Override
    public Determinant triangle() {
        return simpleUpperTriangle();
    }

    @Override
    public Determinant diagonal() {
        SimpleDeterminant simpleDeterminant = simpleUpperTriangle();
        int length = simpleDeterminant.numberArrays.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                simpleDeterminant.numberArrays[i][j] = 0d;
            }
        }
        return simpleDeterminant;
    }

    @Override
    public int getLength() {
        return numberArrays.length;
    }

    private SimpleDeterminant simpleUpperTriangle() {
        // 深拷贝一份
        double[][] copy = deepCopy();
        int length = copy.length;
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                double item = copy[i][j];
                if (item == 0d) {
                    continue;
                }
                double upper = copy[j][j];
                double ratio = - (item / upper);
                copy[i][j] = 0d;
                for (int k = j + 1; k < length; k++) {
                    double multiItem = copy[i][k];
                    double multiUpper = copy[j][k];
                    multiItem = multiItem + multiUpper * ratio;
                    copy[i][k] = multiItem;
                }
            }
        }
        return new SimpleDeterminant(copy);
    }

    /**
     * 数据深拷贝
     */
    private double[][] deepCopy() {
        int length = numberArrays.length;
        double[][] copy = new double[length][length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(numberArrays[i], 0, copy[i], 0, length);
        }
        return copy;
    }
}
