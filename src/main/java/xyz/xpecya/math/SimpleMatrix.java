package xyz.xpecya.math;

/**
 * 浮点数矩阵
 */
public class SimpleMatrix implements Matrix {

    protected final double[][] numberArrays;

    SimpleMatrix(double[][] numberArrays) {
        this.numberArrays = numberArrays;
    }

    @Override
    public Matrix doAdd(Matrix matrix) {
        if (matrix instanceof ComplexMatrix complexMatrix) {
            return complexMatrix.doAdd(this);
        }
        int row = numberArrays.length;
        int column = numberArrays[0].length;
        double[][] numberArray = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArrays[i][j] + matrix.doGetDouble(i, j);
            }
        }
        return new SimpleMatrix(numberArray);
    }

    @Override
    public Matrix doMinus(Matrix matrix) {
        if (matrix instanceof ComplexMatrix) {
            return transform().doMinus(matrix);
        }
        int row = numberArrays.length;
        int column = numberArrays[0].length;
        double[][] numberArray = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArrays[i][j] - matrix.doGetDouble(i, j);
            }
        }
        return new SimpleMatrix(numberArray);
    }

    @Override
    public Matrix multi(double input) {
        int row = this.numberArrays.length;
        int column = this.numberArrays[0].length;
        double[][] numberArray = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArrays[i][j] * input;
            }
        }
        return new SimpleMatrix(numberArray);
    }

    @Override
    public Matrix doMulti(ComplexNumber complexNumber) {
        return transform().doMulti(complexNumber);
    }

    @Override
    public Matrix doMulti(Matrix matrix) {
        if (matrix instanceof ComplexMatrix) {
            return transform().doMulti(matrix);
        }

        int row = getRow();
        int column = getColumn();
        int inputColumn = matrix.getColumn();
        double[][] numberArray = new double[row][inputColumn];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < inputColumn; j++) {
                double result = 0d;
                for (int k = 0; k < column; k++) {
                    double thisNumber = this.numberArrays[i][k];
                    double inputNumber = matrix.doGetDouble(k, j);
                    double multi = thisNumber * inputNumber;
                    result = result + multi;
                }
                numberArray[i][j] = result;
            }
        }
        return new SimpleMatrix(numberArray);
    }

    @Override
    public Matrix doHadamard(Matrix matrix) {
        if (matrix instanceof ComplexMatrix complexMatrix) {
            return complexMatrix.doHadamard(this);
        }
        int row = getRow();
        int column = getColumn();
        double[][] numberArray = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArrays[i][j] * matrix.doGetDouble(i, j);
            }
        }
        return new SimpleMatrix(numberArray);
    }

    @Override
    public Matrix transpose() {
        int row = numberArrays.length;
        int column = numberArrays[0].length;
        double[][] newArray = new double[column][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                newArray[j][i] = numberArrays[i][j];
            }
        }
        return new SimpleMatrix(newArray);
    }

    @Override
    public int getRow() {
        return numberArrays.length;
    }

    @Override
    public int getColumn() {
        return numberArrays[0].length;
    }

    @Override
    public double doGetDouble(int row, int column) {
        return numberArrays[row][column];
    }

    @Override
    public double[] doGetDoubleRow(int row) {
        return numberArrays[row];
    }

    @Override
    public ComplexNumber[] doGetComplexRow(int row) {
        double[] doubleRow = doGetDoubleRow(row);
        return getComplexArray(doubleRow);
    }

    @Override
    public double[] doGetDoubleColumn(int column) {
        int row = getRow();
        double[] result = new double[row];
        for (int i = 0; i < row; i++) {
            result[i] = numberArrays[i][column];
        }
        return result;
    }

    @Override
    public ComplexNumber[] doGetComplexColumn(int column) {
        double[] doubleColumn = doGetDoubleColumn(column);
        return getComplexArray(doubleColumn);
    }

    @Override
    public Determinant doToDeterminant() {
        return new SimpleDeterminant(numberArrays);
    }

    @Override
    public ComplexNumber doGetComplex(int row, int column) {
        return new ComplexNumber(doGetDouble(row, column));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (double[] itemArray : numberArrays) {
            stringBuilder.append("| ");
            for (double item : itemArray) {
                stringBuilder.append(item).append(" ");
            }
            stringBuilder.append("|\r\n");
        }
        return stringBuilder.toString();
    }

    /**
     * 将当前矩阵变为复数矩阵
     * 如果是涉及到复数矩阵的运算，且该运算不满足交换律
     * 则需要首先将当前矩阵变换为复数矩阵再进行对应计算
     *
     * @return 复数矩阵
     */
    private ComplexMatrix transform() {
        int row = getRow();
        int column = getColumn();
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = new ComplexNumber(this.numberArrays[i][j]);
            }
        }
        return new ComplexMatrix(numberArray);
    }

    private ComplexNumber[] getComplexArray(double[] doubleArray) {
        int length = doubleArray.length;
        ComplexNumber[] result = new ComplexNumber[length];
        for (int i = 0; i < length; i++) {
            result[i] = new ComplexNumber(doubleArray[i]);
        }
        return result;
    }
}
