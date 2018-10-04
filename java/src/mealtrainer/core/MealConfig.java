package mealtrainer.core;

public class MealConfig {

     private final int mealDuration;
     private final int targetWeight;
     private final double weightPerBite;

     public MealConfig(int mealDuration, int targetWeight, double weightPerBite) {
          this.mealDuration = mealDuration;
          this.targetWeight = targetWeight;
          this.weightPerBite = weightPerBite;
     }

     /**
      * 
      * @return mealDuration in minutes
      */
     public int getMealDuration() {
          return mealDuration;
     }

     /**
      * 
      * @return targetWeight in gram
      */
     public int getTargetWeight() {
          return targetWeight;
     }

     /**
      * 
      * @return weightPerBite in gram
      */
     public double weightPerBite() {
          return weightPerBite;
     }
}