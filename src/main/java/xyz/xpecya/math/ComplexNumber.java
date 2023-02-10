package xyz.xpecya.math;

/**
 * 复数 基于double
 * 计算可能不是那么准确但是工程上够用
 * 不可变对象，所有复数运算都会生成一个新的复数对象
 */
public class ComplexNumber implements Cloneable {

    private final double a;

    private final double b;

    public ComplexNumber() {
        this.a = 0d;
        this.b = 0d;
    }

    public ComplexNumber(double a) {
        this.a = a;
        this.b = 0d;
    }

    public ComplexNumber(double a, double b) {
        this.a = a;
        this.b = b;
    }

    /**
     * 复数加法
     */
    public ComplexNumber add(ComplexNumber input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null!");
        }
        return new ComplexNumber(this.a + input.a, this.b + input.b);
    }

    /**
     * 复数减法
     */
    public ComplexNumber minus(ComplexNumber input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null!");
        }
        return new ComplexNumber(this.a - input.a, this.b - input.b);
    }

    /**
     * 复数乘法
     */
    public ComplexNumber multi(ComplexNumber input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null!");
        }
        return new ComplexNumber(this.a * input.a - this.b * input.b, this.a * input.b + this.b * input.a);
    }

    /**
     * 复数除法
     */
    public ComplexNumber div(ComplexNumber input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null!");
        }
        double floor = input.a * input.a + input.b * input.b;
        if (floor == 0) {
            throw new IllegalArgumentException("0 cannot be dived!");
        }
        double resultA = (this.a * input.a + this.b * input.b) / floor;
        double resultB = (this.b * input.a - this.a * input.b) / floor;
        return new ComplexNumber(resultA, resultB);
    }

    /**
     * 获取复数实部
     */
    public double real() {
        return a;
    }

    /**
     * 获取复数虚部
     */
    public double imaginary() {
        return b;
    }

    /**
     * 获取复数的模
     */
    public double mod() {
        return Math.sqrt(a * a + b * b);
    }

    /**
     * 获取复数的主幅角
     */
    public double arg() {
        if (a != 0) {
            return Math.atan(b / a);
        }
        if (b == 0) {
            return 0d;
        }
        if (b > 0) {
            return Math.PI / 2;
        }
        return -Math.PI / 2;
    }

    @Override
    public ComplexNumber clone() {
        return new ComplexNumber(a, b);
    }

    @Override
    public String toString() {
        if (b == 0) {
            return String.valueOf(a);
        }
        return String.format("%f%s%fi", a, b > 0 ? '+' : '-', Math.abs(b));
    }

    @Override
    public int hashCode() {
        return Double.valueOf(a * 31 + b).intValue();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj instanceof ComplexNumber complexNumber) {
            return a == complexNumber.a && b == complexNumber.b;
        }
        return false;
    }

    /**
     * 由于double值存在计算误差，提供模糊相等函数
     *
     * @param complexNumber 要对比的复数
     * @param delta 误差范围
     */
    public boolean equals(ComplexNumber complexNumber, double delta) {
        if (complexNumber == null) {
            return false;
        }
        return equals(a, complexNumber.a, delta) && equals(b, complexNumber.b, delta);
    }

    private static boolean equals(double a, double b, double delta) {
        double minus = a - b;
        double abs = Math.abs(minus);
        double absDelta = Math.abs(delta);
        return abs < absDelta;
    }
}
