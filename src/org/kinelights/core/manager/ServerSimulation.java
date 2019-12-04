package org.kinelights.core.manager;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ServerSimulation implements  Runnable {

    private Routine routine;
    public  ServerSimulation(Routine routine){
        System.out.println("Launching simulation of server");
        this.routine = routine;
    }

    @Override
    public void run() {

        while(true){
            try{
            TimeUnit.SECONDS.sleep(1);

            int motionID = 1;
            Random random = new Random();
            float ratio= random.nextFloat();
            ArrayList<Boolean> fingers = new ArrayList<>();
            for(int i = 0; i <3; i++){
                fingers.add(random.nextBoolean());
            }
            Gesture gesture = new Gesture(motionID, fingers, routine);
            routine.getGlove().incomingGesture(gesture);
            routine.updatePow(ratio);
            System.out.println("Gesture sent in Server Sim");

            }
            catch(Exception e){
                e.printStackTrace();
            }

        }


    }
}
