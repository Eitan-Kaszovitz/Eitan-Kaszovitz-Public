package edu.yu.introtoalgs;

public class TestClass {

    public static void main (String[] args) {
        InvertAFunction myClass = new InvertAFunction();
        System.out.println(myClass.cdf(527.783203125, 450, 75));
        System.out.println(myClass.inverseGaussianCDF(.85, .00025, 450, 75));

    }
}
