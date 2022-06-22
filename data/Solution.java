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
                return gcd(a, - b);
            } else {
                return gcd(- a, b);
            }
        }
    }
//
//    /**
//    * gcd.
//    */
//    public Solution reduce() {
//        Solution tmp = new Solution(numerator, denominator);
//        tmp.numerator /= gcd(numerator, denominator);
//        tmp.denominator /= gcd(numerator, denominator);
//        return tmp;
//    }
//
//    /**
//    * gcd.
//    */
//    public Solution add(Solution other) {
//        Solution tmp = new Solution(numerator, denominator);
//        tmp.numerator = tmp.numerator * other.denominator + tmp.denominator * other.numerator;
//        tmp.denominator *= other.denominator;
//        return tmp.reduce();
//    }
//
//    /**
//    * gcd.
//    */
//    public Solution subtract(Solution other) {
//        Solution tmp = new Solution(numerator, denominator);
//        tmp.numerator = tmp.numerator * other.denominator - tmp.denominator * other.numerator;
//        tmp.denominator *= other.denominator;
//        return tmp.reduce();
//    }
//
//    /**
//    * gcd.
//    */
//    public Solution multiply(Solution other) {
//        Solution tmp = new Solution(numerator, denominator);
//        tmp.numerator *= other.numerator;
//        tmp.denominator *= other.denominator;
//        return tmp.reduce();
//    }
//
//    /**
//    * gcd.
//    */
//    public Solution divide(Solution other) {
//        Solution tmp = new Solution(numerator, denominator);
//        tmp.numerator *= other.denominator;
//        tmp.denominator *= other.numerator;
//        return tmp.reduce();
//    }
//
//
//    public int getNumerator() {
//        return this.numerator;
//    }
//
//    public void setNumerator(int numerator) {
//        this.numerator = numerator;
//    }
//
//    public int getDenominator() {
//        return this.denominator;
//    }
//
//    /**
//    * Set.
//    */
//    public void setDenominator(int denominator) {
//        if (denominator != 0) {
//            this.denominator = denominator;
//        }
//    }
//
//    /**
//    * gcd.
//    */
//    public boolean equals(Object obj) {
//        if (obj instanceof Solution) {
//            Solution other = (Solution) obj;
//            if (this.numerator * other.denominator == this.denominator * other.numerator) {
//                return true;
//            }
//            return false;
//        }
//        return false;
//    }
} 