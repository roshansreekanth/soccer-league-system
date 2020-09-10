package views;

import java.util.Collections;
import java.util.List;

import java.time.LocalDate;

import controller.Controller;
import controller.DBOperations;
import controller.ManagerRatingComparator;
import controller.PersonNameComparator;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.League;
import models.Manager;
import models.Player;
import models.Team;

public class View extends Application
{
	private Controller controllerReference;
	
    /**
     * Renders a GridPane element based on the parameters given
     * @param position The relative position of all elements
     * @param HGap Horizontal gap between elements
     * @param VGap Vertical gap between elements
     * @param insetValuesObject Defines the margins and borders
     * @return Returns a GridPane object
     */
    public GridPane renderGridPane(Pos position, int HGap, int VGap, Insets insetValuesObject)
    {
        GridPane grid = new GridPane();
        grid.setAlignment(position);
        grid.setHgap(HGap);
        grid.setVgap(VGap);
        grid.setPadding(insetValuesObject);
        
        return grid;
    }
    
    /**
     * Fetches elements from a table in a database and populates them in a dropdown
     * @param dropDown The ComboBox to be populated
     * @param tableName The name of the table from which elements are fetched from
     */
    public void refreshDropdown(ComboBox dropDown, String tableName)
    {
		dropDown.setItems(FXCollections.observableArrayList(controllerReference.get(tableName)));
        dropDown.getSelectionModel().selectFirst();
    }
    
    /**
     * Fetches elements from a table in a database and populates them in a ListView based on a specific condition
     * @param listViewObject The ListView to be populated
     * @param tableName The table from which the data is fetched
     * @param conditionalColumn The specific column from which to read the data from
     * @param conditionalValue The value of the conditional column
     */
    public void refreshListView(ListView listViewObject, String tableName, String conditionalColumn, String conditionalValue)
    {
    	listViewObject.getItems().clear();
    	List allElements = controllerReference.getConditional(tableName, conditionalColumn, conditionalValue);
    	Collections.sort(allElements, new PersonNameComparator());
    	listViewObject.setItems(FXCollections.observableArrayList(allElements));
    }
    
    /**
     * Fetches <b>all</b> elements from a table and populates them in a ListView
     * @param listViewObject The ListView object that is to be populated with data
     * @param tableName The table from which to fetch the data
     * @param orderBy In case a sort is needed, it either orders by name or some other String criteria
     */
    public void refreshAllElementsListView (ListView listViewObject, String tableName, String orderBy)
    {
    	listViewObject.getItems().clear();
    	List allElements = controllerReference.get(tableName);
    	
    	if(orderBy.equals("name"))
    	{
    		Collections.sort(allElements, new PersonNameComparator());
    	}
    	else 
    	{
    		Collections.sort(allElements, new ManagerRatingComparator());
		}
    	listViewObject.setItems(FXCollections.observableArrayList(allElements));
    }
    
