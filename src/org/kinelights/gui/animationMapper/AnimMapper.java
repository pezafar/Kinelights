package org.kinelights.gui.animationMapper;

import org.kinelights.gui.frontWindow.Window;
import org.kinelights.core.manager.DBmanager;
import org.kinelights.core.manager.Gesture;
import org.kinelights.core.manager.materials.LightMaterial;
import org.kinelights.core.manager.materials.LightMaterialMiniBeam;
import org.kinelights.core.manager.materials.LightMaterialMiniWash;
import org.kinelights.core.manager.materials.LightMaterialStrobo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;


/*

    This windows provides help to map hand/fingers gestures to available animations
    This mapping is saved in a file, in the mapping folder, in the corresponding equipment folder

 */

public class AnimMapper extends JFrame {

    private DefaultListModel modelistMotion = new DefaultListModel();
    private DefaultListModel modelLight = new DefaultListModel();
    private DefaultListModel modelColor= new DefaultListModel();
    private DefaultListModel modelGestures= new DefaultListModel();

    private JList listMotion;
    private JList listLight;
    private JList listColor;
    private JList listGestures;

    private JScrollPane barMotion;
    private JScrollPane barLight;
    private JScrollPane barColor;
    private JScrollPane barGestures;

    private LightMaterial material;
    private Window windowother;
    private JPanel buttonsPanel = new JPanel();
    private JButton buttonAdd = new JButton("ADD");

    private JComboBox listeMatos;
    private String[] tableauMatos = new String[]{"MINIBEAM", "MINIWASH", "STROBOSCOPE"};


    public AnimMapper(Window window){

        super();
        this.windowother = window;
        this.setSize(400,400);
        this.material = new LightMaterialMiniWash(windowother.getRoutine(),0);
        this.getContentPane().setLayout(new GridLayout(1,3));

        listMotion = new JList(modelistMotion);
        listLight = new JList(modelLight);
        listColor = new JList(modelColor);
        listGestures = new JList(modelGestures);

        barMotion = new JScrollPane(listMotion);
        barLight = new JScrollPane(listLight);
        barColor = new JScrollPane(listColor);

        barGestures = new JScrollPane(listGestures);

        this.getContentPane().add(barMotion);
        this.getContentPane().add(barLight);
        this.getContentPane().add(barColor);
        this.getContentPane().add(barGestures);
        this.getContentPane().add(buttonsPanel);

        buttonAdd.setContentAreaFilled(false);

        listeMatos = new JComboBox(tableauMatos);

        buttonsPanel.add(listeMatos);
        buttonsPanel.add(buttonAdd);
        buttonAdd.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    addAnimationMapping();
                }
            });
        buttonAdd.setMnemonic(KeyEvent.VK_ENTER);
        listeMatos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setListsContent();
            }
        });

        this.setListsContent();
        this.setVisible(true);

    }



    public void setListsContent(){
        DBmanager manager = new DBmanager(this.windowother.getRoutine());
        String matosTypeSelected = tableauMatos[listeMatos.getSelectedIndex()];

        ArrayList<ArrayList<String>> animLists = null;
        if (matosTypeSelected == "MINIWASH" || matosTypeSelected == "MINIBEAM"){
            animLists = manager.getListOfAnimations(new LightMaterialMiniWash(windowother.getRoutine(),0));
        }

        else if (matosTypeSelected == "STROBOSCOPE"){
            animLists = manager.getListOfAnimations(new LightMaterialStrobo(windowother.getRoutine(),0));
        }

        String txtToAdd ="";

        modelistMotion.removeAllElements();
        modelLight.removeAllElements();
        modelColor.removeAllElements();
        modelGestures.removeAllElements();

        if (matosTypeSelected == "STROBOSCOPE"){
            for (String anim : animLists.get(0)){
                System.out.println(anim);
                txtToAdd = anim.split("\\.")[0];
                modelLight.addElement(txtToAdd);
            }
        }

        else{
            for (String anim : animLists.get(0)){
                System.out.println(anim);
                txtToAdd = anim.split("\\.")[0];
                int number=Integer.valueOf(txtToAdd);
                String name=manager.getName(number, "motion");
                modelistMotion.addElement(name);
            }
            for (String anim : animLists.get(1)){

                txtToAdd = anim.split("\\.")[0];
                int number=Integer.valueOf(txtToAdd);
                String name=manager.getName(number, "light");
                modelLight.addElement(name);
            }
            for (String anim : animLists.get(2)){
                txtToAdd = anim.split("\\.")[0];
                int number=Integer.valueOf(txtToAdd);
                String name=manager.getName(number, "color");
                modelColor.addElement(name);
            }
        }

            //TODO : List of gestures is set for 3 ML moves and 3 fingers, to be changed
        for (int moveId = 0; moveId < 4; moveId++){
            for (int fingersId = 0; fingersId < 8; fingersId++){
                boolean[] fingers = Gesture.getFingersFromFingersId(fingersId);
                String toDisplay = "" + String.valueOf(moveId)+ "  ";
                for (boolean finger : fingers){
                    if (finger){
                        toDisplay+= "I";
                    }
                    else{
                        toDisplay+= "o";
                    }
                    toDisplay+=" ";
                }
                modelGestures.addElement(toDisplay);
            }
        }
    }



    public void addAnimationMapping(){


        int motionInt = listMotion.getSelectedIndex();
        int lightInt = listLight.getSelectedIndex();
        int colorInt = listColor.getSelectedIndex();
        int gestureInList = listGestures.getSelectedIndex();

        DBmanager dbmanager = new DBmanager(windowother.getRoutine());

        //checking what material type is selected by the user
        String matosTypeSelected = tableauMatos[listeMatos.getSelectedIndex()];
        if(matosTypeSelected == "MINIBEAM"){
            material = new LightMaterialMiniBeam(windowother.getRoutine(),0);
        }
        else if (matosTypeSelected == "MINIWASH"){
            material = new LightMaterialMiniWash(windowother.getRoutine(), 0);
        }
        else if (matosTypeSelected == "STROBOSCOPE"){
            material = new LightMaterialStrobo(windowother.getRoutine(), 0);
        }

        System.out.println(lightInt);
        if ( (lightInt!= -1 )&& ( matosTypeSelected == "STROBOSCOPE" || (motionInt != -1 && colorInt != -1 && gestureInList != -1 ) ) ){

            int gestureEncodedinMapper= 100* (gestureInList/8) + gestureInList%8;
            System.out.print("Found gesture encoded : " + gestureEncodedinMapper);
            dbmanager.addMapping(motionInt,lightInt, colorInt, gestureEncodedinMapper, material);
            JOptionPane.showMessageDialog(this,"Animation ajoutée");
        }
        else{
            JOptionPane.showMessageDialog (this, "Veuillez sélectionnez un geste et une animation complète","Erreur", JOptionPane.ERROR_MESSAGE);
        }




    }




}
