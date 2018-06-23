package tests;
import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import org.junit.Test;

import code.Board3Player;
import code.Location;
import code.Person;

public class Tests_3P {

      //Test if the method "read" in the Board Class
    @Test
      public void readCodeNameTest() {
          Board3Player b = new Board3Player();
          ArrayList<String> s = new ArrayList<String>();
          b.read("readCodeTest.txt");//read file using the method
          
          //creating a list to compare to the list generated by the method.
          for(int i=0; i<25; i++) {
              s.add("hi");
          }
          
          assertEquals(s, b.get_words());
      }
  //-----------------------------------------------------------------------------------------
      
      //Test for the method "codename" in the Board class
      @Test
      public void codenameTest() {
          Board3Player b = new Board3Player();
          List <String> s1 = new ArrayList<>();
          List <String> s2 = new ArrayList<>();
          //boolean t = false;
          //HashMap<String, Integer> dup = new HashMap<>();
          
          //s1 will be the random 25 codenames
          b.setCodenames(new ArrayList<String>());
          b.codename("codenameTest.txt");
          s1 = b.getCodenames();
          //s2 will be a list of codenames that are in order
          try{
              for(String line : Files.readAllLines(Paths.get("codenameTest.txt"))){
                  s2.add(line);
              }
          } catch (IOException ex){
              ex.printStackTrace();
          }
          
          //compare if the two Lists are equal; if it is not, then the s1 is shuffled
          //for(int i=0; i<25; i++) {
          //    dup.put(s1.get(i), 0);//putting the list into a HashMap to ensure no duplications
          //    if(!((s1.get(i)).equals(s2.get(i)))){
          //        t=true;
          //    }else {
          //        t=false;
          //    }
          assertFalse("Codenames list and shuffled codenames list not the same? aka shuffled?", s1.equals(s2));
          for (String codename : s2) {
            assertTrue("Have the same codenames?",s1.remove(codename));
          }          
          assertTrue("No duplicates?",s1.isEmpty());
          //assertEquals(25, s1.size());//checks if there are 25 codenames generated
          //assertEquals("Duplicates",true,dup.size()==s1.size());//check for duplications
          //assertEquals(true, t);//checks if it is shuffled
      }
  //-----------------------------------------------------------------------------------------
      
      //Test for the method "clue" in the Board class
      @Test
      public void clueTest() {
          
          Board3Player b = new Board3Player();
          b.createLocations();
          
          Location[][] l = b.getLocations();
          l[0][0].setVisible(true);//revealed codename
          String visible = l[0][1].getCodename();
          l[0][1].setVisible(false);//not revealed codename
          String notVisible = l[0][0].getCodename();
          
          //if the clue is a codename but it is not revealed, it is an illegal clue.
          assertEquals("Clue is a codename but not visible",false,b.clue(visible));
          //if the clue is more than one word, it is an illegal clue.
          assertEquals("Clue is more than one word",false,b.clue("poster lamp"));
          //if the clue is a codename and the word is revealed, it is a legal clue.
          assertEquals("Clue is a codename and it is visible",true,b.clue(notVisible));
          //if the clue is not a codename, it is a legal clue.
          assertEquals("Clue is not a codename",true,b.clue("cse116"));
          
      }
  //-----------------------------------------------------------------------------------------
      
        //Test the method "shuffle_Agents" in the Board class
        @Test
        public void shuffle_AgentsTest() {
          Board3Player b = new Board3Player();
          b.init();//Generates 25 agents(not shuffled)
          @SuppressWarnings("unchecked") //Added to remove "unchecked cast warning" Complier dosent know what kind of object "b.get_agents()" is so it gives warning 
          List<Person> players = (List<Person>) b.get_agents().clone();//Clone of the list of agents before it is shuffled
          List<Person> shuffled_players = new ArrayList<Person>();
          // 1) Set up an original list, player, of different player types: 6 Red Agents,
          //      5 Blue Agents, 5 Green Agents, 7 Innocent Bystanders, and 2 Assassins
          // 2) Perform the method being tested, AssignAgents. Store the result of this, a shuffled list, into the variable shuffled_players
          shuffled_players = b.shuffle_Agents();
          // 3) Check that the shuffled list is not equal to the original list. If they are, that means the players were not shuffled.
          assertFalse("Players list same as shuffledPlayers list?", players.equals(shuffled_players));
          // 4) Check that the shuffled list contains the same # of elements as original list.
          //    This was done in a for-each loop. For each element in the original list, you should be able to remove the same element
          //    from the shuffled list. If they contain the same # of each element, after removing each element, the shuffled list 
          //    should come down to an empty list.
          for (Person p: players) {
                assertTrue("All players in original list can be removed from shuffled player list?", shuffled_players.remove(p));
          }
          assertTrue("No extra players in shuffled player list?", shuffled_players.isEmpty());
        }
  //-----------------------------------------------------------------------------------------------------------------------
        
