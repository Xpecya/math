package xyz.xpecya.math;

/**
 * 复数 基于double
 * 计算可能不是那么准确但是工程上够用
 * 不可变对象，所有复数运算都会生成一个新的复数对象
 */
public class ComplexNumber {

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

    public ComplexNumber add(ComplexNumber input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null!");
        }
        return new ComplexNumber(this.a + input.a, this.b + input.b);
    }

    public ComplexNumber minus(ComplexNumber input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null!");
        }
        return new ComplexNumber(this.a - input.a, this.b - input.b);
    }

    public ComplexNumber multi(ComplexNumber input) {
        if (input == null) {
            throw new IllegalArgumentException("input is null!");
        }
        return new ComplexNumber(this.a * input.a - this.b * input.b, this.a * input.b + this.b * input.a);
    }

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

    @Override
    public String toString() {
        if (b == 0) {
            return String.valueOf(a);
        }
        return String.format("%f%s%fi", a, b > 0 ? '+' : '-', Math.abs(b));
    }
}
