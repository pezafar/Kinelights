package org.kinelights.core.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import org.kinelights.core.manager.animations.Animation;
import org.kinelights.core.manager.animations.AnimationMiniBeam;
import org.kinelights.core.manager.animations.AnimationMiniWash;
import org.kinelights.core.manager.animations.AnimationStrobo;
import org.kinelights.core.manager.materials.LightMaterial;
import org.kinelights.core.interfaces.AnimationInterface;
import org.kinelights.core.interfaces.DBmanagerInterface;
import org.kinelights.core.interfaces.GestureInterface;

/*

This class provides tools to handle animation files.
Animations are saved as text files, in the FOLDER corresponding to the  corresponding equipment
Lots of path are still hardcoded, as no path manager have been iplemented
 */

public class DBmanager  implements DBmanagerInterface {
	
	private Routine routine;
	public static String PATH_data = "/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data";
	public static String ANIMATION_FOLDER_NAME= "animations";
	public static String ANGLES_FOLDER_NAME = "angles";
	public static String SEPARATOR_MAINID_REST = "-";
	public static String SEPARATOR_FINGERS = ",";
	public static String ANIMATION_EXTENSION = ".txt";
	public static String ANIMATION_PATH = PATH_data + "/" + ANIMATION_FOLDER_NAME;

	public DBmanager(Routine routine) {
		this.routine = routine;
		
	}

	public boolean getStatus() {
		// TODO Auto-generated method stub
		return false;
	}

