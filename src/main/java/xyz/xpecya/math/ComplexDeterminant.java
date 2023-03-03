package xyz.xpecya.math;

/**
 * 行列式 基于复数实现
 */
public class ComplexDeterminant extends ComplexMatrix implements Determinant {

    private ComplexNumber value;

    ComplexDeterminant(ComplexNumber[][] numberArrays) {
        super(numberArrays);
    }

    @Override
    public double calculateDouble() {
        return calculateComplex().real();
    }

    @Override
    public ComplexNumber calculateComplex() {
        if (value != null) {
            return value;
        }
        if (numberArrays.length == 2) {
            // 2*2行列式 直接用定义
            ComplexNumber a = numberArrays[0][0].multi(numberArrays[1][1]);
            ComplexNumber b = numberArrays[0][1].multi(numberArrays[1][0]);
            value = a.minus(b);
        } else {
            ComplexDeterminant complexDeterminant = complexTriangle();
            for (int i = 0; i < numberArrays.length; i++) {
                ComplexNumber data = complexDeterminant.numberArrays[i][i];
                if (value == null) {
                    value = data;
                } else {
                    value = value.multi(data);
                }
            }
        }
        return value;
    }

    @Override
    public double[] doSolve(double[] value) {
        ComplexNumber[] complexValue = new ComplexNumber[value.length];
        for (int i = 0; i < numberArrays.length; i++) {
            complexValue[i] = new ComplexNumber(value[i]);
        }
        ComplexNumber[] complexResult = doSolve(complexValue);
        double[] result = new double[value.length];
        for (int i = 0; i < numberArrays.length; i++) {
            result[i] = complexResult[i].real();
        }
        return result;
    }

    @Override
    public ComplexNumber[] doSolve(ComplexNumber[] value) {
        ComplexNumber calc = calculateComplex();
        if (new ComplexNumber().equals(calc)) {
            return null;
        }
        int length = numberArrays.length;
        ComplexNumber[] result = new ComplexNumber[length];
        for (int i = 0; i < length; i++) {
            ComplexNumber[][] copy = deepCopy();
            for (int j = 0; j < length; j++) {
                copy[j][i] = value[j];
            }
            ComplexDeterminant complexDeterminant = new ComplexDeterminant(copy);
            ComplexNumber determinantValue = complexDeterminant.calculateComplex();
            result[i] = determinantValue.div(calc);
        }
        return result;
    }

    @Override
    public Determinant triangle() {
        return complexTriangle();
    }

    @Override
    public Determinant diagonal() {
        ComplexDeterminant complexDeterminant = complexTriangle();
        int length = complexDeterminant.numberArrays.length;
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                complexDeterminant.numberArrays[i][j] = new ComplexNumber();
            }
        }
        return complexDeterminant;
    }

    @Override
    public int getLength() {
        return numberArrays.length;
    }

    private ComplexDeterminant complexTriangle() {
        // 深拷贝一份
        int length = numberArrays.length;
        ComplexNumber[][] copy = deepCopy();
        ComplexNumber zeroNumber = new ComplexNumber();
        for (int i = 1; i < length; i++) {
            for (int j = 0; j < i; j++) {
                ComplexNumber item = copy[i][j];
                if (item.equals(zeroNumber)) {
                    continue;
                }
                ComplexNumber upper = copy[j][j];
                ComplexNumber ratio = item.div(upper);
                ratio = zeroNumber.minus(ratio);
                copy[i][j] = new ComplexNumber();
                for (int k = j + 1; k < length; k++) {
                    ComplexNumber multiItem = copy[i][k];
                    ComplexNumber multiUpper = copy[j][k];
                    multiUpper = multiUpper.multi(ratio);
                    multiItem = multiItem.add(multiUpper);
                    copy[i][k] = multiItem;
                }
            }
        }
        return new ComplexDeterminant(copy);
    }

    private ComplexNumber[][] deepCopy() {
        // 深拷贝一份
        int length = numberArrays.length;
        ComplexNumber[][] copy = new ComplexNumber[length][length];
        for (int i = 0; i < length; i++) {
            System.arraycopy(numberArrays[i], 0, copy[i], 0, length);
        }
        return copy;
    }
}
