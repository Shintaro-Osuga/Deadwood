import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;

public class Deadwood {

    private static Board board;
    private static Player[] players;
    private static int playerCount;
    private static Bank bank;
    private static View view;
    private static String[] playerNames = {"b", "c", "g", "o", "p", "r", "v", "y"};;

    public static void main(String args[]) {
        String boardFileName = "board.xml";
        String cardFileName = "cards.xml";
        board = new Board(boardFileName, cardFileName);
        bank = new Bank();
        view = new View();
        
        view.numPlayer();
        view.makeVisible();
        //initialize players and check for count related cases
        while(view.getNumPlayers() == 0)
        {    
            System.out.print("");
        }
        playerCount = view.getNumPlayers();
        view.unload();

        //player names used to be here

        int initialRank = playerCount < 7 ? 1 : 2;
        int initialCredits = playerCount == 5 ? 2 : (playerCount == 6 ? 4 : 0); 
        int daysToPlay = playerCount > 3 ? 4 : 3;

        players = new Player[playerCount];

        
        for(int i = 0; i < playerCount; i++) {
            players[i] = new Player(initialRank, playerNames[i], initialCredits, playerNames[i], board);
        }
        
        /* MAIN CONTROL LOOP FOR DEADWOOD */
        while(daysToPlay > 0) {
            startNewDay();
            while(board.dayIsActive()) {
                for(int p = 0; p < playerCount; p++) {
                    playerTurn(players[p]);
                    if(!board.dayIsActive())
                    {
                        break;
                    }
                }
            }
            daysToPlay--;
        }
        endGame();
    }

    /* Communicate to view for player input, run through the valid options 
     * that the player has on a given turn
     */
    private static void playerTurn(Player player) {
        String[] input = {""};
        player.resetTakenTurn();
        player.resetMoved();
        view.printOpeningSentence(player.getName());
        
        // JFrame frame = updateBoard(players);
        view.initBoard();
        // setCards();
        view.makeVisible();
        boolean playing = true;
        Boolean button = true;
        boolean error = true;
        // playing && !player.getTurnTaken() || 
        updateBoard(players);
        view.displayPlayerTurn("It is " + player.getName()+ "'s Turn");
        while(!view.isTurnEnded(player.getName()) && !player.getTurnTaken() ) {
            
            view.printBeginningOfRound();
            // input = view.promptPlayerForInput();
            int[] mouseLocations = view.isMouseClicked();

            while(mouseLocations == null){
                mouseLocations = view.isMouseClicked();

            }

            System.out.println("X: " + mouseLocations[0] + " Y: " + mouseLocations[1]);
            String roomName = board.checkRooms(mouseLocations[0],mouseLocations[1]);
            if(roomName != null){
                move(player,roomName);
                error = false;
                view.displayOutput("Move Succesfull");
            }
            
            
            
            String roleName = board.checkRoles(mouseLocations[0],mouseLocations[1], player.getRoom().getRoomName());
            System.out.println(roleName);
            if(roleName != null){
                takeRole(player,roleName);
                error = false;
                view.displayOutput("Role taken Succesfully");
            } 

            roomName = player.getRoom().getRoomName();
            if(!roomName.equals("trailer") && !roomName.equals("office")) {
                if(((FilmSet)player.getRoom()).checkShotCounters(mouseLocations[0], mouseLocations[1])){
                    act(player);
                    error = false;
                    view.displayOutput("Move Succesfull");
                }
                else if(((FilmSet)player.getRoom()).checkRehearse(mouseLocations[0], mouseLocations[1])){

                    rehearse(player);
                    error = false;
                    view.displayOutput("Move Succesfull");
                }
            
    	    
            }else if(roomName.equals("office")) {
                int[] upgradeInfo = board.checkUpgrade(mouseLocations[0], mouseLocations[1]);
                if(upgradeInfo != null) {
                    upgrade(player, upgradeInfo[0], upgradeInfo[1] == 1 ? true : false);
                    error = false;
                }
            }
            if(error){
                System.out.println("ERROR");
                view.displayOutput("No input detected!");
            }
            //System.out.println(temp);
            
            
            // switch(input[0]) {
            //     case "move":
            //         move(player, input[1]);
            //         break;
            //     case "take":
            //         takeRole(player, input[1]);
            //         break;
            //     case "act":
            //         act(player);
            //         break;
            //     case "rehearse":
            //         rehearse(player);
            //         break;
            //     case "upgrade":
            //         upgrade(player, input[1]);
            //         break;
            //     case "who":
            //         displayPlayerInformation(player);
            //         break;
            //     case "where":
            //         displayPlayerLocation(player);
            //         break;
            //     case "all":
            //         displayAllPlayerLocations();
            //         break;
            //     case "neighbors":
            //         displayPlayerNeighbors(player);
            //         break;
            //     case "roles":
            //         displayerPlayerRoles(player);
            //         break;
            //     case "end":
            //         if(player.getCurrentRole() != null && !player.getTurnTaken())
            //             view.printMustWork();
            //         else
            //             playing = false;
            //         break;
                
            //     default:
            //         view.invalidInput();
            //         break;
            // }
            // view.unload();
            // frame = updateBoard(players);
        }
    }

