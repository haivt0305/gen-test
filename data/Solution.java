public class Solution {
    private int numerator = 0;
    private int denominator = 1;

    public Solution(int numerator, int denominator) {
        if (denominator != 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }


    public static int findGCD(int x, int y) {
        //base case
        if(y== 0){
            return x;
        }
        return findGCD(y, x%y);
    }
}