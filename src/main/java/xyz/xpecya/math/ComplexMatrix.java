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
                numberArray[i][j] = this.numberArray[i][j].add(matrix.doGetComplexValue(i, j));
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
                numberArray[i][j] = this.numberArray[i][j].minus(matrix.doGetComplexValue(i, j));
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
        int inputRow = matrix.getRow();

        ComplexNumber[][] numberArray = new ComplexNumber[row][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                ComplexNumber result = null;
                for (int k = 0; k < column; k++) {
                    if (k >= inputRow) {
                        break;
                    }
                    ComplexNumber thisNumber = this.numberArray[j][k];
                    ComplexNumber inputNumber = matrix.doGetComplexValue(k, i);
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
                numberArray[i][j] = this.numberArray[i][j].multi(matrix.doGetComplexValue(i, j));
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
    public double doGetDoubleValue(int row, int column) {
        return numberArray[row][column].getDoubleValue();
    }

    @Override
    public ComplexNumber doGetComplexValue(int row, int column) {
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
}
