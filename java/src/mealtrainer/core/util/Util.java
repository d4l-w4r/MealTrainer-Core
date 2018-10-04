package mealtrainer.core.util;

public class Util {
     public static double[] linspace(double min, double max, int points) {
          double[] d = new double[points];
          for (int i = 0; i < points; i++) {
               d[i] = min + i * (max - min) / (points - 1);
          }
          return d;
     }

     public static double minValue(double[] values) {
          double currentMin = values[0];
          for (double value : values) {
               if (value < currentMin) {
                    currentMin = value;
               }
          }
          return currentMin;
     }

     public static int indexOf(double[] values, double value) {
          for (int i = 0; i < values.length; i++) {
               if (values[i] == value) {
                    return i;
               }
          }
          return -1;
     }
}