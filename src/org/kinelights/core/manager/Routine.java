package org.kinelights.core.manager;

import java.util.ArrayList;

import org.kinelights.core.manager.animations.Animation;
import org.kinelights.core.manager.animations.AnimationMiniBeam;
import org.kinelights.core.manager.animations.AnimationMiniWash;
import org.kinelights.core.manager.animations.AnimationStrobo;
import org.kinelights.core.manager.audioTests.AudioTest;
import org.kinelights.core.manager.materials.LightMaterial;
import org.kinelights.core.manager.materials.LightMaterialMiniBeam;
import org.kinelights.core.manager.materials.LightMaterialMiniWash;
import org.kinelights.core.manager.materials.LightMaterialStrobo;
import org.kinelights.core.interfaces.GestureInterface;
import org.kinelights.core.interfaces.RoutineInterface;
import org.pact55.software.interfaces.*;

import org.kinelights.gui.frontWindow.Window;
import org.pact55.software.pact55.software.materials.*;
import org.kinelights.server.Serveur;


//Core of the program
//

public class Routine  implements RoutineInterface {

		private Glove glove;
		private DMXmanager dmxManager;
		private Serveur serveur;
		private Gesture currentGesture;
		private DBmanager dbManager;
		private ArrayList<LightMaterial> materialList = new ArrayList<>();
		private float currentPow= 1.0f;
		private int bpm = 120;
		 
		private GestureInterface nextGesture = null;
		private boolean hasReceivedGesture = false;
		private boolean loopingActivated = true;
		
		private Window window;
		private String wavFileName;

		public void setWavFilename(String fileName) {
			this.wavFileName = fileName;
			JarReceiver jarRec = new JarReceiver();
			this.bpm = jarRec.getBpm(this.wavFileName);
		}

		public Routine() {

			//mettre les bon starting blocks
			LightMaterialMiniWash l1 = new LightMaterialMiniWash(this, 4);
			LightMaterialMiniBeam l3 = new LightMaterialMiniBeam(this, 66);
			LightMaterialStrobo l4 = new LightMaterialStrobo(this, 128);
			LightMaterialMiniWash l2 = new LightMaterialMiniWash(this, 280);

			materialList.add(l1);
			materialList.add(l2);
			materialList.add(l3);
			materialList.add(l4);
			
			window = new Window(this);
			glove = new Glove(this);
			dmxManager = new DMXmanager(this);
			dbManager = new DBmanager(this);

			Thread thread_DMXmanager = new Thread(dmxManager);
			thread_DMXmanager.start();

			try {
				Thread.sleep(500);
			}
			catch (Exception e) {
				e.printStackTrace();
			}

			//TODO : TO DEPLOY, UNCOMMENT THE THREE LINES AND DELETE THE FAKE SERVER
			this.serveur = new Serveur(this);
			Thread thread_serveur = new Thread(this.serveur);
			thread_serveur.start();

			//FAKE SERVER FOR LOCAL TESTS
			//ServerSimulation serverSimulation = new ServerSimulation(this);
			//**********************

			//Thread thread_simulation = new Thread(serverSimulation);
			//thread_simulation.start();


		}

		public boolean updateGesture(GestureInterface gesture) {
			System.out.println("gesture updated in routine : " + gesture );
			
			nextGesture = gesture;
			hasReceivedGesture = true;
			window.displayGesture(gesture);
			
			this.updateDMX();
			return false;
		}

		public void updatePow(float ratio) { // A MODIFIER
			System.out.println("ratio updated in routine : " + ratio );
			currentPow=ratio;
			System.out.println("POWER = " + currentPow);
		}

		public boolean updateBeat() {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean updateDMX() {

			System.out.println("updateing DMX");
			if (hasReceivedGesture) {

				System.out.println("ready to load animation");

				hasReceivedGesture =false;
				Animation animation = null;
				System.out.println("animation loaded");

				for (LightMaterial material : this.materialList) {
					if (material.getType() == LightMaterial.TYPE_MINI_WASH ) {
						animation = new AnimationMiniWash(this);
						animation.setID(dbManager.getAnimationIDS(nextGesture.getMainID()));
						animation = (AnimationMiniWash) dbManager.getAnimation(nextGesture, material);
						material.setAnimation(animation);
					}
					else if (material.getType() == LightMaterial.TYPE_MINI_BEAM){
						animation = new AnimationMiniBeam(this);
						animation.setID(dbManager.getAnimationIDS(nextGesture.getMainID()));
						animation = (AnimationMiniBeam) dbManager.getAnimation(nextGesture, material);
						material.setAnimation(animation);

					}
					else if (material.getType() == LightMaterial.TYPE_STROBO){
						animation = new AnimationMiniBeam(this);
						animation.setID(dbManager.getAnimationIDS(nextGesture.getMainID()));
						animation = (AnimationStrobo) dbManager.getAnimation(nextGesture, material);
						material.setAnimation(animation);
					}
				}
			}
			else{
				ArrayList<Boolean> fingersPos = new ArrayList<>();
				for(int i =0; i <5; i++){
					fingersPos.add(false);
				}

				Gesture gestureNull = new Gesture(5, fingersPos, this);
				AnimationMiniWash animation = new AnimationMiniWash(this);

				for (LightMaterial material : this.materialList) {
					if (material.getType() == LightMaterial.TYPE_MINI_WASH && false) {
						animation = (AnimationMiniWash) dbManager.getAnimation(gestureNull, material);
						material.setAnimation(animation);
					}
				}

			}
			return false;
		}
	 
		public Glove getGlove() {
			// TODO Auto-generated method stub
			return glove;
		}

		public DBmanager getDBmanager() {
			return this.dbManager;
		}
		
		public ArrayList<LightMaterial> getLightMaterialList(){
			return (this.materialList);
		}

		public int getBPM() {
			return(this.bpm);
		}
		
		public float getPow() {
			return(this.currentPow);
		}
		
		private void addLightMaterial(int type, int channelStart) {
			
			LightMaterial material = new LightMaterial(this,channelStart);
			if (type == LightMaterial.TYPE_MINI_WASH) {
				material = new LightMaterialMiniWash(this, channelStart);
			}
			else if (type == LightMaterial.TYPE_MINI_BEAM) {
				
			}
			materialList.add(material);
		}
		
		private boolean setStartChanel (int materialID, int startChannel) {
			
			if (materialID > materialList.size()) {
				System.out.println("Unable to change material ID : out of range");
				return (false);
			}
			
			else {
				materialList.get(materialID).setChannelStart(startChannel);
				return(true);
			}
		}
		
		public boolean hasNewGesture() {
			return hasReceivedGesture;
		}

		public void displayBeatSampleDown(int currentPositionInBeat) {
			// TODO Auto-generated method stub
			this.window.nextSample(currentPositionInBeat);		
		}
		 
		public Window getWindow(){
			return (this.window);
		}
		
	}



	

	

