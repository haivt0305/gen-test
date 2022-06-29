import java.io.*;
public class Solution {
    private int numerator = 0;
    private int denominator = 1;

    public Solution(int numerator, int denominator) {
        try{
        //Specify the file name and path here
        File file =new File("/home/tj/Giang/gen-test/JGT-workspace/instrument/Solution/Solution.java-522520416.testpath");
 
        /* This logic is to create the file if the
         * file is not already present
         */
        if(!file.exists()){
           file.createNewFile();
        }
 
        //Here true is to append the content to file
        FileWriter fw = new FileWriter(file,true);
        //BufferedWriter writer give better performance
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("151CFGIfStatementNode{StartAt:151,EndAt:268" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
        if (denominator != 0) {
            try{
        //Specify the file name and path here
        File file =new File("/home/tj/Giang/gen-test/JGT-workspace/instrument/Solution/Solution.java-522520416.testpath");
 
        /* This logic is to create the file if the
         * file is not already present
         */
        if(!file.exists()){
           file.createNewFile();
        }
 
        //Here true is to append the content to file
        FileWriter fw = new FileWriter(file,true);
        //BufferedWriter writer give better performance
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("187CFGExpressionStatement{StartAt:187,EndAt:214" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
            this.numerator = numerator;
            try{
        //Specify the file name and path here
        File file =new File("/home/tj/Giang/gen-test/JGT-workspace/instrument/Solution/Solution.java-522520416.testpath");
 
        /* This logic is to create the file if the
         * file is not already present
         */
        if(!file.exists()){
           file.createNewFile();
        }
 
        //Here true is to append the content to file
        FileWriter fw = new FileWriter(file,true);
        //BufferedWriter writer give better performance
        BufferedWriter bw = new BufferedWriter(fw);
        bw.write("227CFGExpressionStatement{StartAt:227,EndAt:258" + "\n");
        //Closing BufferedWriter Stream
        bw.close();
 
    System.out.println("Data successfully appended at the end of file");
 
      }catch(IOException ioe){
         System.out.println("Exception occurred:");
         ioe.printStackTrace();
       }
            this.denominator = denominator;
        }
    }


    public static int findGCD(int x, int y) {
        if(y== 0){
            return x;
        }
        return findGCD(y, x%y);
    }
    public static void main(String[] args) {new Solution(570633225,1695735348);}
}