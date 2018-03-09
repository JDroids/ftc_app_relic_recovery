package org.firstinspires.ftc.teamcode.resources;

import android.util.Log;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Created by dansm on 3/8/2018.
 */

public class MotionProfiling {
    private ArrayList<Double> timeList = new ArrayList<Double>();
    private ArrayList<Double> velocityList = new ArrayList<Double>();
    private ArrayList<Double> positionList = new ArrayList<Double>();

    private double kv = 0;
    private double kp = 0;

    private boolean firstTime = true;

    private double startTime;
    private double currentTime;

    private double currentError;

    private int currentIndex;

    private double velocityToTravel;

    public double calculatePower(double distanceToTravel, double currentDistance){
        //We are ignoring Ka because calculating accleration before hand is hard math I cannot do :P
        if(firstTime){
            startTime = System.currentTimeMillis();
            firstTime = false;
        }

        currentTime = (((int) System.currentTimeMillis() - startTime+5)/10)*10; //Rounds to closest 10 milliseconds

        currentIndex = timeList.indexOf(currentTime);

        currentError = velocityList.get(currentIndex) - currentDistance;

        velocityToTravel = kv * velocityList.get(currentIndex);
        velocityToTravel += kp * currentError;

        return velocityToTravel;

    }

    public void setCoeffecients(double Kv, double Kp) {
        kv = Kv;
        kp = Kp;
    }

    public boolean readMotionProfileFile(String csvFile){
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        try {
            br = new BufferedReader(new FileReader(csvFile));

            Log.d("JDFile", "Passed creation of br");

            while ((line = br.readLine()) != null) {

                // use comma as separator
                String[] lineValue = line.split(cvsSplitBy);

                Log.d("JDFile", "Retrieved Values: " + lineValue[0]);

                timeList.add(Double.parseDouble(lineValue[0]));
                velocityList.add(Double.parseDouble(lineValue[1]));
                positionList.add(Double.parseDouble(lineValue[2]));
            }

            Log.d("JDFile", "Passed while loop");
        }
        catch (FileNotFoundException e) {
            return false;
        }
        catch (IOException e) {
            return false;
        }
        finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if(!timeList.isEmpty()){
                return true;
            }
            else{
                return false;
            }
        }
    }
}