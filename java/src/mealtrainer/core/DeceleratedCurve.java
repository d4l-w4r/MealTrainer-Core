package mealtrainer.core;

import java.lang.Math;
import java.lang.Double;
import mealtrainer.core.util.Util;
import mealtrainer.core.MealConfig;

public class DeceleratedCurve {

     private final MealConfig config;
     private final double rateOfDeceleration;
     private final double initialEatingRate;

     public DeceleratedCurve(MealConfig config) {
          this.config = config;
          this.initialEatingRate = findInitialRateForMinimalTargetDifference();
          this.rateOfDeceleration = getRateOfDecline(initialEatingRate, config.getMealDuration());
     }

     public int getCurrentSecondsTillNextBite(int currentTimeSeconds) {
          final double decimalMinutes = Double.valueOf(currentTimeSeconds) / 60.0;
          final double estimateFoodConsumed = estimateWeightConsumedAtTime(decimalMinutes);
          final double foodConsumedIn1Minute = estimateWeightConsumedAtTime(decimalMinutes + 1);
          final double consumptionDelta = foodConsumedIn1Minute - estimateFoodConsumed;
          final double bitesNeeded = Math.max(consumptionDelta / config.weightPerBite(), 1.0);
          final double timePerBite = 60.0 / bitesNeeded;
          return (int) timePerBite;
     }

     private double estimateWeightConsumedAtTime(double x) {
          return (rateOfDeceleration * (x * x)) + (initialEatingRate * x);
     }

     private double getRateOfDecline(double initialEatingRate, int durationMinutes) {
          return -1 * (initialEatingRate / (2 * durationMinutes));
     }

     private double findInitialRateForMinimalTargetDifference() {
          final double[] B = Util.linspace(5, 160, 100);
          final double[] diffs = getDiffs(B);
          final double min_diff = Util.minValue(diffs);
          if (min_diff > 1) {
               System.err.println(
                         "[WARNING]: Can't estimate the target weight to within 1g.\n\t   Minimum absolute difference to target weight '"
                                   + min_diff + "g' for duration '" + config.getMealDuration() + " min' is "
                                   + config.getTargetWeight() + "g.");
          }
          return B[Util.indexOf(diffs, min_diff)];
     }

     private double[] getDiffs(double[] bValues) {
          double[] out = new double[bValues.length];
          final int target = config.getTargetWeight();
          final int duration = config.getMealDuration();
          for (int i = 0; i < bValues.length; i++) {
               out[i] = target
                         - (getRateOfDecline(bValues[i], duration) * (duration * duration) + bValues[i] * duration);
          }
          return out;
     }

}