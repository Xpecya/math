package xyz.xpecya.math;

/**
 * 复数矩阵
 */
public class ComplexMatrix implements Matrix {

    protected final ComplexNumber[][] numberArrays;

    ComplexMatrix(ComplexNumber[][] numberArrays) {
        this.numberArrays = numberArrays;
    }

    @Override
    public Matrix doAdd(Matrix matrix) {
        int row = numberArrays.length;
        int column = numberArrays[0].length;
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArrays[i][j].add(matrix.doGetComplex(i, j));
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    public Matrix doMinus(Matrix matrix) {
        int row = numberArrays.length;
        int column = numberArrays[0].length;
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArrays[i][j].minus(matrix.doGetComplex(i, j));
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    public Matrix multi(double input) {
        return multi(new ComplexNumber(input));
    }

    @Override
    public Matrix doMulti(ComplexNumber complexNumber) {
        int row = this.numberArrays.length;
        int column = this.numberArrays[0].length;
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArrays[i][j].multi(complexNumber);
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    public Matrix doMulti(Matrix matrix) {
        int row = getRow();
        int column = getColumn();
        int inputColumn = matrix.getColumn();

        ComplexNumber[][] numberArray = new ComplexNumber[row][inputColumn];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < inputColumn; j++) {
                ComplexNumber result = null;
                for (int k = 0; k < column; k++) {
                    ComplexNumber thisNumber = this.numberArrays[i][k];
                    ComplexNumber inputNumber = matrix.doGetComplex(k, j);
                    ComplexNumber multi = thisNumber.multi(inputNumber);
                    if (result == null) {
                        result = multi;
                    } else {
                        result = result.add(multi);
                    }
                }
                numberArray[j][i] = result;
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    public Matrix doHadamard(Matrix matrix) {
        int row = getRow();
        int column = getColumn();
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArrays[i][j].multi(matrix.doGetComplex(i, j));
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    public Matrix transpose() {
        int row = numberArrays.length;
        int column = numberArrays[0].length;
        ComplexNumber[][] newArray = new ComplexNumber[column][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                newArray[j][i] = numberArrays[i][j];
            }
        }
        return new ComplexMatrix(newArray);
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
        return numberArrays[row][column].real();
    }

    @Override
    public double[] doGetDoubleRow(int row) {
        return getDoubleArray(doGetComplexRow(row));
    }

    @Override
    public ComplexNumber[] doGetComplexRow(int row) {
        return new ComplexNumber[row];
    }

    @Override
    public double[] doGetDoubleColumn(int column) {
        return getDoubleArray(doGetComplexColumn(column));
    }

    @Override
    public ComplexNumber[] doGetComplexColumn(int column) {
        int row = getRow();
        ComplexNumber[] result = new ComplexNumber[row];
        for (int i = 0; i < row; i++) {
            result[i] = numberArrays[i][column];
        }
        return result;
    }

    @Override
    public Determinant doToDeterminant() {
        return new ComplexDeterminant(numberArrays);
    }

    @Override
    public ComplexNumber doGetComplex(int row, int column) {
        return numberArrays[row][column];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ComplexNumber[] itemArray : numberArrays) {
            stringBuilder.append("| ");
            for (ComplexNumber complexNumber : itemArray) {
                stringBuilder.append(complexNumber).append(" ");
            }
            stringBuilder.append("|\r\n");
        }
        return stringBuilder.toString();
    }

    private double[] getDoubleArray(ComplexNumber[] complexNumbers) {
        int length = complexNumbers.length;
        double[] result = new double[length];
        for (int i = 0; i < length; i++) {
            result[i] = complexNumbers[i].real();
        }
        return result;
    }
}
