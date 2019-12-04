package org.kinelights.core.manager;

import java.util.ArrayList;
import java.util.concurrent.CyclicBarrier;

import org.kinelights.core.manager.animations.Animation;
import org.kinelights.core.manager.materials.LightMaterial;
import org.kinelights.core.interfaces.AnimationInterface;
import org.kinelights.core.interfaces.DmxManagerInterface;
import org.kinelights.core.interfaces.LightMaterialInterface;



/*

Handles DMX communication, through Serial
 */
public class DMXmanager implements DmxManagerInterface, Runnable {
	private LightMaterialInterface hardware1;
	private int hardwareNumber = 1;
	private int currentBeat = 0;
	private int currentPositionInBeat = 0;
	private long lastSendTime = 0;
	private float currentPow = 1;
	private byte[] byteArrayToSend = new byte[518];
	private Routine routine;
	private CyclicBarrier barriere;

	public DMXmanager(Routine routine) {
		super();
		this.init();
		this.routine = routine;
	}

	public void init() {
		initializeDmxArray();		
	}

	public void playAnimation() {
		}

	public void run() {
		System.out.println("DMX thread starting");
		com.fazecast.jSerialComm.SerialPort serialPort = com.fazecast.jSerialComm.SerialPort.getCommPort("/dev/tty.usbserial-EN198886");
		serialPort.openPort();
		System.out.println("Serial connected");
		
		int beatTimeMarkerAdvancement = 0;
		long lastMarkerTime = 0;
		long lastWaveformAnimationTime = System.currentTimeMillis();
		long lastRatioCheck=lastWaveformAnimationTime;
		while( true) {

			/*
			We check if it is time to send a nex DMX frame
			If so: we run through equipment, then through each animation, then for each channel, we send the corresponding value
			 */

			long timeNano = System.nanoTime() ;
			long timeMilli = System.currentTimeMillis();

			//checks if it time to update the beat metronome (does it 5 times/beat)
			if (timeNano -lastMarkerTime > (int) (1000*1000*1000*(60.0/routine.getBPM())/5 )) {
				routine.displayBeatSampleDown(beatTimeMarkerAdvancement);
				beatTimeMarkerAdvancement++;
				lastMarkerTime = timeNano;
			}

			//Is it time to send a dmx array ?
			if (timeNano -lastSendTime > (int) (1000*1000*1000*(60.0/routine.getBPM())/ Animation.RATE_PER_BEAT )) {
				//valeurTestEnvoi ++;
				lastSendTime = System.nanoTime();
				for(LightMaterial material : this.routine.getLightMaterialList() ) {
					int channelCount = 0; 
					for (ArrayList<Integer> valuesList : material.getAnimation().getAnimationListList() ) {
						if (channelCount+1==material.getAnimation().getBrightnessChannel() && material.getIndicator() != "STROBOSCOPE") {
							currentPow = Math.max(0.25f, routine.getPow());
							byteArrayToSend[material.getChannelStart() -1 + 5  + channelCount] =  (byte) ( currentPow*((float)valuesList.get(currentPositionInBeat).byteValue()));
						}
						else {
							byteArrayToSend[material.getChannelStart() -1 + 5  + channelCount] =   (byte) (valuesList.get(currentPositionInBeat).byteValue());
							//byteArrayToSend[material.getChannelStart() -1 + 5  + channelCount] =   (byte) (valuesList.get(currentPositionInBeat).byteValue());
						}
						channelCount++;
					}
				}

				for (int i = 7; i < 518; i++){
					//byteArrayToSend[i] = (byte) (100);
					System.out.print(byteArrayToSend[i] + " ");
				}

				System.out.println("");
				serialPort.writeBytes(byteArrayToSend, 518);
				currentPositionInBeat++;

				//We check if we reached the end of the animation
				if (currentPositionInBeat >= Animation.RATE_PER_BEAT * 4) {
					currentPositionInBeat = 0;
					beatTimeMarkerAdvancement = 0;
					routine.updateDMX();
				}
			}
			
		}
	}

	public void updateAnimation(AnimationInterface animation) {
		hardware1.setAnimation(animation);
		this.playAnimation();
	}


	//Opening sequence according to Entecc documentation to communicate through Serial to the DMX interface
	public void initializeDmxArray() {
		byteArrayToSend[0] = 126;
		byteArrayToSend[1] = 0x06;
		byteArrayToSend[2] = 0x01;
		byteArrayToSend[3] = 0x02;
		byteArrayToSend[4] = 0x00;

		byteArrayToSend[517] = 00;
	
		
	}
	
	
}
