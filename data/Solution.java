public class Solution {
    private int numerator = 0;
    private int denominator = 1;

    /**
     * constructor 1.
     */
    public Solution(int numerator, int denominator) {
        if (denominator != 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    /**
     * gcd.
     */
    public static int gcd(int a, int b) {
        if (a * b == 0) {
            if (a == 0) {
                return b;
            }
            return a;
        }
        if (a * b > 0) {
            if (a == b) {
                return a;
            }
            if (a > b) {
                return gcd(a - b, b);
            }
            return gcd(a, b - a);
        } else {
            if (a > 0) {
                return gcd(a, -b);
            } else {
                return gcd(-a, b);
            }
        }
    }
}