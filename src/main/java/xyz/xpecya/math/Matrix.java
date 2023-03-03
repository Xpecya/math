package xyz.xpecya.math;

/**
 * 矩阵类 根据底层的不同有多个实现类
 * 不可变对象，所有矩阵运算都会生成一个新矩阵
 */
public interface Matrix {

    /**
     * 构建一个全是0的矩阵
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @return 指定行数 列数的 全是0的矩阵
     */
    static Matrix zero(int row, int column) {
        return init(row, column, 0);
    }

    /**
     * 构建一个全是1的矩阵
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @return 指定行数 列数的 全是1的矩阵
     */
    static Matrix one(int row, int column) {
        return init(row, column, 1);
    }

    /**
     * 构建一个length * length的方阵
     *
     * @param length 方阵边长
     * @param initNumber 初始化浮点数值
     * @return 对应边长 初始值的方阵
     */
    static Matrix square(int length, double initNumber) {
        return init(length, length, initNumber);
    }

    /**
     * 构建一个length * length的方阵
     *
     * @param length 方阵边长
     * @param initNumber 初始化复数值
     * @return 对应边长 初始值的方阵
     */
    static Matrix square(int length, ComplexNumber initNumber) {
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
    static Matrix init(int row, int column, double initNumber) {
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
    static Matrix init(int row, int column, ComplexNumber initNumber) {
        if (row <= 0) {
            throw new IllegalArgumentException("row cannot less than or equal to 0!");
        }
        if (column <= 0) {
            throw new IllegalArgumentException("column cannot less than or equal to 0!");
        }
        ComplexNumber[][] array = new ComplexNumber[row][column];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < column; j++) {
                array[i][j] = initNumber.clone();
            }
        }
        return new ComplexMatrix(array);
    }

    /**
     * 通用创建函数 可以构造任意矩阵
     * 对源数组的修改不会导致矩阵值的修改，反之亦然
     */
    static Matrix create(double[][] numberArray) {
        if (numberArray == null || numberArray.length == 0) {
            throw new IllegalArgumentException("numberArray is empty!");
        }

        // 获取矩阵长度
        int column = 0;
        for (double[] doubles : numberArray) {
            if (doubles != null && doubles.length > column) {
                column = doubles.length;
            }
        }
        if (column < 1) {
            throw new IllegalArgumentException("numberArray is empty!");
        }
        double[][] newArray = new double[numberArray.length][column];

        for (int i = 0; i < numberArray.length; i++) {
            double[] doubles = numberArray[i];
            if (doubles == null) {
                continue;
            }
            System.arraycopy(numberArray[i], 0, newArray[i], 0, doubles.length);
        }
        return new SimpleMatrix(newArray);
    }