	public static void main(String[] args)
	{

		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		controllerReference = new Controller();
		primaryStage.setTitle("Soccer League Application");
		
		TabPane tabPane = new TabPane();
		tabPane.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		
		Tab introTab = new Tab("Intro");
		Tab manageTab = new Tab("Add");
		Tab viewTab = new Tab("View");
		Tab removeTab = new Tab("Remove");
		Tab editTab = new Tab("Search and Edit");
		
		VBox introBox = new VBox(10);
		Label welcomeMessage = new Label("Welcome to the app!");
		Button overloadMemoryButton = new Button("Overload Memory");
		introBox.getChildren().addAll(welcomeMessage, overloadMemoryButton);
		introTab.setContent(introBox);
		
		GridPane manageGrid = renderGridPane(Pos.TOP_CENTER, 10, 10, new Insets(10, 10, 10, 10));
		
		// ------------------- LEAGUE AREA ---------------- //
		Label leagueTitleLabel = new Label("League Details");
		manageGrid.add(leagueTitleLabel, 0, 0);
		
		HBox leagueBox = new HBox(10);
		Label leagueNameLabel = new Label("League Name   ");
		TextField leagueTextField = new TextField();
		Button addLeagueButton = new Button("Add");
		leagueBox.getChildren().addAll(leagueNameLabel, leagueTextField, addLeagueButton);
		manageGrid.add(leagueBox, 0, 1);
				
		// ------------------- LEAGUE AREA ---------------- //

	
		// ------------------- MANAGER AREA ---------------- //
        
		Label managerLabel = new Label("Manager details");
		manageGrid.add(managerLabel, 0, 3);
		
        HBox managerNameBox = new HBox(10);
		Label managerNameLabel = new Label("Manager Name ");
		TextField managerNameTextField = new TextField();
		managerNameBox.getChildren().addAll(managerNameLabel,managerNameTextField);
		manageGrid.add(managerNameBox, 0, 4);
		
		HBox managerEmailBox = new HBox(10);
		Label managerEmailLabel = new Label("Manager Email");
		TextField managerEmailTextField = new TextField();
		managerEmailBox.getChildren().addAll(managerEmailLabel, managerEmailTextField);
		manageGrid.add(managerEmailBox, 1, 4);
		
		HBox managerPhoneBox = new HBox(10);
		Label managerPhoneLabel = new Label("Manager Phone");
		TextField managerPhoneTextField = new TextField();
		managerPhoneBox.getChildren().addAll(managerPhoneLabel, managerPhoneTextField);
		manageGrid.add(managerPhoneBox, 0, 5);
		
		 HBox starBox = new HBox(10);
		 Label starLabel = new Label("Star Rating      ");
		 Spinner<Integer> starTextField = new Spinner<Integer>(0, Integer.MAX_VALUE, 1);
		 starTextField.setPrefWidth(80);
		 starTextField.setEditable(true);
		 starBox.getChildren().addAll(starLabel, starTextField);
		 
		 manageGrid.add(starBox, 1, 5);
		
	     HBox managerDateInput = new HBox(10);
		 Label managerDateLabel = new Label("Date of birth     ");
	     DatePicker dateTextField = new DatePicker();
	     managerDateInput.getChildren().addAll(managerDateLabel, dateTextField);
	     manageGrid.add(managerDateInput, 0, 6);
	     
	     Button addManagerButton = new Button("Add");
	     manageGrid.add(addManagerButton, 2, 7);
		 manageTab.setContent(manageGrid);
		// ------------------- MANAGER AREA ---------------- //
		 
		// ------------------- TEAM AREA ---------------- //
			Label teamTitleLabel = new Label("Team Details");
			manageGrid.add(teamTitleLabel, 0, 9);
			
			HBox teamNameBox = new HBox(10);
			Label teamLabel = new Label("Team Name");
			TextField teamTextField = new TextField();
			teamNameBox.getChildren().addAll(teamLabel, teamTextField);
			manageGrid.add(teamNameBox, 0, 10);
			
			HBox teamLeagueBox = new HBox(10);
			Label teamLeagueLabel = new Label("League   ");
		    ComboBox<League> teamLeagueDropdown = new ComboBox<League>();
		    teamLeagueDropdown.setPrefWidth(200);
		    refreshDropdown(teamLeagueDropdown, "League");
		    teamLeagueBox.getChildren().addAll(teamLeagueLabel, teamLeagueDropdown);
		    manageGrid.add(teamLeagueBox, 1, 10);
		    
			HBox teamColourBox = new HBox();
			Label teamColourLabel = new Label("Jersey Colour");
			TextField teamColourTextField = new TextField();
			teamColourBox.getChildren().addAll(teamColourLabel, teamColourTextField);
			manageGrid.add(teamColourBox, 0, 11);
			
			HBox teamManagerBox = new HBox(10);
			Label teamManagerLabel = new Label("Manager");
		    ComboBox<Manager> teamManagerDropdown = new ComboBox<Manager>();
		    teamManagerDropdown.setPrefWidth(200);
		    refreshDropdown(teamManagerDropdown, "Manager");
			teamManagerBox.getChildren().addAll(teamManagerLabel, teamManagerDropdown);
			manageGrid.add(teamManagerBox, 1, 11);
			
			Button addTeamButton = new Button("Add");
			manageGrid.add(addTeamButton, 2, 12);
			// ------------------- TEAM AREA ---------------- //
			
			// ------------------- PLAYER AREA ---------------- //
			Label playerLabel = new Label("Player details");
			manageGrid.add(playerLabel, 0, 13);
			
			HBox playerNameBox = new HBox(10);
			Label playerNameLabel = new Label("Player Name");
			TextField playerNameTextField = new TextField();
			playerNameBox.getChildren().addAll(playerNameLabel, playerNameTextField);
			manageGrid.add(playerNameBox, 0, 14);
			
			HBox playerEmailBox = new HBox(10);
			Label playerEmailLabel = new Label("Player Email");
			TextField playerEmailTextField = new TextField();
			playerEmailBox.getChildren().addAll(playerEmailLabel, playerEmailTextField);
			manageGrid.add(playerEmailBox, 1, 14);
			
			HBox playerPhoneBox = new HBox(10);
			Label playerPhoneLabel = new Label("Player Phone");
			TextField playerPhoneTextField = new TextField();
			playerPhoneBox.getChildren().addAll(playerPhoneLabel, playerPhoneTextField);
			manageGrid.add(playerPhoneBox, 0, 15);
			
			
	        CheckBox goalieCheckBox = new CheckBox("Goalie?");
	        manageGrid.add(goalieCheckBox, 1, 17);
	        
	        HBox goalsInput = new HBox(10);
	        Label goalsLabel = new Label("Goals            ");
	        Spinner<Integer> goalsTextField = new Spinner<Integer>(0, Integer.MAX_VALUE, 1);
	        goalsTextField.setPrefWidth(80);
	        goalsTextField.setEditable(true);
	        goalsInput.getChildren().addAll(goalsLabel, goalsTextField);
			
			manageGrid.add(goalsInput, 0, 17);
			
	        
	        HBox playerTeamBox = new HBox(10);
	        Label playerTeamLabel = new Label("Team          ");
	        ComboBox<Team> playerTeamDropdown = new ComboBox<Team>();
	        playerTeamDropdown.setPrefWidth(200);
	        playerTeamBox.getChildren().addAll(playerTeamLabel, playerTeamDropdown);
	        refreshDropdown(playerTeamDropdown, "Team");
	        manageGrid.add(playerTeamBox, 1, 15);
	        
	        Button addPlayerButton = new Button("Add");
	        manageGrid.add(addPlayerButton, 2, 18);
	        
			// ------------------- PLAYER AREA ---------------- //
	        
		
		// ------------------- VIEW AREA ---------------- //
		GridPane viewPane = renderGridPane(Pos.TOP_LEFT, 10, 10, new Insets(10, 10, 10, 10));
		HBox teamSelectBox = new HBox(10);
		
		Label teamSelectLabel = new Label("Select Team");
		ComboBox<Team> teamSelectDropdown = new ComboBox<Team>();
		teamSelectDropdown.setPrefWidth(200);
		refreshDropdown(teamSelectDropdown, "Team");
		teamSelectBox.getChildren().addAll(teamSelectLabel, teamSelectDropdown);
		viewPane.add(teamSelectBox, 0, 0);
		
		HBox selectedTeamManagerBox = new HBox(10);
		Label selectedTeamManagerLabel = new Label("Manager     ");
		TextField selectedTeamManagerName = new TextField();		
		selectedTeamManagerName.setEditable(false);
		selectedTeamManagerBox.getChildren().addAll(selectedTeamManagerLabel, selectedTeamManagerName);
		viewPane.add(selectedTeamManagerBox, 0, 1);
		

		HBox selectedTeamLeagueBox = new HBox(10);
		Label selectedTeamLeagueLabel = new Label("League        ");
		TextField selectedTeamLeagueName = new TextField();		
		selectedTeamLeagueName.setEditable(false);
		selectedTeamLeagueBox.getChildren().addAll(selectedTeamLeagueLabel, selectedTeamLeagueName);
		viewPane.add(selectedTeamLeagueBox, 0, 2);
		
		VBox selectedTeamPlayersBox = new VBox(10);
		ListView<Player> teamPlayersView = new ListView<Player>();
		selectedTeamPlayersBox.getChildren().addAll(teamPlayersView);
		viewPane.add(selectedTeamPlayersBox, 0, 3);
		
		VBox allManagersListViewBox = new VBox(10);
		Label viewAllManagerLabel = new Label("View all Managers");
		HBox orderingButtonsBox = new HBox(10);
		Button sortByRatingButton = new Button("Sort by Rating");
		Button sortByAlphabetButton = new Button("Sort by Alphabet");
		orderingButtonsBox.getChildren().addAll(sortByAlphabetButton, sortByRatingButton);
		ListView<Manager> managersListView = new ListView<Manager>();
		allManagersListViewBox.getChildren().addAll(viewAllManagerLabel, orderingButtonsBox, managersListView);
		viewPane.add(allManagersListViewBox, 0, 6);
		refreshAllElementsListView(managersListView, "Manager", "name");
		
    	teamSelectDropdown.getSelectionModel().selectFirst();
		Team teamObject =  teamSelectDropdown.getSelectionModel().getSelectedItem();
		
		if(teamObject != null)
		{
			refreshListView(teamPlayersView, "Player", "teamName", teamObject.getName());
			selectedTeamManagerName.setText(teamObject.getManager());
			selectedTeamLeagueName.setText(teamObject.getLeague());
		}
		else 
		{
			teamPlayersView.getItems().clear();
			selectedTeamManagerName.clear();
			selectedTeamLeagueName.clear();
		}
		
		Label viewAllTeamsLabel = new Label("View all teams in League");
		viewPane.add(viewAllTeamsLabel, 6, 0);
		
		HBox leagueSelectBox = new HBox(10);
		Label leagueSelectLabel = new Label("Select League");
		ComboBox<League> leagueSelectDropdown = new ComboBox<League>();
		leagueSelectDropdown.setPrefWidth(200);
		refreshDropdown(leagueSelectDropdown, "League");
		leagueSelectBox.getChildren().addAll(leagueSelectLabel, leagueSelectDropdown);
		viewPane.add(leagueSelectBox, 6, 1);
		
		VBox selectedLeagueTeamsBox = new VBox(10);
		ListView<Team> leagueTeamsView = new ListView<Team>();
		selectedLeagueTeamsBox.getChildren().addAll(leagueTeamsView);
		viewPane.add(selectedLeagueTeamsBox, 6, 3);
		
		leagueSelectDropdown.getSelectionModel().selectFirst();
		League selectedLeagueObject =  leagueSelectDropdown.getSelectionModel().getSelectedItem();
		
		if(selectedLeagueObject != null)
		{
			refreshListView(leagueTeamsView, "Team", "leagueName", selectedLeagueObject.getName());
		}
		else 
		{
			leagueTeamsView.getItems().clear();
		}
		
		viewTab.setContent(viewPane); 
		// ------------------- VIEW AREA ---------------- //

		
		// ------------------- REMOVE AREA ---------------- //
		
		GridPane removePane = renderGridPane(Pos.CENTER, 10, 10, new Insets(10, 10, 10, 10));
		
		HBox leagueRemoveBox = new HBox(10);
		Label leagueRemoveLabel = new Label("League    ");
		ComboBox<League> leagueRemoveDropDown = new ComboBox<League>();
		leagueRemoveDropDown.setPrefWidth(200);
		refreshDropdown(leagueRemoveDropDown, "League");
		Button leagueRemoveButton = new Button("Remove");
		leagueRemoveBox.getChildren().addAll(leagueRemoveLabel, leagueRemoveDropDown, leagueRemoveButton);
		removePane.add(leagueRemoveBox, 0, 0);
		refreshDropdown(leagueRemoveDropDown, "League");
		
		HBox teamRemoveBox = new HBox(10);
		Label teamRemoveLabel = new Label("Team       ");
		ComboBox<Team> teamRemoveDropDown = new ComboBox<Team>();
		teamRemoveDropDown.setPrefWidth(200);
		refreshDropdown(teamRemoveDropDown, "Team");
		Button teamRemoveButton = new Button("Remove");
		teamRemoveBox.getChildren().addAll(teamRemoveLabel, teamRemoveDropDown, teamRemoveButton);
		removePane.add(teamRemoveBox, 0, 1);
		refreshDropdown(teamRemoveDropDown, "Team");
		
		HBox managerRemoveBox = new HBox(10);
		Label managerRemoveLabel = new Label("Manager  ");
		ComboBox<Manager> managerRemoveDropDown = new ComboBox<Manager>();
		managerRemoveDropDown.setPrefWidth(200);
		refreshDropdown(managerRemoveDropDown, "Manager");
		Button managerRemoveButton = new Button("Remove");
		managerRemoveBox.getChildren().addAll(managerRemoveLabel, managerRemoveDropDown, managerRemoveButton);
		removePane.add(managerRemoveBox, 0, 2);
		refreshDropdown(managerRemoveDropDown, "Manager");
		
		HBox playerRemoveBox = new HBox(10);
		Label playerRemoveLabel = new Label("Player      ");
		ComboBox<Player> playerRemoveDropDown = new ComboBox<Player>();
		playerRemoveDropDown.setPrefWidth(200);
		refreshDropdown(playerRemoveDropDown, "Player");
		Button playerRemoveButton = new Button("Remove");
		playerRemoveBox.getChildren().addAll(playerRemoveLabel, playerRemoveDropDown, playerRemoveButton);
		removePane.add(playerRemoveBox, 0, 3);
		refreshDropdown(playerRemoveDropDown, "Player");
		removeTab.setContent(removePane);		
		// ------------------- REMOVE AREA ---------------- //
		// ------------------- EDIT AREA ------------------//
		GridPane editPane = renderGridPane(Pos.CENTER, 10, 10, new Insets(10, 10, 10, 10));
		
		HBox searchPlayerBox = new HBox(10);
		Label playerNameSearchLabel = new Label("Search Player");
		TextField playerSearchTextField = new TextField();
		Button playerSearchButton = new Button("Search");
		searchPlayerBox.getChildren().addAll(playerNameSearchLabel, playerSearchTextField, playerSearchButton);
		editPane.add(searchPlayerBox, 0, 0);
		
		HBox searchedManagerNameBox = new HBox(10);
		Label searchedManagerNameLabel = new Label("Manager Name");
		TextField searchedManagerNameTextField = new TextField();
		searchedManagerNameTextField.setEditable(false);
		searchedManagerNameBox.getChildren().addAll(searchedManagerNameLabel, searchedManagerNameTextField);
		editPane.add(searchedManagerNameBox, 1, 0);
		
		HBox searchedPlayerNameBox = new HBox(10);
		Label searchedPlayerNameLabel = new Label("Player Name ");
		TextField searchedPlayerNameTextField = new TextField();
		searchedPlayerNameBox.getChildren().addAll(searchedPlayerNameLabel, searchedPlayerNameTextField);
		editPane.add(searchedPlayerNameBox, 0, 5);
		
		HBox searchedPlayerEmailBox = new HBox(10);
		Label searchedPlayerEmailLabel = new Label("Player Email  ");
		TextField searchedPlayerEmailTextField = new TextField();
		searchedPlayerEmailBox.getChildren().addAll(searchedPlayerEmailLabel, searchedPlayerEmailTextField);
		editPane.add(searchedPlayerEmailBox, 1, 5);
		
		HBox searchedPlayerPhoneBox = new HBox(10);
		Label searchedPlayerPhoneLabel = new Label("Player Phone");
		TextField searchedPlayerPhoneTextField = new TextField();
		searchedPlayerPhoneBox.getChildren().addAll(searchedPlayerPhoneLabel, searchedPlayerPhoneTextField);
		editPane.add(searchedPlayerPhoneBox, 0, 6);
					
        CheckBox searchedGoalieCheckBox = new CheckBox("Goalie?");
        editPane.add(searchedGoalieCheckBox, 1, 6);
        
        HBox searchedGoals = new HBox(10);
        Label searchedGoalsLabel = new Label("Goals            ");
        Spinner<Integer> searchedGoalsTextField = new Spinner<Integer>(0, Integer.MAX_VALUE, 0);
        searchedGoalsTextField.setPrefWidth(80);
        searchedGoalsTextField.setEditable(true);
        searchedGoals.getChildren().addAll(searchedGoalsLabel, searchedGoalsTextField);
        editPane.add(searchedGoals, 0, 7);
		
        Button saveEditButton = new Button("Save Changes");
        editPane.add(saveEditButton, 2, 8);
        
        editTab.setContent(editPane);
		//-------------------- EDIT AREA ------------------//
		tabPane.getTabs().add(introTab);
		tabPane.getTabs().add(manageTab);
		tabPane.getTabs().add(viewTab);
		tabPane.getTabs().add(removeTab);
		tabPane.getTabs().add(editTab);
		
		Scene scene = new Scene(tabPane, 1280, 720);
        scene.getStylesheets().add("stylesheet.css");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        addLeagueButton.setOnAction(e ->
        {
        	String leagueTextString = leagueTextField.getText();
        	if(leagueTextString.length() > 0)
        	{
        		League leagueObject = new League(leagueTextString);
        		controllerReference.add(leagueObject);
        		refreshDropdown(teamLeagueDropdown, "League");
        		refreshDropdown(leagueRemoveDropDown, "League");
        		refreshDropdown(leagueSelectDropdown, "League");
        		leagueSelectDropdown.getSelectionModel().selectFirst();
            	League updatedLeagueObject = leagueSelectDropdown.getSelectionModel().getSelectedItem();
            	if(updatedLeagueObject != null)
            	{
            		refreshListView(leagueTeamsView, "Team", "leagueName", updatedLeagueObject.getName());
            	}
            	else 
            	{
            		leagueTeamsView.getItems().clear();
    			}
        	}
        });
        
        addManagerButton.setOnAction(e -> 
        {
        	String managerName = managerNameTextField.getText();
        	String managerEmail = managerEmailTextField.getText();
        	String managerPhone = managerPhoneTextField.getText();
        	int managerRating = starTextField.getValue();
        	LocalDate managerDOB = dateTextField.getValue();
        	String managerTeam = null;
        	int managerTaken = 0;
        	
        	if(managerName.length() > 0 && managerName.contains(" ") && managerEmail.length() > 0 && managerPhone.length() > 0 && managerDOB != null)
        	{
        		Manager managerObject = new Manager(managerName, managerPhone, managerEmail, managerDOB, managerRating);
        		managerObject.setStatus(false);
        		managerObject.setTeam(null);
        		controllerReference.add(managerObject);
        		refreshDropdown(teamManagerDropdown, "Manager");
        		refreshDropdown(managerRemoveDropDown, "Manager");
        		refreshAllElementsListView(managersListView, "Manager", "name");

        	}
        });
        
        addTeamButton.setOnAction(e ->
        {
        	String teamName = teamTextField.getText();
        	String teamColour = teamColourTextField.getText();
        	String teamLeagueName = teamLeagueDropdown.getSelectionModel().getSelectedItem().getName();
        	String teamManagerEmail = teamManagerDropdown.getSelectionModel().getSelectedItem().getEmail();
        	controllerReference.processTeam(teamName, teamColour, teamLeagueName, teamManagerEmail);
        	leagueSelectDropdown.getSelectionModel().selectFirst();
        	League updatedLeagueObject = leagueSelectDropdown.getSelectionModel().getSelectedItem();
        	if(updatedLeagueObject != null)
        	{
        		refreshListView(leagueTeamsView, "Team", "leagueName", updatedLeagueObject.getName());
        	}
        	else 
        	{
        		leagueTeamsView.getItems().clear();
			}
        	refreshDropdown(playerTeamDropdown, "Team");
        	refreshDropdown(teamSelectDropdown, "Team");
        	refreshDropdown(teamRemoveDropDown, "Team");
        });
        
        addPlayerButton.setOnAction(e ->
        {
        	String playerName = playerNameTextField.getText();
        	String playerEmail = playerEmailTextField.getText();
        	String playerPhone = playerPhoneTextField.getText();
        	String playerTeamName = playerTeamDropdown.getSelectionModel().getSelectedItem().getName();
        	int numGoals = goalsTextField.getValue();
        	boolean goalieStatus = false;
        	if(goalieCheckBox.isSelected())
        	{
        		goalieStatus = true;
        	}
        	
        	if(playerName.length() > 0 && playerName.contains(" ") && playerEmail.length() > 0 && playerPhone.length() > 0)
        	{
        		Player playerObject = new Player(playerName, playerPhone, playerEmail, numGoals, goalieStatus);
            	controllerReference.processPlayer(playerObject, playerTeamName);
        		refreshDropdown(playerRemoveDropDown, "Player");
        		teamSelectDropdown.getSelectionModel().selectFirst();
        		Team updatedTeamObject = teamSelectDropdown.getSelectionModel().getSelectedItem();
        		if(updatedTeamObject != null)
        		{
        			refreshListView(teamPlayersView, "Player", "teamName", updatedTeamObject.getName());
        			selectedTeamManagerName.setText(updatedTeamObject.getManager());
        			selectedTeamLeagueName.setText(updatedTeamObject.getLeague());
        		}
        		else 
        		{
    				selectedTeamManagerName.clear();
    				selectedTeamLeagueName.clear();
    			}
        	}
        });
        
        teamSelectDropdown.setOnAction(e->
        {
    		Team selectedTeamObject =  teamSelectDropdown.getSelectionModel().getSelectedItem();
    		if(selectedTeamObject != null)
    		{
    			refreshListView(teamPlayersView, "Player", "teamName", selectedTeamObject.getName());
        		selectedTeamManagerName.setText(selectedTeamObject.getManager());
        		selectedTeamLeagueName.setText(selectedTeamObject.getLeague());
    		}
    		
        });
        
        leagueSelectDropdown.setOnAction(e ->
        {
        	League leagueObject =  leagueSelectDropdown.getSelectionModel().getSelectedItem();
    		if(leagueObject != null)
    		{
    			refreshListView(leagueTeamsView, "Team", "leagueName", leagueObject.getName());

    		}
        });
        
        leagueRemoveButton.setOnAction(e -> 
        {
        	League leagueToRemove = leagueRemoveDropDown.getSelectionModel().getSelectedItem();
        	if(leagueToRemove != null)
        	{
        		controllerReference.processLeagueRemoval(leagueToRemove);	
            	refreshDropdown(teamSelectDropdown, "Team");
            	refreshDropdown(teamRemoveDropDown, "Team");
            	refreshDropdown(playerTeamDropdown, "Team");
            	refreshDropdown(teamLeagueDropdown, "League");
            	refreshDropdown(leagueRemoveDropDown, "League");
            	refreshDropdown(leagueSelectDropdown, "League");
            	leagueSelectDropdown.getSelectionModel().selectFirst();
            	League updatedLeagueObject = leagueSelectDropdown.getSelectionModel().getSelectedItem();
            	if(updatedLeagueObject != null)
            	{
            		refreshListView(leagueTeamsView, "Team", "leagueName", updatedLeagueObject.getName());
            	}
            	else 
            	{
            		leagueTeamsView.getItems().clear();
    			}
        	}
        });
        
        teamRemoveButton.setOnAction(e ->
        {
        	Team teamToRemove = teamRemoveDropDown.getSelectionModel().getSelectedItem();
        	if(teamToRemove != null)
        	{
        		controllerReference.processTeamRemoval(teamToRemove);
        		refreshDropdown(playerTeamDropdown, "Team");
        		refreshDropdown(teamSelectDropdown, "Team");
        		refreshDropdown(teamRemoveDropDown, "Team");
        		selectedTeamManagerName.clear();		
        		selectedTeamLeagueName.clear();
        		refreshListView(teamPlayersView, "Player", "teamName", teamToRemove.getName());
        		refreshListView(leagueTeamsView, "Team", "leagueName", teamToRemove.getLeague());
        		leagueSelectDropdown.getSelectionModel().selectFirst();
            	League updatedLeagueObject = leagueSelectDropdown.getSelectionModel().getSelectedItem();
            	if(updatedLeagueObject != null)
            	{
            		refreshListView(leagueTeamsView, "Team", "leagueName", updatedLeagueObject.getName());
            	}
            	else 
            	{
            		leagueTeamsView.getItems().clear();
    			}
        	}
        });
        
        managerRemoveButton.setOnAction(e ->
        {
        	Manager managerToRemove = managerRemoveDropDown.getSelectionModel().getSelectedItem();
        	if(managerToRemove != null)
        	{
        		controllerReference.processManagerRemoval(managerToRemove);
            	refreshDropdown(teamManagerDropdown, "Manager");
            	refreshDropdown(managerRemoveDropDown, "Manager");
        		refreshAllElementsListView(managersListView, "Manager", "name");
        	}
        });
        
        playerRemoveButton.setOnAction(e ->
        {
        	Player playerToRemove = playerRemoveDropDown.getSelectionModel().getSelectedItem();
        	if(playerToRemove != null)
        	{
        		controllerReference.processPlayerRemoval(playerToRemove);
            	refreshDropdown(playerRemoveDropDown, "Player");
            	teamSelectDropdown.getSelectionModel().selectFirst();
        		Team updatedTeamObject = teamSelectDropdown.getSelectionModel().getSelectedItem();
        		if(updatedTeamObject != null)
        		{
        			refreshListView(teamPlayersView, "Player", "teamName", updatedTeamObject.getName());
        			selectedTeamManagerName.setText(updatedTeamObject.getManager());
        			selectedTeamLeagueName.setText(updatedTeamObject.getLeague());
        		}
        		else 
        		{
        			teamPlayersView.getItems().clear();
    				selectedTeamManagerName.clear();
    				selectedTeamLeagueName.clear();
    			}
        		
        	}
        	
        });
        
        sortByAlphabetButton.setOnAction(e -> 
        {
        	refreshAllElementsListView(managersListView, "Manager", "name");
        });
        
        sortByRatingButton.setOnAction(e ->
        {
        	refreshAllElementsListView(managersListView, "Manager", "rating");
        });
        
        playerSearchButton.setOnAction(e ->
        {
        	String searchedPlayerName = playerSearchTextField.getText();
        	List searchedPlayerObjects = controllerReference.getConditional("Player", "name", searchedPlayerName);
        	if(searchedPlayerObjects.size() > 1)
        	{
        		System.out.println("Multiple player objects found");
        	}
        	else 
        	{
        		if(searchedPlayerObjects.size() > 0 && searchedPlayerObjects.get(0) != null)
        		{
        			Player searchedPlayerObject = (Player) searchedPlayerObjects.get(0);
        			List playerManagerObjects = controllerReference.getConditional("Manager", "teamName", searchedPlayerObject.getTeam());
        			if(playerManagerObjects.size() > 0)
        			{
            			searchedManagerNameTextField.setText(((Manager) playerManagerObjects.get(0)).getName());

        			}
        			searchedPlayerNameTextField.setText(searchedPlayerObject.getName());
        			searchedPlayerEmailTextField.setText(searchedPlayerObject.getEmail());
        			searchedPlayerPhoneTextField.setText(searchedPlayerObject.getPhone());
        			if(searchedPlayerObject.getGoalieStatus())
        			{
        				searchedGoalieCheckBox.setSelected(true);
        			}
        			else 
        			{
        				searchedGoalieCheckBox.setSelected(false);
					}
        			searchedGoalsTextField.getValueFactory().setValue(searchedPlayerObject.getNumGoals());
				}
			}
        });
        
        saveEditButton.setOnAction(e -> 
        {
        	String playerToRemove = playerSearchTextField.getText();
        	List searchedPlayerObjects = controllerReference.getConditional("Player", "name", playerToRemove);
        	if(searchedPlayerObjects.size() > 1)
        	{
        		System.out.println("Multiple player objects found");
        	}
        	else 
        	{
        		if(searchedPlayerObjects.size() > 0 && searchedPlayerObjects.get(0) != null)
        		{
        			Player searchedPlayerObject = (Player) searchedPlayerObjects.get(0);
        			String newPlayerName = searchedPlayerNameTextField.getText();
        			String newPlayerPhone = searchedPlayerPhoneTextField.getText();
        			String newPlayerEmail = searchedPlayerEmailTextField.getText();
        			String newPlayerTeam = searchedPlayerObject.getTeam();
        			int newPlayerNumGoals = searchedGoalsTextField.getValue();
        			boolean newPlayerGoalie = false;
        			if(searchedGoalieCheckBox.isSelected())
        			{
        				newPlayerGoalie = true;
        			}
        			controllerReference.processPlayerRemoval(searchedPlayerObject);
        			if(newPlayerName.length() > 0 && newPlayerName.contains(" ") && newPlayerEmail.length() > 0 && newPlayerPhone.length() > 0)
                	{
                		Player playerObject = new Player(newPlayerName, newPlayerPhone, newPlayerEmail, newPlayerNumGoals, newPlayerGoalie);
                    	controllerReference.processPlayer(playerObject, newPlayerTeam);
                		refreshDropdown(playerRemoveDropDown, "Player");
                		teamSelectDropdown.getSelectionModel().selectFirst();
                		Team updatedTeamObject = teamSelectDropdown.getSelectionModel().getSelectedItem();
                		if(updatedTeamObject != null)
                		{
                			refreshListView(teamPlayersView, "Player", "teamName", updatedTeamObject.getName());
                			selectedTeamManagerName.setText(updatedTeamObject.getManager());
                			selectedTeamLeagueName.setText(updatedTeamObject.getLeague());
                		}
                		else 
                		{
            				selectedTeamManagerName.clear();
            				selectedTeamLeagueName.clear();
            			}
                		playerSearchTextField.setText(playerObject.getName());
                		searchedPlayerNameTextField.clear();
            			searchedPlayerPhoneTextField.clear();
            			searchedManagerNameTextField.clear();
            			searchedPlayerEmailTextField.clear();
            			searchedGoalsTextField.getValueFactory().setValue(0);
        				searchedGoalieCheckBox.setSelected(false);
        				refreshDropdown(playerRemoveDropDown, "Player");
                	}
        		}
        	}
        });
        
        overloadMemoryButton.setOnAction(e -> 
        {
        	List objectsList = DBOperations.getAll("League");
        	while(true)
        	{
        		objectsList.add(new League("leagueObject"));
        	}
        });
	}
}