        //Test for the method "createLocations" in the Board class
        @Test
        public void createLocationsTest() {
              Board3Player b = new Board3Player();
              b.createLocations();
              Location [][]grid = b.getLocations();
              
              // Check that it is red team's move on count 0
              assertTrue(b.getCount() == 0);
              assertTrue(b.getTurn().equals("Red")); 
              // Check that for each location instance on the board, the instance's fields (codename, Person) and _isVisible are not null
              for (int row = 0; row < grid.length; row++) {
                for (int col = 0; col < grid[0].length; col++) {
                  Location location = grid[row][col];
                  // Does each board location reference a location object?
                  assertFalse("Location at each board 2d array spot?", location == null);
                  // Does each board location instance have a codename field?
                  // (Not sure what the non-initialized value for a string is, check for both "" and null cases)
                  assertFalse("Location codename at each Location instance not empty string?", location.getCodename().equals(""));
                  assertFalse("Location codename at each Location instance not null?", location.getCodename() == null);
                  // Does each board location instance have a Person field that refers to a Person object?
                  assertFalse("Location person field at each Location instance refers to a Person object?", location.getPerson() == null);
                  // Is each board location instance not visible? (aka no one revealed)
                  assertFalse("Location _isVisible field at each Location instance false?",location.getVisibility());
                }
              }
            }
  //--------------------------------------------------------------------------------------------------------------
        
