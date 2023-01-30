package xyz.xpecya.math;

/**
 * 矩阵类 根据底层的不同有多个实现类
 * 不可变对象，所有矩阵运算都会生成一个新矩阵
 */
public abstract class Matrix {

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
     * 构建一个length * length的方阵
     *
     * @param length 方阵边长
     * @param initNumber 初始化浮点数值
     * @return 对应边长 初始值的方阵
     */
    public static Matrix square(int length, double initNumber) {
        return init(length, length, initNumber);
    }

    /**
     * 构建一个length * length的方阵
     *
     * @param length 方阵边长
     * @param initNumber 初始化复数值
     * @return 对应边长 初始值的方阵
     */
    public static Matrix square(int length, ComplexNumber initNumber) {
        return init(length, length, initNumber);
    }

    /**
     * 构建一个由任意浮点数构成的矩阵
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @param initNumber 初始化浮点值
     * @return 指定行数 列数 初始化值 的矩阵
     */
    public static Matrix init(int row, int column, double initNumber) {
        if (row <= 0) {
            throw new IllegalArgumentException("row cannot less than or equal to 0!");
        }
        if (column <= 0) {
            throw new IllegalArgumentException("column cannot less than or equal to 0!");
        }
        double[][] array = new double[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                array[i][j] = initNumber;
            }
        }
        return new SimpleMatrix(array);
    }

    /**
     * 构建一个由任意复数构成的矩阵
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @param initNumber 初始化浮点值
     * @return 指定行数 列数 初始化值 的矩阵
     */
    public static Matrix init(int row, int column, ComplexNumber initNumber) {
        if (row <= 0) {
            throw new IllegalArgumentException("row cannot less than or equal to 0!");
        }
        if (column <= 0) {
            throw new IllegalArgumentException("column cannot less than or equal to 0!");
        }
        ComplexNumber[][] array = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                array[i][j] = initNumber;
            }
        }
        return new ComplexMatrix(array);
    }

    /**
     * 通用创建函数 可以构造任意矩阵
     * 为避免运行时空指针异常，遍历确保数组中没有null项
     */
    public static Matrix create(double[][] numberArray) {
        if (numberArray == null || numberArray.length == 0) {
            throw new IllegalArgumentException("numberArray is null!");
        }
        int columnLength = 0;
        for (double[] doubles : numberArray) {
            if (doubles == null || doubles.length == 0) {
                throw new IllegalArgumentException("there are null elements in numberArray!");
            }
            if (columnLength == 0) {
                columnLength = doubles.length;
            } else if (columnLength != doubles.length) {
                throw new IllegalArgumentException("arrays' length in numberArray must be the same!");
            }
        }
        return new SimpleMatrix(numberArray);
    }

    /**
     * 通用创建函数 可以构造任意矩阵
     * 为避免运行时空指针异常，用二重遍历确保数组中没有null项
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
                throw new IllegalArgumentException("arrays' length in numberArray must be the same!");
            }
            for (ComplexNumber complexNumber : complexNumbers) {
                if (complexNumber == null) {
                    throw new IllegalArgumentException("there are null elements in numberArray!");
                }
            }
        }
        return new ComplexMatrix(numberArray);
    }

    /**
     * 矩阵加法 要求入参矩阵和当前矩阵的长和宽都完全相同
     *
     * @param matrix 入参矩阵
     * @return 计算后的新矩阵
     */
    public Matrix add(Matrix matrix) {
        sameMatrixCheck(matrix);
        return doAdd(matrix);
    }

    protected abstract Matrix doAdd(Matrix matrix);

    /**
     * 矩阵减法 要求入参矩阵和当前矩阵的长和宽都完全相同
     *
     * @param matrix 入参矩阵
     * @return 计算后的新矩阵
     */
    public Matrix minus(Matrix matrix) {
        sameMatrixCheck(matrix);
        return doMinus(matrix);
    }

    protected abstract Matrix doMinus(Matrix matrix);

    /**
     * 矩阵数乘
     *
     * @param input 要相乘的浮点数
     * @return 相乘结果
     */
    public abstract Matrix multi(double input);

    /**
     * 矩阵数乘
     *
     * @param complexNumber 要相乘的复数
     * @return 相乘结果
     */
    public Matrix multi(ComplexNumber complexNumber) {
        complexNumberNonNullRequirement(complexNumber);
        return doMulti(complexNumber);
    }

    protected abstract Matrix doMulti(ComplexNumber complexNumber);

    /**
     * 矩阵相乘 要求入参矩阵的column == 当前矩阵的row
     *
     * @param matrix 入参矩阵
     * @return 相乘结果
     */
    public Matrix multi(Matrix matrix) {
        matrixNonNullRequirement(matrix);
        int row = getRow();
        int inputColumn = matrix.getColumn();
        if (row != inputColumn) {
            throw new IllegalArgumentException("input matrix:\r\n" + matrix +
                    "\r\nthis matrix:\r\n" + this +
                    "\r\nthey cannot multi to each other!");
        }
        return doMulti(matrix);
    }

    protected abstract Matrix doMulti(Matrix matrix);

    /**
     * 哈达马乘积
     * 要求入参矩阵长宽和此矩阵相同
     *
     * @param matrix 入参矩阵
     * @return 相乘结果
     */
    public Matrix hadamard(Matrix matrix) {
        sameMatrixCheck(matrix);
        return doHadamard(matrix);
    }

    protected abstract Matrix doHadamard(Matrix matrix);

    /**
     * 转置
     */
    public abstract Matrix transpose();

    /**
     * 获取row长度
     */
    public abstract int getRow();

    /**
     * 获取column长度
     */
    public abstract int getColumn();

    /**
     * 获取矩阵指定位置的浮点值
     * 如果是复数矩阵，获取其实部
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @return 指定位置浮点数
     */
    public double getDoubleValue(int row, int column) {
        rowCheck(row);
        columnCheck(column);
        return doGetDoubleValue(row, column);
    }

    protected abstract double doGetDoubleValue(int row, int column);

    /**
     * 获取矩阵指定位置的复数值
     * 如果是浮点数矩阵，转换为复数输出
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @return 指定位置浮点数
     */
    public ComplexNumber getComplexValue(int row, int column) {
        rowCheck(row);
        columnCheck(column);
        return doGetComplexValue(row, column);
    }

    protected abstract ComplexNumber doGetComplexValue(int row, int column);

    private void sameMatrixCheck(Matrix matrix) {
        matrixNonNullRequirement(matrix);
        int row = getRow();
        int column = getColumn();
        int inputRow = matrix.getRow();
        int inputColumn = matrix.getColumn();
        if (row != inputRow || column != inputColumn) {
            throw new IllegalArgumentException("input matrix:\r\n" + matrix +
                    "\r\nthis matrix:\r\n" + this +
                    "\r\nthey cannot add to each other!");
        }
    }

    private void matrixNonNullRequirement(Matrix matrix) {
        if (matrix == null) {
            throw new IllegalArgumentException("matrix is null!");
        }
    }

    private void complexNumberNonNullRequirement(ComplexNumber complexNumber) {
        if (complexNumber == null) {
            throw new IllegalArgumentException("complexNumber is null!");
        }
    }

    private void rowCheck(int row) {
        if (row <= 0) {
            throw new IllegalArgumentException("request row must greater than zero!");
        }
        if (row >= getRow()) {
            throw new IllegalArgumentException("request row is out of this matrix!");
        }
    }

    private void columnCheck(int column) {
        if (column <= 0) {
            throw new IllegalArgumentException("request column must greater than zero!");
        }
        if (column >= getColumn()) {
            throw new IllegalArgumentException("request column is out of this matrix!");
        }
    }
}
