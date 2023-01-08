import java.util.Scanner;

//For GUI version
import javax.swing.plaf.basic.BasicInternalFrameTitlePane.SystemMenuBar;
import javax.swing.table.DefaultTableModel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeListener;
import javax.swing.event.MouseInputAdapter;


import java.util.*;

public class View extends JPanel { 

    // private JLabel backCard = new JLabel(new ImageIcon("Images/CardBack-small.jpg"));

    private Scanner scan;
    private JFrame frame = new JFrame("DeadWood");
    private JLayeredPane layers = new JLayeredPane();
    private JPanel panel = new JPanel();

    private Map<int[], JLabel> areaToJLabel;
    private Map<String, JLabel> colorToJLabel;

    private JLabel layer1[] = new JLabel[1];
    private JLabel layer2[] = new JLabel[1];
    private JLabel layer3[] = new JLabel[0];
    private JLabel layer4[] = new JLabel[0];
    private JLabel cards[] = new JLabel[2];
    
    private int numPlayer = 0;
    public boolean mouseClicked = true;
    public boolean turnEnded = false;
    //public JTextField output;
    // Listener listen = new Listener();
    
    static JSlider slider;

    static JLabel label;

    public View() {
        scan = new Scanner(System.in);
        areaToJLabel = new HashMap<int[], JLabel>();
        colorToJLabel = new HashMap<String, JLabel>();
        // frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frameInit();
    }
    
