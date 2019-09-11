package edu.yu.introtoalgs;

public class InvertAFunction {


    /**
     * No-argument constructor.
     */
    public InvertAFunction() {

    }


    /**
     * Return the CDF (the "area under the normal curve") for the
     * specified
     * point in a Gaussian distribution characterized by the
     * specified mean and
     * standard deviation.
     *
     * @param z      Value from this Gaussian distribution
     * @param mean   Value of the distribution’s mean
     * @param stddev Value of the distribution’s standard deviation
     * @return Cumulative distribution function for the specified
     * value z: the
     * probability that a random Gaussian variable from this
     * distribution will be
     * less than z
     */
    public double cdf(final double z, final double mean, double stddev) {

        double zNew = (z - mean) / stddev;
        double meanNew = 0;
        double stddevNew = 1;

        double upperNumber = ((zNew * zNew) / (2)) * -1;
        double pdf = (1 / (Math.sqrt(2 * Math.PI))) * Math.pow(Math.E, upperNumber);

        double TSnumber = 0;
        double num = 0;
        double multNum = 1;
        for (int n = 1; n < 105; n += 2) {
            num = Math.pow(zNew, n);
            multNum *= n;
            TSnumber += (num / multNum);
        }
        double cdf = .5 + pdf * TSnumber;
        return cdf;
    }


    /**
     * Assume that y = f(x) is an random variable from the specified
     * Gaussian
     * distribution , return the value "x" for which the CDF(x)=y,
     * accurate to the
     * specified precision delta.
     *
     * @param y      Gaussian CDF value for a standard normal
     *               distribution
     * @param delta  Desired precision of answer
     * @param mean   Value of the distribution’s mean
     * @param stddev Value of the distribution’s standard deviation
     * @return Value x whose Gaussian CDF = y
     */
    public double inverseGaussianCDF(double y, double delta, double mean, double stddev) {
        double l = -3;
        double r = 3;
        while (true) {
            double mid = (Math.abs(l + r)) / 2;
            double z = cdf(mid, 0, 1);
            if (Math.abs(z - y) < delta) {
                return (mid * stddev) + mean;
            } else {
                if (z > y) {
                    r = mid;
                }
                if (z < y) {
                    l = mid;
                }
            }
        }
    }
}