    //initiate final payouts then display winner!
    private static void endGame() {
        int[] finalScores = new int[playerCount];
        int indexOfMax = 0;
        String winnerName = "";

        for(int i = 0; i < playerCount; i++) {
            finalScores[i] = players[i].getCredits() + players[i].getDollars() + players[i].getRank() * 5;
        }

        for(int i = 0; i < playerCount; i++) {
            indexOfMax = finalScores[i] == Math.max(finalScores[indexOfMax], finalScores[i]) ? i : indexOfMax;
        }
        winnerName = players[indexOfMax].getName();

        for(int i = 0; i < playerCount; i++) {
            view.printFinalScore(players[i].getName(), finalScores[i]);
        }
        view.printWinner(winnerName, finalScores[indexOfMax]);

        view.closeScanner();
    }
    private static void checkRoomCollision(){
        
    }
    
    private static void createBoard()
    {
        updateBoard(players);
    }

    private static void updateBoard(Player [] players){
        String [] colNames = {"Player","Dollars","Credits","Practice Chips"};
        //player dollars credits practice chips
        Object[][] data= new Object[players.length+1][4];
        data[0] = colNames;
        for(int i=0; i<players.length; i++){
            data[i+1][0] = players[i].getName();
            data[i+1][1] = players[i].getDollars();
            data[i+1][2] = players[i].getCredits();
            data[i+1][3] = players[i].getPracticeChips();

        }
        
        JTable table = new JTable(data, colNames);
        view.displayTable(table);
        return;
    }

    private static void createBoardGUI()
    {
        view.initBoard();
        setBackCards();
        setPlayers();
        // updateBoard(players);
        view.setPane();
        view.makeVisible();
    }

    private static void setPlayers()
    {
        int[] area = {941,148,194,201};
        for(int i = 0; i < playerCount; i++)
        {
            if(i%2==0)
            {
                area[1] = area[1] + 40;
                area[0] = 991;
            }
            view.setPlayer(players[i].getColor(), players[i].getRank(), area);
            area[0] = area[0] + 45;
        }
    }

    private static void setBackCards()
    {
        for(FilmSet f : board.getFilmSets())
        {
            view.addBackCard(f.getArea());
        }
    }

    private static void flipCard(Player p)
    {
        view.flipCard(((FilmSet)p.getRoom()).getCardOnSet().getImgName(), p.getRoom().getArea());
        view.setPane();
        view.makeVisible();
    }

    private static void getPlayerLocation(Player[] player){

    }

    /////////
    // methods made to comunicate between the controller and view, while enacting player actions
    private static void displayPlayerInformation(Player p) {
        String role = p.getCurrentRole() != null ? p.getCurrentRole().getName() : null;
        view.printPlayerInformation(p.getName(), p.getDollars(), p.getCredits(), p.getRank(), role, p.getRoom().getRoomName(), p.getPracticeChips());
    }

    private static void displayPlayerLocation(Player p) {
        view.printPlayersLocation(p.getName(), p.getRoom().getRoomName());
    }

    private static void displayAllPlayerLocations() {
        for(int i = 0; i < players.length; i++){
            view.printPlayersLocation(players[i].getName(), players[i].getRoom().getRoomName());
        }
    }

    private static void displayPlayerNeighbors(Player player){
        view.printPlayerNeighbors(player.getRoom().getRoom());
    }
    
    private static void displayerPlayerRoles(Player player) {
        if(player.getRoom().getRoomName().toLowerCase().equals("trailer") || player.getRoom().getRoomName().toLowerCase().equals("casting office")) {
            System.out.println("null");
        }
        else {
            if(!player.getRoom().getRoomName().equals("Trailers") && !player.getRoom().getRoomName().equals("Casting Office") && ((FilmSet)player.getRoom()).getCardOnSet() != null) {
                List<String> onCardRolesNames = new ArrayList<String>();
                for(Role r : player.getBoard().getOnCardRolesForRoom(player.getRoom().getRoomName()))
                    onCardRolesNames.add(r.getName());

                List<Integer> onCardRolesMinRanks = new ArrayList<Integer>();
                for(Role r : player.getBoard().getOnCardRolesForRoom(player.getRoom().getRoomName()))
                    onCardRolesMinRanks.add(r.getMinRank());


                List<String> offCardRolesNames = new ArrayList<String>();
                for(Role r : player.getBoard().getOffCardRolesForRoom(player.getRoom().getRoomName()))
                    offCardRolesNames.add(r.getName());

                List<Integer> offCardRolesMinRanks = new ArrayList<Integer>();
                for(Role r : player.getBoard().getOffCardRolesForRoom(player.getRoom().getRoomName()))
                    offCardRolesMinRanks.add(r.getMinRank());

                view.printPlayerRoles(offCardRolesNames, offCardRolesMinRanks, onCardRolesNames, onCardRolesMinRanks);
            }
            else
                view.printNoRoles();
        }
    }

