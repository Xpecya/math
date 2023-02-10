package xyz.xpecya.math;

/**
 * 复数矩阵
 */
public class ComplexMatrix extends Matrix {

    private final ComplexNumber[][] numberArray;

    ComplexMatrix(ComplexNumber[][] numberArray) {
        this.numberArray = numberArray;
    }

    @Override
    protected Matrix doAdd(Matrix matrix) {
        int row = numberArray.length;
        int column = numberArray[0].length;
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArray[i][j].add(matrix.doGetComplex(i, j));
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    protected Matrix doMinus(Matrix matrix) {
        int row = numberArray.length;
        int column = numberArray[0].length;
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArray[i][j].minus(matrix.doGetComplex(i, j));
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    public Matrix multi(double input) {
        return multi(new ComplexNumber(input));
    }

    @Override
    protected Matrix doMulti(ComplexNumber complexNumber) {
        int row = this.numberArray.length;
        int column = this.numberArray[0].length;
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArray[i][j].multi(complexNumber);
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    protected Matrix doMulti(Matrix matrix) {
        int row = getRow();
        int column = getColumn();
        int inputColumn = matrix.getColumn();

        ComplexNumber[][] numberArray = new ComplexNumber[row][inputColumn];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < inputColumn; j++) {
                ComplexNumber result = null;
                for (int k = 0; k < column; k++) {
                    ComplexNumber thisNumber = this.numberArray[i][k];
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
    protected Matrix doHadamard(Matrix matrix) {
        int row = getRow();
        int column = getColumn();
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArray[i][j].multi(matrix.doGetComplex(i, j));
            }
        }
        return new ComplexMatrix(numberArray);
    }

    @Override
    public Matrix transpose() {
        int row = numberArray.length;
        int column = numberArray[0].length;
        ComplexNumber[][] newArray = new ComplexNumber[column][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                newArray[j][i] = numberArray[i][j];
            }
        }
        return new ComplexMatrix(newArray);
    }

    @Override
    public int getRow() {
        return numberArray.length;
    }

    @Override
    public int getColumn() {
        return numberArray[0].length;
    }

    @Override
    public double doGetDouble(int row, int column) {
        return numberArray[row][column].real();
    }

    @Override
    protected double[] doGetDoubleRow(int row) {
        return getDoubleArray(doGetComplexRow(row));
    }

    @Override
    protected ComplexNumber[] doGetComplexRow(int row) {
        return new ComplexNumber[row];
    }

    @Override
    protected double[] doGetDoubleColumn(int column) {
        return getDoubleArray(doGetComplexColumn(column));
    }

    @Override
    protected ComplexNumber[] doGetComplexColumn(int column) {
        int row = getRow();
        ComplexNumber[] result = new ComplexNumber[row];
        for (int i = 0; i < row; i++) {
            result[i] = numberArray[i][column];
        }
        return result;
    }

    @Override
    protected Determinant doToDeterminant() {
        int row = getRow();
        ComplexNumber[][] newArray = new ComplexNumber[row][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                newArray[i][j] = numberArray[i][j].clone();
            }
        }
        return new ComplexDeterminant(newArray);
    }

    @Override
    public ComplexNumber doGetComplex(int row, int column) {
        return numberArray[row][column];
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (ComplexNumber[] itemArray : numberArray) {
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