    private void frameInit(){
        // frame=new JFrame("DeadWood");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1500, 1500);    
        frame.setLayout(null);
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1){
                    mouseClicked = true;
                }
            }
        });
        this.frame = frame;
        

        // frame.setVisible(true);

    }

    public int getNumPlayers()
    {
        return numPlayer;
    }
    private JFrame sliderFrame(JFrame frame)
    {

        // frame = new JFrame("frame");

        label = new JLabel();

        JPanel panel = new JPanel();

        slider = new JSlider(2,8,2);

        slider.setPaintTrack(true);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);

        slider.setMajorTickSpacing(2);
        slider.setMinorTickSpacing(1);

        panel.add(slider);
        panel.add(label);

        frame.add(panel);

        label.setText("-- Player Count --");

        frame.setSize(300,300);

        return frame;
        // frame.show();
    }
    
    public void initBoard()
    {
        JLabel imgLabel = new JLabel(new ImageIcon("Images/board.jpg"));
        imgLabel.setBounds(0,0,1200,900);
        layers.add(imgLabel, JLayeredPane.DEFAULT_LAYER);
        JButton endGame = new JButton("End Turn");
        endGame.setBounds(1225, 75, 100, 50);
        
        endGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                turnEnded = true;
            }
        });
        
        //output = textField;
        //layers.add(output);
        frame.add(endGame);

        // JLabel backCard = new JLabel(new ImageIcon("Images/CardBack-small.jpg"));
        // backCard.setBounds(21,69,205,115);
        // layers.add(backCard, JLayeredPane.MODAL_LAYER);

        // frame.pack();
        
        // frame.setVisible(true);
        // frame.setLayout(null);
    }
    public boolean isTurnEnded(String name){
        if(turnEnded){
            turnEnded = false;
            return true;
        }
        return false;
        
    }
    public void displayOutput(String message){
        // layers.remove(output);
        // JTextField jt = new JTextField(message);
        // jt.setBounds(1225, 200, 175,30);
        // output = jt;
        JTextField textField = new JTextField(message);
        textField.setBounds(1225, 200, 175,30);
        textField.setText(message);
        layers.add(textField, JLayeredPane.POPUP_LAYER);
        layers.setVisible(true);
        frame.add(textField);
        frame.setVisible(true);
    }
    public void displayPlayerTurn(String message){
        JTextField textField = new JTextField(message);
        textField.setBounds(1225, 160, 175,30);
        textField.setText(message);
        layers.add(textField, JLayeredPane.POPUP_LAYER);
        layers.setVisible(true);
        frame.add(textField);
        frame.setVisible(true);
    }

    public void displayTable(JTable table){
        
        //    f.add(textField);
        table.setBounds(1225,500,500,200);
        //JScrollPane sp = new JScrollPane(table);
        // layers.add(sp);
        layers.add(table, JLayeredPane.POPUP_LAYER);
        layers.setVisible(true);
        frame.add(table, BorderLayout.SOUTH);
        frame.setVisible(true);
        // frame.add(table, BorderLayout.SOUTH);
        // frame.setLayout(null);
        // frame.pack();
        // frame.setVisible(true);

        // System.out.println(mouseX + " " + mouseY);
        /*
        JPanel panel = new JPanel();
        frame.add(panel);
        panel.addMouseListener(new MouseAdapter() {
        public void mousePressed(MouseEvent e) {
        System.out.println(e.getX() + "," + e.getY());
            }
        });
        
*/
        return ;
    }


    public int numPlayer(){
        //JTextField textField = new JTextField("Enter Number of Players: ");
        //textField.setBounds(500, 300, 175,30);
        JButton button2 = new JButton("2 Players");
        JButton button3 = new JButton("3 Players");
        JButton button4 = new JButton("4 Players");
        JButton button5 = new JButton("5 Players");
        JButton button6 = new JButton("6 Players");
        JButton button7 = new JButton("7 Players");
        JButton button8 = new JButton("8 Players");

        button2.setBounds(500, 625, 90, 50);
        button3.setBounds(500, 550, 90, 50);
        button4.setBounds(500, 475, 90, 50);
        button5.setBounds(500, 400, 90, 50);
        button6.setBounds(500, 325, 90, 50);
        button7.setBounds(500, 250, 90, 50);
        button8.setBounds(500, 175, 90, 50);

        //frame.add(textField);
        frame.add(button2);
        frame.add(button3);
        frame.add(button4);
        frame.add(button5);
        frame.add(button6);
        frame.add(button7);
        frame.add(button8);
        frame.pack();
        // frame.setVisible(true);
        // int ret = -1;

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayer = 2;
                System.out.println(numPlayer);
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayer = 3;
                System.out.println(numPlayer);
            }
        });

        button4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayer = 4;
                System.out.println(numPlayer);
            }
        });

        button5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayer = 5;
                System.out.println(numPlayer);
            }
        });
        
        button6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayer = 6;
                System.out.println(numPlayer);
            }
        });

        button7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayer = 7;
                System.out.println(numPlayer);
            }
        });
        
        button8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numPlayer = 8;
                System.out.println(numPlayer);
            }
        });

        return numPlayer;
    }

    public void unload()
    {
        frame.getContentPane().removeAll();
        frame.repaint();
    }

    public void addBackCard(int[] area)
    {
        JLabel backCard = new JLabel(new ImageIcon("Images/CardBack-small.jpg"));
        backCard.setBounds(area[0],area[1],area[3],area[2]);
        areaToJLabel.put(area, backCard);
        layers.add(backCard, JLayeredPane.MODAL_LAYER);
    }

    public void flipCard(String img, int[] area)
    {
        JLabel newCard = new JLabel(new ImageIcon("Images/cards/"+img));
        newCard.setBounds(area[0],area[1],area[3],area[2]);

        layers.remove(areaToJLabel.get(area));
        areaToJLabel.replace(area, newCard);
        
        layers.add(newCard, JLayeredPane.MODAL_LAYER);
    }

    public void removeCard(int[] area)
    {
        layers.remove(areaToJLabel.get(area));
        areaToJLabel.remove(area);
    }

    public void clearMap()
    {
        if(!areaToJLabel.isEmpty())
        {
            for (Map.Entry<int[], JLabel> entry : areaToJLabel.entrySet()) {
                int[] key = entry.getKey();
                layers.remove(areaToJLabel.get(key));
            }
            areaToJLabel.clear();
        }
    }

    public void addRole(int[] area)
    {
        //set bounds for where player can click
    }

    public void makeVisible()
    {
        frame.setVisible(true);
    }

    public void setPane()
    {
        frame.setContentPane(layers);
    }

    public void setShotCounter(int[] area)
    {
        JLabel shotCounter = new JLabel(new ImageIcon("Images/shot.png"));
        shotCounter.setBounds(area[0], area[1], area[3], area[2]);
        layers.add(shotCounter, JLayeredPane.POPUP_LAYER);
        areaToJLabel.put(area, shotCounter);
    }

    public void removeShotCounter(int[] area)
    {
        layers.remove(areaToJLabel.get(area));
        areaToJLabel.remove(area);
    }

    public void setPlayer(String color, int rank, int[] area)
    {
        String uri = "Images/dice/"+color+String.valueOf(rank)+".png";
        JLabel player = new JLabel(new ImageIcon(uri));
        player.setBounds(area[0],area[1],area[3],area[2]);
        layers.add(player, JLayeredPane.POPUP_LAYER);
        colorToJLabel.put(color, player);
    }

    public void movePlayer(String color, int rank, int[] area)
    {
        String uri = "Images/dice/"+color+String.valueOf(rank)+".png";
        JLabel player = new JLabel(new ImageIcon(uri));
        player.setBounds(area[0],area[1],area[3],area[2]);

        layers.remove(colorToJLabel.get(color));
        colorToJLabel.remove(color);
        layers.add(player, JLayeredPane.POPUP_LAYER);
        colorToJLabel.put(color, player);
    }
    public int[] isMouseClicked(){
        
        // frame.add(panel);
    //System.out.println("MOUSECLICKED IS " + mouseClicked);
        if(mouseClicked){
            mouseClicked = false;
            return getMouseLocation();
        }
        return null;
    }



    public int[] getMouseLocation(){
        
        int[] mouseLocations = new int[2];
        int mouseX = MouseInfo.getPointerInfo().getLocation().x - frame.getLocationOnScreen().x;
        int mouseY = MouseInfo.getPointerInfo().getLocation().y - frame.getLocationOnScreen().y;
        mouseLocations[0] = mouseX;
        mouseLocations[1] = mouseY;

        return mouseLocations;

    }

    public void mouse()
    {
        JLabel label = new JLabel();
        panel.add(label);
        panel.setLayout(null);
        frame.add(panel);
        panel.addMouseListener(new MouseInputAdapter() {
            public void mouseClicked(MouseEvent e)
            {
                int x = e.getX();
                int y = e.getY();
                label.setText("X: "+x+" \t Y: "+y); // this sets the JLabel's text
                label.setBounds(x, y, label.getText().length()*2, 20);
                if(e.getButton() == MouseEvent.BUTTON1)
                {
                    mouseClicked = true;
                    System.out.println("TRUE");
                }
            }
        });
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(200, 200);
        frame.setVisible(true);
    }

    public void printFinalScore(String playerName, int score) {
        System.out.println(playerName + "'s final score is " + score);
    }

    public void printWinner(String playerName, int score) {
        System.out.println("\n" + playerName + " is the winner with a score of " + score + "!");
    }

    public int promptForPlayerCount() {
        System.out.println("How many players will be playing Deadwood?");
        int playerCount = Integer.parseInt(scan.nextLine());
        while(playerCount < 1 || playerCount > 8) {
            System.out.println("Please enter an number between 2 and 8");
            playerCount = Integer.parseInt(scan.nextLine());
        }
        return playerCount;
    }
    
    public String[] promptPlayerForInput() {
        String s = scan.nextLine();
        return parseInput(s);
    }

    public String promptForUpgradeType() {
        System.out.println("Would you like to pay with dollars or credits?");
        return scan.nextLine();
    }
   
    public void printPlayerInformation(String name,int dollars, int credits, int rank, String currentRole, String currentRoom, int practiceChips){
        System.out.println("It is " + name + "'s, turn.");
        System.out.println("They have " + dollars +" Dollars.");
        System.out.println("They have " + credits +" Credits.");
        System.out.println("They have " + practiceChips +" Practice chips");
        System.out.println("They are rank " + rank);
        System.out.println("Their current role is " + currentRole);
        System.out.println("They are currently in " + currentRoom);
    }

    public void printPlayersLocation(String name, String location) {
        System.out.println(name + " is at " + location);
    }

    public void printPlayerNeighbors(List<String> neighbors) {
        for(int i = 0; i < neighbors.size()-1; i++) {
            System.out.print(neighbors.get(i) + ", ");
        }
        System.out.println(neighbors.get(neighbors.size()-1));
    }

    public void printPlayerRoles(List<String> offCardRolesNames, List<Integer> offCardRolesMinRanks, List<String> onCardRolesNames, List<Integer> onCardRolesMinRanks) {
        for(int i = 0; i < offCardRolesNames.size(); i++) {
            System.out.println(offCardRolesNames.get(i) + ": " + offCardRolesMinRanks.get(i));
        }

        for(int i = 0; i < onCardRolesNames.size(); i++) {
            System.out.println(onCardRolesNames.get(i) + ": " + onCardRolesMinRanks.get(i));
        }
    }

    public void moveValid(String name, String location) {
        System.out.println(name + " has moved to " + location);
    }
    
    public void moveInvalid(String location) {
        System.out.println("You are not adjacent to " + location + "!");
    }

    public void invalidInput() {
        System.out.println("Invalid input");
    }

    public void printPartInformation(String name, String job) {
        System.out.println(name + " Is working at "+job);

    }

    public void printRehearse(String name) {
        System.out.println("Success! " + name + " is rehearsing.");
    }
    public void printFailedRehearse(String name) {
        System.out.println("FAILURE! " + name + " has too many practice chips!");
    }

    public void printFailedRehearseNoRole(String name) {
        System.out.println("FAILURE! " + name + " Doesn't have a role!");
    }
    
    public void printActing(String name, String payout) {
        System.out.println("Success! " + name + " earned " + payout);
    }
    public void printFailedActing(String name, String payout) {
        System.out.println("FAILURE! " + name + " earned " + payout);
    }

    public void printRole(String name) {
        System.out.println("Success! " + name + " has taken a role");
    }
    public void printFailedRole(String name) {
        System.out.println("FAILURE! " + name + " has not taken a role");
    }

    public void printUpgrade(String name, int rank) {
        System.out.println("Success! " + name + " has upgraded to rank " + rank);
    }

    public void printFailedUpgrade(String name) {
        System.out.println("FAILURE! " + name + " has insufficient funds");
    }

    public void printUnupgradable(String name) {
        System.out.println("FAILURE! " + name + " is not in the casting office");
    }

    public void printAlreadyMoved(String name) {
        System.out.println("FAILURE! " + name + " has already moved");
    }

    public void printDoneAction(String name) {
        System.out.println("FAILURE! " + name + " has already done that action");
    }

    public void printShotCounter(String roomName, int shotCounters) {
        System.out.println(roomName + " currently has " + shotCounters + " shot counters");
    }

    public void printSceneComplete(String roomName) {
        System.out.println("The scene in " + roomName + " has finished");
        System.out.println("Bonuses have been paid");
    }

    public void printBeginningOfRound() {
        System.out.println("What would you like to do?");
        System.out.println("move, take, act, rehearse, upgrade + (desired rank), who, where, neighbors, roles, all, end");
    }
    public void printOpeningSentence(String name) {
        System.out.println("\nIt is " + name + "'s turn");
    }

    public void printNewDay(){
        System.out.println("--- A New Day Has Begun ---");
    }

    public void printNoRoles() {
        System.out.println("There are no available roles");
    }
    
    public void endTurn(String name) {
        System.out.println(name + "'s turn has ended");
    }

    public void printMustWork() {
        System.out.println("You are currently working, you have to act or rehearse!");
    }

    /* This method parses input into two strings, the first string is the first word of the
     * string, the second is the rest of the string (i.e. move Train Station -> [move, Train Station])
     */
    private String[] parseInput(String input) {
        String[] output = new String[2];
        
        if(input.equals("")){
            output[0] = "";
            return output;
        }

        int i = 0;
        while(i < input.length()-1 && input.charAt(i) != ' ') {
            i++;
        }

        if(i == input.length()-1) {
            output[0] = input.substring(0, i+1);
        }
        else {
            output[0] = input.substring(0, i);
            output[1] = input.substring(i + 1, input.length());
        }
        return output;
    }

    //Lets the controller close the scanner at the end of the game
    public void closeScanner() {
        scan.close();
    }
}   
 