    private static void move(Player p, String location) {
        if(p.move(location)) {
            view.moveValid(p.getName(), location);
            int[] area = {p.getRoom().getArea()[0], p.getRoom().getArea()[1], p.getRoom().getArea()[2], p.getRoom().getArea()[3]};
            area[0] = area[0]+40*(numPlayersInRoom(p.getRoom())-1)-80;
            area[1] = area[1]+80;
            view.movePlayer(p.getColor(), p.getRank(), area);
            if(!location.equals("trailer") && !location.equals("office"))
            {
                flipCard(p);
            }
        }
        else if(p.getMoved())
        {
            view.printAlreadyMoved(p.getName());
        }else{
            view.moveInvalid(location);
            view.displayOutput("Move is invalid");
        }
    }

    public static int numPlayersInRoom(Room room)
    {
        int counter = 0;
        for(Player p : players)
        {
            if(p.getRoom().equals(room))
            {
                counter++;
            }
        }
        System.out.println(counter);
        return counter;
    }

    private static void takeRole(Player p, String role) {
        if(p.takeRole(role)) {
            view.printRole(p.getName());
        }
        else
            view.printFailedRole(p.getName());
    }

    private static void act(Player p) {
        if(p.getTurnTaken() == true)
        {
            view.printDoneAction(p.getName());
            return;
        }

        String payout = p.act();

        if(payout != null && !payout.equals("1 dollar")) {
            view.printActing(p.getName(), payout);
            view.printShotCounter(p.getRoom().getRoomName(), ((FilmSet)p.getRoom()).getShotCounters());
            view.setShotCounter(((FilmSet)p.getRoom()).getShotCounterArea());
            if(((FilmSet)p.getRoom()).getShotCounters() == 0) {
                sceneCompleted((FilmSet)p.getRoom());
                view.printSceneComplete(p.getRoom().getRoomName());
            }
        }
        else{
            view.displayOutput("Failed Acting");
            view.printFailedActing(p.getName(), payout);
        }
    }

    private static void rehearse(Player p)
    {
        if(p.hasRole())
        {
            if(p.rehearse())
            {
                view.printRehearse(p.getName());
            }else{
                view.displayOutput("Can't rehearse");
                view.printFailedRehearse(p.getName());
            }   
        }else{
            view.displayOutput("Failed rehearse");
            view.printFailedRehearseNoRole(p.getName());
        }
    }

    private static void upgrade(Player p, int rank, boolean payedWithDollars) {
        if(p.upgradable(rank, bank.getUpgradePrice(rank, payedWithDollars), payedWithDollars)) {
            if(p.upgrade(rank, bank.getUpgradePrice(rank, payedWithDollars), payedWithDollars)) {
                //view.printUpgrade(p.getName(), rank);
                //UPDATE PLAYER DICE
            }
            else{
                //view.printFailedUpgrade(p.getName());
                //CONSOLE LOG?
            }
        }
        else {
            //view.printUnupgradable(p.getName());
            //CONSOLE LOG?
        }
    }

    /////////

    //Reset the board and reset each player
    private static void startNewDay() {
        board.resetBoard();
        view.printNewDay();
        view.clearMap();
        for(int i = 0; i < playerCount; i++) {
            players[i].resetPlayer();
        }
        createBoardGUI();
    }

    public static void sceneCompleted(FilmSet filmSet) {
        bank.payStarBonus(filmSet.getCardOnSet());
        for(Role r : filmSet.getCardOnSet().getRoles()) {
            if(r.getCurrentPlayer() != null)
                r.getCurrentPlayer().removeRole();
        }

        if(filmSet.getCardOnSet() != null) {
            for(Role r : filmSet.getOffCardRoles()) {
                if(r.getCurrentPlayer() != null) {
                    bank.payExtraBonus(r);
                    r.getCurrentPlayer().removeRole();
                }
            }
        }

        view.removeCard(filmSet.getArea());
        filmSet.setCardOnSet(null);
    }
}