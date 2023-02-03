package xyz.xpecya.math.test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import xyz.xpecya.math.ComplexNumber;

import java.lang.reflect.Field;
import java.util.Random;

public class ComplexNumberTest {

    /**
     * 用无参构造函数构造一个复数对象
     * 复数等于0
     */
    @Test
    public void createZero() {
        ComplexNumber result = new ComplexNumber();
        Assertions.assertEquals(result, new ComplexNumber(0));
    }

    /**
     * 用只有一个浮点数为参数的构造函数构造一个复数
     * 复数等于该浮点数
     */
    @Test
    public void createDouble() {
        Random random = new Random();
        double randomDouble = random.nextDouble();
        ComplexNumber result = new ComplexNumber(randomDouble);
        Assertions.assertEquals(result.getDoubleValue(), randomDouble);
    }

    /**
     * 通过两个浮点数构造复数
     * 分别检查构造的复数的实部和虚部
     */
    @Test
    public void createGeneral() throws NoSuchFieldException, IllegalAccessException {
        Random random = new Random();
        double randomA = random.nextDouble();
        double randomB = random.nextDouble();
        ComplexNumber result = new ComplexNumber(randomA, randomB);
        Assertions.assertEquals(result.getDoubleValue(), randomA);
        Field fieldB = ComplexNumber.class.getDeclaredField("b");
        fieldB.setAccessible(true);
        Assertions.assertEquals(randomB, fieldB.get(result));
    }

    /**
     * 测试复数加法
     * 随机构造两个复数 然后分别检查实部和虚部
     * 同时校验复数的不可变性
     * 加法交换律不用单独测试
     */
    @Test
    public void add() throws NoSuchFieldException, IllegalAccessException {
        ComplexNumber first = random();
        double firstA = first.getDoubleValue();
        Field fieldB = ComplexNumber.class.getDeclaredField("b");
        fieldB.setAccessible(true);
        double firstB = (double) fieldB.get(first);
        ComplexNumber second = random();
        double secondA = second.getDoubleValue();
        double secondB = (double) fieldB.get(second);
        ComplexNumber result = first.add(second);

        // 校验不可变性
        Assertions.assertEquals(first.getDoubleValue(), firstA);
        Assertions.assertEquals(fieldB.get(first), firstB);
        Assertions.assertEquals(second.getDoubleValue(), secondA);
        Assertions.assertEquals(fieldB.get(second), secondB);

        // 检验加法结果
        Assertions.assertEquals(result.getDoubleValue(), firstA + secondA);
        Assertions.assertEquals(fieldB.get(result), firstB + secondB);

        // 校验add方法入参不可为空
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> first.add(null));
    }

    /**
     * 测试复数减法
     * 随机构造两个复数 相减然后分别检查实部和虚部
     * 同时校验复数的不可变性
     */
    @Test
    public void minus() throws NoSuchFieldException, IllegalAccessException {
        ComplexNumber first = random();
        double firstA = first.getDoubleValue();
        Field fieldB = ComplexNumber.class.getDeclaredField("b");
        fieldB.setAccessible(true);
        double firstB = (double) fieldB.get(first);
        ComplexNumber second = random();
        double secondA = second.getDoubleValue();
        double secondB = (double) fieldB.get(second);
        ComplexNumber result = first.minus(second);

        // 校验不可变性
        Assertions.assertEquals(first.getDoubleValue(), firstA);
        Assertions.assertEquals(fieldB.get(first), firstB);
        Assertions.assertEquals(second.getDoubleValue(), secondA);
        Assertions.assertEquals(fieldB.get(second), secondB);

        // 检验减法结果
        Assertions.assertEquals(result.getDoubleValue(), firstA - secondA);
        Assertions.assertEquals(fieldB.get(result), firstB - secondB);

        // 校验minus方法入参不可为空
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> first.minus(null));
    }

    /**
     * 测试复数乘法
     * 随机构造两个复数 相乘然后分别检查实部和虚部
     * 同时校验复数的不可变性
     * 乘法交换律不用单独测试
     */
    @Test
    public void multi() throws NoSuchFieldException, IllegalAccessException {
        ComplexNumber first = random();
        double firstA = first.getDoubleValue();
        Field fieldB = ComplexNumber.class.getDeclaredField("b");
        fieldB.setAccessible(true);
        double firstB = (double) fieldB.get(first);
        ComplexNumber second = random();
        double secondA = second.getDoubleValue();
        double secondB = (double) fieldB.get(second);
        ComplexNumber result = first.multi(second);

        // 校验不可变性
        Assertions.assertEquals(first.getDoubleValue(), firstA);
        Assertions.assertEquals(fieldB.get(first), firstB);
        Assertions.assertEquals(second.getDoubleValue(), secondA);
        Assertions.assertEquals(fieldB.get(second), secondB);

        // 检验乘法结果
        Assertions.assertEquals(result.getDoubleValue(), firstA * secondA - firstB * secondB);
        Assertions.assertEquals(fieldB.get(result), firstA * secondB + firstB * secondA);

        // 校验multi方法入参不可为空
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> first.multi(null));
    }

    /**
     * 测试复数除法
     * 随机构造两个复数 相除然后分别检查实部和虚部
     * 同时校验复数的不可变性
     */
    @Test
    public void div() throws NoSuchFieldException, IllegalAccessException {
        ComplexNumber first = random();
        double firstA = first.getDoubleValue();
        Field fieldB = ComplexNumber.class.getDeclaredField("b");
        fieldB.setAccessible(true);
        double firstB = (double) fieldB.get(first);
        ComplexNumber second = random();
        double secondA = second.getDoubleValue();
        double secondB = (double) fieldB.get(second);
        ComplexNumber result = first.div(second);

        // 校验不可变性
        Assertions.assertEquals(first.getDoubleValue(), firstA);
        Assertions.assertEquals(fieldB.get(first), firstB);
        Assertions.assertEquals(second.getDoubleValue(), secondA);
        Assertions.assertEquals(fieldB.get(second), secondB);

        // 检验除法结果
        double floor = secondA * secondA + secondB * secondB;
        double resultA = (firstA * secondA + firstB * secondB) / floor;
        double resultB = (firstB * secondA - firstA * secondB) / floor;
        Assertions.assertEquals(result.getDoubleValue(), resultA);
        Assertions.assertEquals(fieldB.get(result), resultB);

        // 校验div方法入参不可为空
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> first.multi(null));

        // 除数不可为0
        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> first.div(new ComplexNumber()));
    }

    /**
     * 测试获取复数的浮点数值
     * 检测浮点数值等于实部
     */
    @Test
    public void getDoubleValueTest() throws NoSuchFieldException, IllegalAccessException {
        Random random = new Random();
        double randomDouble = random.nextDouble();
        ComplexNumber complexNumber = new ComplexNumber(randomDouble);
        Field field = ComplexNumber.class.getDeclaredField("a");
        field.setAccessible(true);
        Assertions.assertEquals(field.get(complexNumber), randomDouble);
    }

    @Test
    public void cloneTest() {
        ComplexNumber random = random();
        ComplexNumber clone = random.clone();
        Assertions.assertNotSame(random, clone);
        Assertions.assertEquals(random, clone);
    }

    private ComplexNumber random() {
        Random random = new Random();
        double randomA = random.nextDouble();
        double randomB = random.nextDouble();
        return new ComplexNumber(randomA, randomB);
    }
}