	public AnimationInterface getAnimationFromData(GestureInterface gesture, LightMaterial material) {
		String folderName = null;
		Animation animation = null;
		if (material.getType() == LightMaterial.TYPE_MINI_WASH){
			animation = new AnimationMiniWash(this.routine);
			folderName = ANIMATION_PATH + "/" + "MINIWASH";
		}
		else if (material.getType() == LightMaterial.TYPE_MINI_BEAM){
			animation = new AnimationMiniBeam(this.routine);
			folderName = ANIMATION_PATH + "/" + "MINIWASH";

		}
		else if(material.getType() == LightMaterial.TYPE_STROBO) {
			animation = new AnimationStrobo(this.routine);
			folderName = ANIMATION_PATH + "/" + "STROBOSCOPE";

		}
		animation.setID(this.getAnimationIDS(gesture.getMainID()));
		BufferedReader br=null;
		String line =  null;

		System.out.println(folderName);

		//on parcourt les différents blocs d'animations que le matériel en question peut assembler
		ArrayList<ArrayList<Integer>> blocks = material.getAnimation().getAnimationChannelsBlocks();
		ArrayList<String> blockNames = material.getAnimation().getAnimationBlocksNames();
		ArrayList<Integer> idList = this.getAnimationIdList(gesture.getMainID(), material.getType());

		//on parcours les différents blocs
		for ( int blockIndex = 0; blockIndex < blocks.size(); blockIndex++){

			String fileName = folderName + "/" + blockNames.get(blockIndex) + "/" + idList.get(blockIndex) + ".txt";
			//On récupère l'animation partielle qui correspond au bloc d'animation correspondant
			try
			{
				FileReader fr=new FileReader(fileName);
				br=new BufferedReader(fr);
				line=br.readLine();

				int compteur = 0;
				while (line!=null && compteur < blocks.get(blockIndex).size() )
				{
					ArrayList<Integer> valuesList = new ArrayList<Integer>();
					java.util.List<String> tempList =  Arrays.asList(line.split(",")) ;

					for(String valeurString : tempList) {
						valuesList.add(Integer.valueOf(valeurString));
					}
					System.out.println(valuesList);

					//We put the read list in the corresponding line of the corresponding channel of the animation
					animation.setValues(blocks.get(blockIndex).get(compteur), valuesList);

					line=br.readLine();
					compteur++;
				}
				br.close();

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		return (animation); 
	}

	public int getAnimationIDS(int gestureMainID) {
		BufferedReader br=null;
		String i=null;
		int animationID=0;
		
		try
		{
			FileReader fr=new FileReader("/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/GesturesID-AnimationsID.txt");
			br=new BufferedReader(fr);
			i=br.readLine();
			while (i!=null)
			{
				String[] Link =i.split("-");
				if ( Integer.valueOf(Link[0])== gestureMainID) {
					animationID=Integer.parseInt(Link[1]);
					System.out.println("animaton ID found");
					break;
				}
				i=br.readLine();
			}	
			br.close();	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		System.out.println("animation id trouvé : " + animationID);

		return animationID;
	}
	
	public String getName(int number, String blocktype) {
		BufferedReader br=null;
		String i=null;
		String name=null;
		
		
		try
		{
			FileReader fr=new FileReader("/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/mapping/Names/" + blocktype + ".txt");
			br=new BufferedReader(fr);
			i=br.readLine();
			while (i!=null)
			{
				String[] Link =i.split("-");
				if ( Integer.valueOf(Link[0])== number) {
					name=Link[1];
					System.out.println("on a trouvé le nom correspondant");
					break;
				}
				i=br.readLine();
			}	
			br.close();	
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		if (name == null) {
			name = String.valueOf(number);
			}
		return name;
	}
	public ArrayList<Integer> getAnimationIdList(int gestureMainID, int materialType){
		ArrayList<Integer> list = new ArrayList<>();
		BufferedReader br=null;
		String line=null;
		try
		{
			FileReader fr = null;
			if (materialType == LightMaterial.TYPE_STROBO){

				fr=new FileReader("/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/mapping/STROBOSCOPE.txt");
			}
			else if(materialType == LightMaterial.TYPE_MINI_BEAM){
				fr=new FileReader("/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/mapping/MINIBEAM.txt");
			}

			else if (materialType == LightMaterial.TYPE_MINI_WASH){
				fr=new FileReader("/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/mapping/MINIWASH.txt");
			}


			br=new BufferedReader(fr);
			line=br.readLine();
			while (line!=null)
			{
				String[] Link =line.split("-");

				if ( Integer.valueOf(Link[0])== gestureMainID) {
					String[] ids = Link[1].split(",");
					if (materialType == LightMaterial.TYPE_MINI_WASH){
						for (int i = 0; i < AnimationMiniWash.BLOCK_ANIMATION_NUMBER; i++ ){

							list.add(Integer.valueOf(ids[i]));
						}
					}
					else if (materialType == LightMaterial.TYPE_MINI_BEAM){
						for (int i = 0; i < AnimationMiniBeam.BLOCK_ANIMATION_NUMBER; i++ ){
							list.add(Integer.valueOf(ids[i]));
						}
					}
					else if (materialType == LightMaterial.TYPE_STROBO){
						for (int i = 0; i < AnimationStrobo.BLOCK_ANIMATION_NUMBER; i++ ){
							list.add(Integer.valueOf(ids[i]));
						}
					}

					else{
						System.out.println("incapable de chercher des animations pour d'autres matériels que MINIWASH");
					}
					break;
				}
				line=br.readLine();
			}
			br.close();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return (list);
	}

	public int getGestureMainID(int motionID, ArrayList<Boolean> fingersPosition) {
		int mainID= 0;
		for (int i = 0; i < 3; i++) {
			int temp = 0;
			if (fingersPosition.get(i)) {
				temp = 1;
			}
			else{temp = 0;}
			
			mainID += (int) Math.pow(2, i) * temp;
		}
		
		mainID += 100 * motionID;
		
		return (mainID);
	}

	public AnimationInterface getAnimation(GestureInterface gesture, LightMaterial material) {
		// TODO Auto-generated method stub
		if (material.getType() == LightMaterial.TYPE_MINI_WASH || true) {
			return getAnimationFromData(gesture, material);
		}
		else {
			System.out.println("ERREUR");
		}
		
		return null;
		
		
	}

	public void setAnimation(int gestureID, int newAnimationID) { //modifie le fichier GesturesID-AnimationsID
		PrintWriter pw=null;
		try
		{
			pw=new PrintWriter("/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/GesturesID-AnimationsID.txt");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			pw.close();
		}
	}

	public ArrayList<ArrayList<String>> getListOfAnimations(LightMaterial material ){

		ArrayList<ArrayList<String>> listToRet = new ArrayList<>();
		ArrayList<String> blockNames = material.getAnimation().getAnimationBlocksNames();
		String fileName = ANIMATION_PATH + "/" + material.getIndicator() + "/";

		for (String blockId : blockNames){
			ArrayList<String> blockList =  new ArrayList<>();

			File folder = new File(fileName +blockId);
			System.out.println(fileName+blockId);
			File[] listOfFiles = folder.listFiles();


			for (File file : listOfFiles){
				blockList.add(file.getName());
				System.out.println(file.getName());
			}
			listToRet.add(blockList);
		}

		return (listToRet);
	}


	public boolean addMapping(int motion, int light, int color, int gestId, LightMaterial material){


		String fileName = ANIMATION_PATH + "/" + material.getIndicator() + "/";
		ArrayList<String> blockNames = material.getAnimation().getAnimationBlocksNames();
		String matIndic = material.getIndicator();

		BufferedReader br=null;
		String line=null;


		try {
			FileReader fr = new FileReader("/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/mapping/" + matIndic + ".txt");
			br = new BufferedReader(fr);
			PrintWriter pw=null;
			int gestRead = 0;
			String lineRead = " ";

			int lineNumberToReplace = 0;
			int compteur = 0;

			ArrayList<String> content = new ArrayList<>();
			do{
				lineRead = br.readLine();
				if (lineRead != null){
					java.util.List<String> tempList =  Arrays.asList(lineRead.split("-") );
					if (Integer.parseInt(tempList.get(0)) == gestId){
						lineNumberToReplace = compteur;
					}
					content.add(lineRead);
				}
				compteur++;
			}
			while(lineRead != null);


			try
			{
					pw=new PrintWriter("/Users/joeltang/Documents/JavaProjects/pact55/software/java/pact55.software/data/mapping/" + matIndic + ".txt");

					if (material.getIndicator() == "STROBOSCOPE"){
						content.set(lineNumberToReplace, ""+ gestId + "-" + light);
					}
					else{
						content.set(lineNumberToReplace, ""+ gestId + "-" + motion +"," + light + "," + color);
					}

					String contentStringified = "";
					for (String lineInArray : content){
						contentStringified+= lineInArray + "\n";
					}

					pw.write(contentStringified);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				pw.close();
			}

			System.out.println(lineRead);
		}

		catch (Exception e){
			e.printStackTrace();
		}


		return (true);
	}





}
