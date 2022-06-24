public class Solution {
    private int numerator = 0;
    private int denominator = 1;

    public Solution(int numerator, int denominator) {
            if (denominator != 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }


    public static int gcd(int a, int b) {
        System.out.println("CFGIfStatementNode==={StartAt:344===EndAt:464===");
        if (a * b == 0) {
            System.out.println("CFGIfStatementNode==={StartAt:375===EndAt:430===");
            if (a == 0) {
                return b;
            }
            return a;
        }
        System.out.println("CFGIfStatementNode==={StartAt:474===EndAt:838===");
        if (a * b > 0) {
            System.out.println("CFGIfStatementNode==={StartAt:504===EndAt:559===");
            if (a == b) {
                return a;
            }
            System.out.println("CFGIfStatementNode==={StartAt:573===EndAt:639===");
            if (a > b) {
                return gcd(a - b, b);
            }
            return gcd(a, b - a);
        } else {
            System.out.println("CFGIfStatementNode==={StartAt:706===EndAt:827===");
            if (a > 0) {
                return gcd(a, -b);
            } else {
                return gcd(-a, b);
            }
        }
    }
    public static void main(String[] args) {Solution.gcd(1427165639,-674656292);}
}
