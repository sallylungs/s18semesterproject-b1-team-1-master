package tests;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Test;

import code.Board;
import code.Location;
import code.Person;


public class Tests {

	//Test if the method "read" in the Board Class
	@Test
	public void readCodeNameTest() {
		Board b = new Board();
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
		Board b = new Board();
		List <String> s1 = new ArrayList<>();
		List <String> s2 = new ArrayList<>();
		boolean t = false;
		HashMap<String, Integer> dup = new HashMap<>();
		
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
		for(int i=0; i<25; i++) {
			dup.put(s1.get(i), 0);//putting the list into a HashMap to ensure no duplications
			if(!((s1.get(i)).equals(s2.get(i)))){
				t=true;
			}else {
				t=false;
			}
		}
		
		assertEquals(25, s1.size());//checks if there are 25 codenames generated
		assertEquals(true, t);//checks if it is shuffled
		assertEquals("Duplicates",true,dup.size()==s1.size());//check for duplications
	}
//-----------------------------------------------------------------------------------------
	
	//Test for the method "clue" in the Board class
	@Test
	public void clueTest() {
		
		Board b = new Board();
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
		Board b = new Board();
		b.init();//Generates 25 agents(not shuffled)
	    @SuppressWarnings("unchecked") //Added to remove "unchecked cast warning" Complier dosent know what kind of object "b.get_agents()" is so it gives warning 
		List<Person> players = (List<Person>) b.get_agents().clone();//Clone of the list of agents before it is shuffled
	    List<Person> shuffled_players = new ArrayList<Person>();
	    // 1) Set up an original list, player, of different player types: 9 Red Agents,
	    //		8 Blue Agents, 7 Innocent Bystanders, and 1 Assassins
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
		    Board b = new Board();
		    b.createLocations();
		    Location [][]grid = b.getLocations();
		    
		    // Check that it is red team's move on count 0
		    assertTrue(b.getCount() == 0);
		    assertTrue(b.get_isRedTurn()); 
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
		  Board game= new Board();
		  //overrides the default value of _locations to a new array that is able to be tested
		  game.setLocations(new Location[1][3]);
		  
		  //creates 3 locations to test our method
		  Location t1= new Location("testname",new Person("Red"));
		  Location t2= new Location("testname2",new Person("Blue"));
		  Location t3= new Location("testname3",new Person("Innocent"));
		  
		  //adds these new locations to the array
		  game.getLocations()[0][0]=t2;
		  game.getLocations()[0][1]=t3;
		  game.getLocations()[0][2]=t1;
		  
		  //for use later checking visibility(This is extra but good to know)
		  Location tVisibility= new Location("testname4",new Person("Red"));
		  
		  //By Default it is the Red turn, so this should return true since t1 is a red tile
		  assertTrue("The Tile Should contain the teams current agent",game.TileSelected(t1));
		  //since it is still red turn and tile is blue it should return false.
		  assertFalse("The tile should not contain the current agent",game.TileSelected(t2));
		  
		  //resetting the count
		  game.setCount(7);
		  //resetting the turn to red
		  game.set_isRedTurn(true);
		  //resetting the visiblity of t1
		  t1.setVisible(false);
		  //selecting t1 for upcoming assertions
		  game.TileSelected(t1);
		  
		  //Checks that the count decreased by 1 after selecting our teams tile
		  assertEquals("The current location selected was our teams location",6,game.getCount());
		  game.TileSelected(t1);
		  //checks that the count remains the same since the tile was already selected
		  assertEquals("The Count should not go down because the tile has already been selected",6,game.getCount());
		  //checks that t1 is still visible since it was already revealed
		  assertTrue("The Tile should remain visible because the tile has already been selected",t1.getVisibility());
		  
		  //resetting parameters
		  game.setCount(7);
		  game.set_isRedTurn(true);
		  t2.setVisible(false);
		  game.TileSelected(t2);
		  //since we selected an enemy team tile our count goes to zero and now it is the enemy turn
		  assertEquals("The location selected was the other teams agent, so the current turn is over", 0,game.getCount());
		  
		  //resetting params
		  game.setCount(7);
		  t3.setVisible(false);
		  game.TileSelected(t3);
		  //Since the location selected was an innocent bystander the current turn is over
		  assertEquals("The location selected was an innocent bystander, so the current turn is over",0,game.getCount());
		 
		  //checking that visiblity changes
		  tVisibility.setVisible(true);
		  //returns the visibility and checks that it is now true
		  assertTrue("Checks to see if visibility will change",tVisibility.getVisibility());
	  }

	//--------------------------------------------------------------------------------------------------------------
	  
	  @Test
	  public void testisInWinningState() {
		  //Board should have variables that count how many blue agents, red agents and if the assassin is revealed.
		  
		  Board game=new Board(); //initializes a board to test
		  HashMap<String, Integer> scores = new HashMap<String, Integer>(); //used to store game stats
          game.set_tilesRevealed(scores); //setting up the game board with our info rather than random
          
          //setting the scores and turn for current test
		  scores.put("Red", 9);
		  scores.put("Blue",0);
		  scores.put("Assassin",0);
		  game.set_isRedTurn(true);
		  
		  //by the current scores red should be winning because they have 9 of their agents revealed
		  assertTrue("Is there a winning state? Red should be in winning state",game.isInWinningState());
		  
		  //resetting scores and making blue able to win. is Red turn though
		  scores.put("Red", 0);
		  scores.put("Blue",8);
		  scores.put("Assassin",0);
		  
		  //Blue team wins because red found the blue teams final agent
		  assertTrue("Is there a winning state? Blue should be in winning state",game.isInWinningState()); 
		  
		  //making an assassin win
		  scores.put("Red", 0);
		  scores.put("Blue",6);
		  scores.put("Assassin",1);
		  
		  //the Blue team wins since red found the assassin
		  assertTrue("Is there a winning state? Blue should be in winning state bc red found assassin",game.isInWinningState()); 		 
		  
		  //no one wins and its blue turn
		  scores.put("Red", 0);
		  scores.put("Blue",0);
		  scores.put("Assassin",0);
		  game.set_isRedTurn(false);
		  
		  //there isnt a winning state so it returns false
		  assertFalse("Is there a winning state?",game.isInWinningState()); 
	  }

	//--------------------------------------------------------------------------------------------------------------
	  
	  @Test
	  public void testwhoWonAssassinRevealed() {
		  //returns team name who won bc the assassin was revealed by the other team.
		  
		  //creates a board for testing
		  Board game=new Board();
		  
		  HashMap<String, Integer> scores= new HashMap<String, Integer>(); //used to store game stats
		  //creating scores to test with
		  scores.put("Red", 1);
		  scores.put("Blue",1);
		  scores.put("Assassin",1);
		  //adding them to the games_tiles
		  game.set_tilesRevealed(scores);
		  //By default it is red turn
		  game.set_isRedTurn(true);
		  
		  //since it is red turn blue wins bc red found assassin
		  assertEquals("assassin found but is red turn so blue wins","Blue",game.whoWonAssassinRevealed());
		  //Now it is Blue turn
		  game.set_isRedTurn(false);
		  //Red wins since blue revealed the assassin
		  assertEquals("assassin found but is blue turn so red wins","Red",game.whoWonAssassinRevealed());	
		  
	  }
	  
	  
}