        //Test for the method "Tile Selected" in the Board Class
        @Test
        public void testwhenTileSelected() {
            //creates a new board to test with
            Board3Player game= new Board3Player();
            //overrides the default value of _locations to a new array that is able to be tested
            game.setLocations(new Location[1][6]);
            
            //creates 3 locations to test our method
            Location t0= new Location("testname",new Person("Red"));
            Location t1= new Location("testname1",new Person("Blue"));
            Location t2= new Location("testname2", new Person("Green"));
            Location t3= new Location("testname3",new Person("Innocent"));
            Location t4 = new Location("testname4", new Person("Assassin"));
            
            //adds these new locations to the array
            game.getLocations()[0][0]=t0;
            game.getLocations()[0][1]=t1;
            game.getLocations()[0][2]=t2;
            game.getLocations()[0][3]=t3;
            game.getLocations()[0][4]=t4;
            
            //for use later checking visibility(This is extra but good to know)
            Location tVisibility= new Location("testname5",new Person("Red"));
            game.getLocations()[0][5] = tVisibility;

            // Testing case in which Red Team selects a red team tile
            
            //resetting the count
            game.setCount(7);
            //resetting the turn to red
            game.setTurn("Red");
            //resetting the visiblity of t0
            t0.setVisible(false);
            //selecting t0 for upcoming assertions
            boolean t0_bool = game.TileSelected(t0);

            assertTrue("This red tile should contain current agent",t0_bool);
            assertEquals("The current location selected was our teams location",6,game.getCount());
            assertTrue("The visibility of this tile should now be true", t0.getVisibility());
            assertEquals("The turn should still be red", "Red", game.getTurn());
            
            boolean t0_bool_again = game.TileSelected(t0);
            assertFalse("Should return false because this tile is already revealed", t0_bool_again);
            //checks that the count remains the same since the tile was already selected
            assertEquals("The Count should not go down because the tile has already been selected",6,game.getCount());
            //checks that t1 is still visible since it was already revealed
            assertTrue("The Tile should remain visible because the tile has already been selected",t0.getVisibility());
            
            // Testing case in which Red Team selects a blue team tile
            //resetting parameters
            game.setCount(7);
            game.setTurn("Red");
            t1.setVisible(false);
            boolean t1_bool = game.TileSelected(t1);
            assertFalse("This blue tile should not contain current agent",t1_bool);
            assertTrue("The Tile should remain visible because the tile has already been selected",t1.getVisibility());
            assertEquals("The location selected was blue teams agent, so the current turn is over", 0,game.getCount());
            assertEquals("The turn should now be blue", "Blue", game.getTurn());
            
            // Testing case in which Red Team selects a green team tile
            game.setCount(7);
            game.setTurn("Red");
            t2.setVisible(false);
            boolean t2_bool = game.TileSelected(t2);
            assertFalse("This green tile should not contain current agent", t2_bool);
            assertTrue("The Tile should remain visible because the tile has already been selected",t2.getVisibility());
            assertEquals("The location selected was the green teams agent, so the current turn is over", 0,game.getCount());
            assertEquals("The turn should now be blue", "Blue", game.getTurn());
            
            // Testing case in which Red Team selects an innocent tile
            game.setCount(7);
            game.setTurn("Red");
            t3.setVisible(false);
            boolean t3_bool = game.TileSelected(t3);
            assertFalse("This innocent tile should not contain current agent", t3_bool);
            assertTrue("The Tile should remain visible because the tile has already been selected",t3.getVisibility());
            assertEquals("The location selected was an innocent bystander, so the current turn is over",0,game.getCount());
            assertEquals("The turn should now be blue", "Blue", game.getTurn());

            // Testing case in which Red Team selects an assassin tile
            game.setCount(7);
            game.setTurn("Red");
            t4.setVisible(false);
            boolean t4_bool = game.TileSelected(t4);
            assertFalse("This assassin tile should not contain current agent", t4_bool);
            assertTrue("The Tile should remain visible because the tile has already been selected",t4.getVisibility());
            assertEquals("The location selected was an assassin, so the current turn is over",0,game.getCount());
            assertEquals("The turn should now be blue", "Blue", game.getTurn());
            
            //checking that visibility changes
            assertFalse("Tile initialized to visibility false?", tVisibility.getVisibility());
            game.TileSelected(tVisibility);
            //returns the visibility and checks that it is now true
            assertTrue("Checks to see if visibility will change",tVisibility.getVisibility());
            
        }

      //--------------------------------------------------------------------------------------------------------------
        
        @Test
        public void testisInWinningState() {
            
            Board3Player game=new Board3Player(); //initializes a board to test
            HashMap<String, Integer> scores = new HashMap<String, Integer>(); //used to store game stats
            game.set_tilesRevealed(scores); //setting up the game board with our info rather than random
            // Scores randomized to any losing score below
            int randblue = (int) (Math.random() * 4);
            int randred = (int) (Math.random() * 5);
            int randgreen = (int) (Math.random() * 4);
            List<Integer> randassassinchoices = new ArrayList<Integer>();
            randassassinchoices.add((Integer) 0);
            randassassinchoices.add((Integer) 1);
            int randassassin = randassassinchoices.get(new Random().nextInt(randassassinchoices.size()));
            
            //setting the scores and turn for current test
            scores.put("Red", 6);
            scores.put("Blue",randblue);
            scores.put("Green", randgreen);
            scores.put("Assassin",randassassin);
            
            //by the current scores red should be winning because they have 9 of their agents revealed
            assertTrue("Is there a winning state? Red should be in winning state",game.isInWinningState());
            
            //resetting scores and making blue able to win. is Red turn though
            scores.put("Red", randred);
            scores.put("Blue", 5);
            scores.put("Green", randgreen);
            scores.put("Assassin",randassassin);
            
            //Blue team wins because red found the blue teams final agent
            assertTrue("Is there a winning state? Blue should be in winning state",game.isInWinningState()); 
            
            // Test if green team wins from a score of 5
            scores.put("Red", randred);
            scores.put("Blue", randblue);
            scores.put("Green", 5);
            scores.put("Assassin", randassassin);
            
            // should be true bc green team has 5/5 agents revealed
            assertTrue("Is there a winning state? Green should be in winning state",game.isInWinningState()); 

            //making an assassin win
            scores.put("Red", randred);
            scores.put("Blue", randblue);
            scores.put("Green", randgreen);
            scores.put("Assassin",2);
            
            //Should be a winner bc 2 teams revealed assassins
            assertTrue("Is there a winning state? Should be bc 2 assassins revealed",game.isInWinningState());         
            
            // No one has all tiles revealed and both assassins haven't been revealed
            scores.put("Red", randred);
            scores.put("Blue",randblue);
            scores.put("Green", randgreen);
            scores.put("Assassin",randassassin);
            
            //there isnt a winning state so it returns false
            assertFalse("Is there a winning state?",game.isInWinningState()); 
        }

