// Chapter 8 Exercise 3

import static java.lang.Math.random;

public class FormatDoubleData {
  public static void main(String[] args) {
    double[] data = new double[20];
    
    for (int i = 0 ; i<data.length ; i++) {
      data[i] = 100.0*random()-50.0;
    }
    int i = 0;
    for(double x : data) {
      System.out.printf("%4d)%+7.2f", ++i, x);
      if(i%5 == 0) {
        System.out.println();
      }
    }
  }
}