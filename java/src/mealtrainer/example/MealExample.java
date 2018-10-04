package mealtrainer.example;

import java.lang.Thread;
import java.lang.System;
import mealtrainer.core.*;

public class MealExample {

    public static void main(String[] args) {
        final MealConfig config = new MealConfig(14, 350, 16);
        final DeceleratedCurve curve = new DeceleratedCurve(config);
        int passes = 0;
        final long startTime = System.currentTimeMillis();
        try {
            while (passes < 20) {
                int now = (int) (System.currentTimeMillis() - startTime) / 1000;
                int wait = curve.getCurrentSecondsTillNextBite(now);
                System.out.println("Eating since " + now + " seconds.\nTime till next bite: " + wait);
                Thread.sleep(wait * 1000);
                passes++;
            }
        } catch (InterruptedException exception) {
            System.err.println(exception.getLocalizedMessage());
        }
    }
}