      //--------------------------------------------------------------------------------------------------------------
        
        @Test
        public void testwhoWonAssassinRevealed() {
            //returns team name who won bc the assassin was revealed by the other team.
          
            // TEST FOR GREEN WIN
            
            //creates a board for testing
            Board3Player game=new Board3Player();
            int randblue = (int) (Math.random() * 4);
            int randred = (int) (Math.random() * 5);
            int randgreen = (int) (Math.random() * 4);
   
            HashMap<String, Integer> scores= new HashMap<String, Integer>(); //used to store game stats
            //creating scores to test with
            scores.put("Red", randred);
            scores.put("Blue",randblue);
            scores.put("Green", randgreen);
            scores.put("Assassin",0);
            game.set_tilesRevealed(scores);
            
            //setting up location tiles with type assassin
            Location aasloc1 = new Location("Test1", new Person("Assassin"));
            Location aasloc2 = new Location("Test2", new Person("Assassin"));
            game.getLocations()[0][0] = aasloc1;
            game.getLocations()[0][1] = aasloc2;
            
            game.TileSelected(aasloc1); //red turn
            assertNull("one assassin found; therefore, no winner", game.whoWonAssassinRevealed());
            game.TileSelected(aasloc2); //blue turn
            assertEquals("both assassins found; one by red team, one by blue team, so green must be winner","Green",game.whoWonAssassinRevealed());
            
            // TEST FOR RED WIN
            game=new Board3Player();
            randblue = (int) (Math.random() * 4);
            randred = (int) (Math.random() * 5);
            randgreen = (int) (Math.random() * 4);
            scores.put("Red", randred);
            scores.put("Blue",randblue);
            scores.put("Green", randgreen);
            scores.put("Assassin",0);
            game.set_tilesRevealed(scores);
            aasloc1 = new Location("Test1", new Person("Assassin"));
            aasloc2 = new Location("Test2", new Person("Assassin"));
            game.getLocations()[0][0] = aasloc1;
            game.getLocations()[0][1] = aasloc2;
            
            // change turn to blue
            game.setTurn("Blue");
            game.TileSelected(aasloc1); //blue turn
            assertNull("one assassin found; therefore, no winner", game.whoWonAssassinRevealed());
            game.TileSelected(aasloc2); //green turn
            assertEquals("both assassins found; one by blue team, one by green team, so green must be winner","Red",game.whoWonAssassinRevealed());
            
            //TEST FOR BLUE WIN
            game=new Board3Player();
            randblue = (int) (Math.random() * 4);
            randred = (int) (Math.random() * 5);
            randgreen = (int) (Math.random() * 4);
            scores.put("Red", randred);
            scores.put("Blue",randblue);
            scores.put("Green", randgreen);
            scores.put("Assassin",0);
            game.set_tilesRevealed(scores);
            aasloc1 = new Location("Test1", new Person("Assassin"));
            aasloc2 = new Location("Test2", new Person("Assassin"));
            game.getLocations()[0][0] = aasloc1;
            game.getLocations()[0][1] = aasloc2;
            
            // change turn to green
            game.setTurn("Green");
            game.TileSelected(aasloc1); //green turn
            assertNull("one assassin found; therefore, no winner", game.whoWonAssassinRevealed());
            game.TileSelected(aasloc2); //red turn
            assertEquals("both assassins found; one by blue team, one by green team, so green must be winner","Blue",game.whoWonAssassinRevealed());
        }
        
        
        
  }