    /**
     * 通用创建函数 可以构造任意矩阵
     * 对源数组的修改不会导致矩阵值的修改，反之亦然
     */
    static Matrix create(ComplexNumber[][] numberArray) {
        if (numberArray == null || numberArray.length == 0) {
            throw new IllegalArgumentException("numberArray is null!");
        }

        // 获取矩阵长度
        int column = 0;
        for (ComplexNumber[] complexNumbers : numberArray) {
            if (complexNumbers != null && complexNumbers.length > column) {
                column = complexNumbers.length;
            }
        }
        if (column < 1) {
            throw new IllegalArgumentException("numberArray is empty!");
        }
        ComplexNumber[][] newArray = new ComplexNumber[numberArray.length][column];

        for (int i = 0; i < numberArray.length; i++) {
            ComplexNumber[] complexNumbers = numberArray[i];
            if (complexNumbers == null) {
                continue;
            }
            for (int j = 0; j < column; j++) {
                if (j < complexNumbers.length) {
                    ComplexNumber complexNumber = complexNumbers[j];
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
        return new ComplexMatrix(newArray);
    }

    /**
     * 矩阵加法 要求入参矩阵和当前矩阵的长和宽都完全相同
     *
     * @param matrix 入参矩阵
     * @return 计算后的新矩阵
     */
    default Matrix add(Matrix matrix) {
        sameMatrixCheck(matrix);
        return doAdd(matrix);
    }

    Matrix doAdd(Matrix matrix);

    /**
     * 矩阵减法 要求入参矩阵和当前矩阵的长和宽都完全相同
     *
     * @param matrix 入参矩阵
     * @return 计算后的新矩阵
     */
    default Matrix minus(Matrix matrix) {
        sameMatrixCheck(matrix);
        return doMinus(matrix);
    }

    Matrix doMinus(Matrix matrix);

    /**
     * 矩阵数乘
     *
     * @param input 要相乘的浮点数
     * @return 相乘结果
     */
    Matrix multi(double input);

    /**
     * 矩阵数乘
     *
     * @param complexNumber 要相乘的复数
     * @return 相乘结果
     */
    default Matrix multi(ComplexNumber complexNumber) {
        complexNumberNonNullRequirement(complexNumber);
        return doMulti(complexNumber);
    }

    Matrix doMulti(ComplexNumber complexNumber);

    /**
     * 矩阵相乘 要求入参矩阵的column == 当前矩阵的row
     *
     * @param matrix 入参矩阵
     * @return 相乘结果
     */
    default Matrix multi(Matrix matrix) {
        matrixNonNullRequirement(matrix);
        int column = getColumn();
        int inputRow = matrix.getRow();
        if (column != inputRow) {
            throw new IllegalArgumentException("input matrix:\r\n" + matrix +
                    "\r\nthis matrix:\r\n" + this +
                    "\r\nthey cannot multi to each other!");
        }
        return doMulti(matrix);
    }

    Matrix doMulti(Matrix matrix);

    /**
     * 哈达马乘积
     * 要求入参矩阵长宽和此矩阵相同
     *
     * @param matrix 入参矩阵
     * @return 相乘结果
     */
    default Matrix hadamard(Matrix matrix) {
        sameMatrixCheck(matrix);
        return doHadamard(matrix);
    }

    Matrix doHadamard(Matrix matrix);

    /**
     * 转置
     */
    Matrix transpose();

    /**
     * 获取row长度
     */
    int getRow();

    /**
     * 获取column长度
     */
    int getColumn();

    /**
     * 获取矩阵指定位置的浮点值
     * 如果是复数矩阵，获取其实部
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @return 指定位置浮点数
     */
    default double getDouble(int row, int column) {
        rowCheck(row);
        columnCheck(column);
        return doGetDouble(row, column);
    }

    double doGetDouble(int row, int column);

    /**
     * 获取矩阵指定位置的复数值
     * 如果是浮点数矩阵，转换为复数输出
     *
     * @param row    number of row 列数
     * @param column number of column 行数
     * @return 指定位置浮点数
     */
    default ComplexNumber getComplex(int row, int column) {
        rowCheck(row);
        columnCheck(column);
        return doGetComplex(row, column);
    }

    default double[] getDoubleRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException("编号不可小于0!");
        }
        int thisRow = getRow();
        if (row >= thisRow) {
            throw new IllegalArgumentException("编号" + row + "超过范围! 应在[0, " + (thisRow - 1) + "]之间!");
        }
        return doGetDoubleRow(row);
    }

    double[] doGetDoubleRow(int row);

    default ComplexNumber[] getComplexRow(int row) {
        if (row < 0) {
            throw new IllegalArgumentException("编号不可小于0!");
        }
        int thisRow = getRow();
        if (row >= thisRow) {
            throw new IllegalArgumentException("编号" + row + "超过范围! 应在[0, " + (thisRow - 1) + "]之间!");
        }
        return doGetComplexRow(row);
    }

    ComplexNumber[] doGetComplexRow(int row);

    default double[] getDoubleColumn(int column) {
        if (column < 0) {
            throw new IllegalArgumentException("编号不可小于0!");
        }
        int thisColumn = getColumn();
        if (column >= thisColumn) {
            throw new IllegalArgumentException("编号" + column + "超过范围! 应在[0, " + (thisColumn - 1) + "]之间!");
        }
        return doGetDoubleColumn(column);
    }

    double[] doGetDoubleColumn(int column);

    default ComplexNumber[] getComplexColumn(int column) {
        if (column < 0) {
            throw new IllegalArgumentException("编号不可小于0!");
        }
        int thisColumn = getColumn();
        if (column >= thisColumn) {
            throw new IllegalArgumentException("编号" + column + "超过范围! 应在[0, " + (thisColumn - 1) + "]之间!");
        }
        return doGetComplexColumn(column);
    }

    ComplexNumber[] doGetComplexColumn(int column);

    /**
     * 将当前矩阵转换为行列式
     * 要求当前矩阵必须是方阵 即row == column
     *
     * @return 转换结果
     */
    default Determinant toDeterminant() {
        int row = getRow();
        int column = getColumn();
        if (row != column) {
            throw new IllegalStateException("this matrix is not a square matrix! row = " + row + ", column = " + column);
        }
        return doToDeterminant();
    }

    Determinant doToDeterminant();

    ComplexNumber doGetComplex(int row, int column);

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
