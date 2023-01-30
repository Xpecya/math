package xyz.xpecya.math;

/**
 * 矩阵 底层是复数二维数组
 * 不可变对象，所有矩阵运算都会生成一个新矩阵
 */
public class Matrix {

    private final ComplexNumber[][] numberArray;

    private Matrix(ComplexNumber[][] numberArray) {
        this.numberArray = numberArray;
    }

    /**
     * 通用创建函数 可以构造任意矩阵
     * 为避免运行时空指针异常，用二重遍历确保数组中没有null项
     * 构建大型矩阵时请注意 也许用{@link #one(int, int)}
     * 或者{@link #zero(int, int)}
     * 或者{@link #square(int, ComplexNumber)}
     * 可以获得更好的性能
     */
    public static Matrix create(ComplexNumber[][] numberArray) {
        if (numberArray == null || numberArray.length == 0) {
            throw new IllegalArgumentException("numberArray is null!");
        }
        int columnLength = 0;
        for (ComplexNumber[] complexNumbers : numberArray) {
            if (complexNumbers == null || complexNumbers.length == 0) {
                throw new IllegalArgumentException("there are null elements in numberArray!");
            }
            if (columnLength == 0) {
                columnLength = complexNumbers.length;
            } else if (columnLength != complexNumbers.length) {
                throw new IllegalArgumentException("");
            }
            for (ComplexNumber complexNumber : complexNumbers) {
                if (complexNumber == null) {
                    throw new IllegalArgumentException("there are null elements in numberArray!");
                }
            }
        }
        return new Matrix(numberArray);
    }

    /**
     * 构建一个全是0的矩阵
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @return 指定行数 列数的 全是0的矩阵
     */
    public static Matrix zero(int row, int column) {
        return init(row, column, 0);
    }

    /**
     * 构建一个全是1的矩阵
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @return 指定行数 列数的 全是1的矩阵
     */
    public static Matrix one(int row, int column) {
        return init(row, column, 1);
    }

    /**
     * 构建一个length * length的方阵 并将内容初始化为给定值
     * @param length 方阵边长
     * @param value 方阵中每个复数值
     */
    public static Matrix square(int length, ComplexNumber value) {
        if (length <= 0) {
            throw new IllegalArgumentException("length must greater than 0!");
        }
        if (value == null) {
            throw new IllegalArgumentException("init value is null!");
        }
        ComplexNumber[][] numbers = new ComplexNumber[length][length];
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                numbers[i][j] = value;
            }
        }
        return new Matrix(numbers);
    }

    /**
     * 矩阵加法 要求入参矩阵和当前矩阵的长和宽都完全相同
     *
     * @param matrix 入参矩阵
     * @return 计算后的新矩阵
     */
    public Matrix add(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix is null!");
        }
        int row = numberArray.length;
        int column = numberArray[0].length;
        int inputRow = matrix.numberArray.length;
        int inputColumn = matrix.numberArray[0].length;
        if (row != inputRow || column != inputColumn) {
            throw new IllegalArgumentException(
                    "input matrix:\r\n" + matrix +
                            "\r\nthis matrix:\r\n" + this +
                            "\r\nthey cannot add to each other!");
        }
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArray[i][j].add(matrix.numberArray[i][j]);
            }
        }
        return new Matrix(numberArray);
    }

    /**
     * 矩阵加法 要求入参矩阵和当前矩阵的长和宽都完全相同
     *
     * @param matrix 入参矩阵
     * @return 计算后的新矩阵
     */
    public Matrix minus(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix is null!");
        }
        int row = numberArray.length;
        int column = numberArray[0].length;
        int inputRow = matrix.numberArray.length;
        int inputColumn = matrix.numberArray[0].length;
        if (row != inputRow || column != inputColumn) {
            throw new IllegalArgumentException("input matrix:\r\n" + matrix +
                    "\r\nthis matrix:\r\n" + this +
                    "\r\nthey cannot minus from each other!");
        }
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArray[i][j].minus(matrix.numberArray[i][j]);
            }
        }
        return new Matrix(numberArray);
    }

    /**
     * 矩阵数乘
     *
     * @param complexNumber 要相乘的数
     * @return 相乘结果
     */
    public Matrix multi(ComplexNumber complexNumber) {
        if (complexNumber == null) {
            throw new IllegalArgumentException("complexNumber is null!");
        }
        int row = this.numberArray.length;
        int column = this.numberArray[0].length;
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArray[i][j].multi(complexNumber);
            }
        }
        return new Matrix(numberArray);
    }

    /**
     * 矩阵相乘 要求
     *
     * @param matrix 要相乘的数
     * @return 相乘结果
     */
    public Matrix multi(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix is null!");
        }
        int row = this.numberArray.length;
        int column = this.numberArray[0].length;
        int inputRow = matrix.numberArray[0].length;
        int inputColumn = matrix.numberArray.length;
        if (row != inputRow) {
            throw new IllegalArgumentException("input matrix:\r\n" + matrix +
                    "\r\nthis matrix:\r\n" + this +
                    "\r\nthey cannot multi to each other!");
        }

        ComplexNumber[][] numberArray = new ComplexNumber[row][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                ComplexNumber result = null;
                for (int k = 0; k < column; k++) {
                    if (k >= inputColumn) {
                        break;
                    }
                    ComplexNumber thisNumber = this.numberArray[j][k];
                    ComplexNumber inputNumber = matrix.numberArray[k][i];
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
        return new Matrix(numberArray);
    }

    /**
     * 哈达马乘积
     * 要求入参矩阵长宽和此矩阵相同
     */
    public Matrix hadamard(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix is null!");
        }
        int row = numberArray.length;
        int column = numberArray[0].length;
        int inputRow = matrix.numberArray.length;
        int inputColumn = matrix.numberArray[0].length;
        if (row != inputRow || column != inputColumn) {
            throw new IllegalArgumentException(
                    "input matrix:\r\n" + matrix +
                            "\r\nthis matrix:\r\n" + this +
                            "\r\nthey cannot hadamard to each other!");
        }
        ComplexNumber[][] numberArray = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                numberArray[i][j] = this.numberArray[i][j].multi(matrix.numberArray[i][j]);
            }
        }
        return new Matrix(numberArray);
    }

    /**
     * 转置
     */
    public Matrix transpose(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix is null!");
        }
        ComplexNumber[][] numberArray = matrix.numberArray;
        int row = numberArray.length;
        int column = numberArray[0].length;
        ComplexNumber[][] newArray = new ComplexNumber[column][row];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                newArray[j][i] = numberArray[i][j];
            }
        }
        return new Matrix(newArray);
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

    private static Matrix init(int row, int column, int initNumber) {
        if (row <= 0) {
            throw new IllegalArgumentException("row cannot less than or equal to 0!");
        }
        if (column <= 0) {
            throw new IllegalArgumentException("column cannot less than or equal to 0!");
        }
        ComplexNumber[][] array = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                array[i][j] = new ComplexNumber(initNumber);
            }
        }
        return new Matrix(array);
    }
}
