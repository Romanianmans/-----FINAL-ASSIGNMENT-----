import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;


public class DailyFitnessTracker extends Application{ 

    public static void main(String[] args) throws Exception {
        Scanner reader = new Scanner (System.in);
        Stage stage = new Stage();

        String displayUserInformation = "1";
        String calculateNewBMI = "2";
        String calculateCalories = "3";
        String displayProgressGraph = "4";
        String quit = "5";
        
        String[] dateInfo = fetchDate();
        String quote = dailyQuote();
        System.out.println(quote);

        String name = collectName();
        Boolean filecheck = doesFileExist(name);

        double height = 0;
        String gender = "";
        double met = 0;
        int weight = 0;
        int time = 0;
        int bDayYear = 0;
        int bDayMonth = 0;
        int bDayDay = 0;
        double bmi = 0;
        int age = 0;
        String userInput = "";
        
    
        if (filecheck == true){
            height = collectHeight();
            weight = collectWeight();
            met = collectMET();
            time = collectWorkoutTime();
            bmi = bmiCalculator(weight, height);
            age = ageCalculator(bDayYear, bDayMonth, bDayDay, dateInfo);
            bDayYear = fileReadBDayYear(name);
            bDayMonth = fileReadBDayMonth(name);
            bDayDay = fileReadBDayDay(name);
            age = ageCalculator(bDayYear, bDayMonth, bDayDay, dateInfo);
            existingFileWrite(name, dateInfo, bDayYear, bDayMonth, bDayDay, gender, height, weight, bmi);
        }
        else{
            height = collectHeight();
            gender = collectGender();
            met = collectMET();
            weight = collectWeight();
            time = collectWorkoutTime();
            bDayYear = collectBdayYear();
            bDayMonth = collectBdayMonth();
            bDayDay = collectBdayDay(bDayMonth);
            bmi = bmiCalculator(weight, height);
            age = ageCalculator(bDayYear, bDayMonth, bDayDay, dateInfo);
            newUserFile(name, dateInfo, bDayYear, bDayMonth, bDayDay, gender, height, weight, bmi);
        }
        
        
        menu ();
        

        do {
            userInput = reader.nextLine();
            if (userInput.equals(displayUserInformation)) {
                // the methods for user input
            }
            else if (userInput.equals(calculateNewBMI)) {
                avgCalculator(gender, age, bmi);
            }
            else if (userInput.equals(calculateCalories)) {
                // the methods for user inputting calories and calculating
                caloriesCalculator(time, met, weight);
            }
            else if (userInput.equals(displayProgressGraph)) {
                // the methods for graph
                launch(args);
                List<Double> bmiList = fileReadBMI(name);
                exportAsLineChart(bmiList, stage);
                start(stage);
            }
        } while (!userInput.equals(quit));
    }
    public static void menu () {
        System.out.println("Select an Option:");
        System.out.println("1. Display User Information");
        System.out.println("2. Calculate New BMI");
        System.out.println("3. Calculate Calories");
        System.out.println("4. Display Progress Graph");
        System.out.println("5. Quit \n");
        System.out.println("Enter menu option (1-5)");
      }
    /**
     * @Description Collects the users name
     * @Author Robert Todica
     * @return Returns a variable with users name
     */
    public static String collectName(){
        Scanner sc = new Scanner(System.in); //Initilize scanner

        System.out.println("What is your first name?");  //Asks user for first name
        String info = sc.nextLine();  //Saves name as string variable info

        return info;
    }
    /**
     * @Description Collects the users height
     * @Author  Robert Todica
     * @return Returns a variable with the users height0
     */
    public static double collectHeight(){
        Scanner sc = new Scanner(System.in); //Initilize scanner

        System.out.println("What is your height in meters?");  //Asks user for first name
        double height = sc.nextDouble();  //Saves height as string variable height

        return height;
    }
    /**
     * @Description Collects the users gender
     * @Author Robert Todica
     * @return Returns a variable with the users height
     */
    public static String collectGender(){
        Scanner sc = new Scanner(System.in); //Initilize scanner
        String gender = "";

        Boolean continueAsking = true; //Sets boolean continueAsking to true (used for looping if userinput is invalid)
        while(continueAsking) //While loop for when boolean continueAsking is true 
        {
            System.out.println("What is your gender (male/female?)"); //Asks user for gender
            gender = sc.nextLine(); //Saves gender as variable gender

            if(gender.equals("male")){ //If users input is "male"
                continueAsking = false; //Valid answer and while loop stops
            }
            else if(gender.equals("female")){//If users input is "female"
                continueAsking = false;//Valid answer and while loop stops
            }
            else{ //If users input is not "male" or "female"
                System.out.println("INVALID INPUT"); //Input is invalid 
                continueAsking = true; //continueAsking is set to true so it loops the question
            }
        }
        return gender;
    }
    /**
     * @Description Asks the user if they know what an met is / Asks user for their MET value / educates the user if they dont know their MET
     * @Author Robert Todica
     * @return Returns a variable with the users inputted MET value
     * @throws IOException
     */
    public static double collectMET() throws IOException{
        Scanner sc = new Scanner(System.in); //Initilize scanner

        Boolean continueAsking = true; //Sets boolean continueAsking to true (used for looping if userinput is invalid)
        while(continueAsking) //While loop for when boolean continueAsking is true 
        {
            System.out.println("Do you know what a MET value is?"); //Asks user if they know what an MET value is
            String input = sc.next(); //Collects userinput
            if(input.equals("no")) //If user enters "no" (they dont know what an MET is)
            {
                Scanner reader = new Scanner(new File("MET.csv")); //MET.csv file opens and prints out what an MET is and various activities with corresponding MET values
                while(reader.hasNextLine()) //While loop to print out every line in the MET.csv file
                {
                    System.out.println(reader.nextLine()); //Prints every like of the MET.csv file
                }
            }
            else if(input.equals("yes")) //If the user knows what an MET value is or after the MET.csv has been displayed, user is asked what their MET value is
            {
                continueAsking = false; //Sets the boolean to false so it does not loop
            }
            else //If userinput does not satisfy previous conditions the userinput must be false so "INVALID INPUT" is displayed
            {
                System.out.println("INVALID INPUT"); 
            }
        }
        
        System.out.println("What is the MET value for your workout?"); //Asks user what is their MET value
        double metValue = sc.nextDouble(); //MET value saved as variable metValue
        return metValue;
    }
    /**
     * @Author Robert Todica
     * @Description Ask user for their weight 
     * @return Returns a variable with the users weight
     */
    public static int collectWeight(){
        Scanner sc = new Scanner(System.in); //Initilize scanner

        System.out.println("What is your weight in kilograms?"); //Asks user how much they weigh
        int weight = sc.nextInt(); //Users weight is saved in variable "weight"

        return weight;
    }
    /**
     * @Author Robert Todica
     * @Description Asks the user how long their workout will be
     * @return Returns a variable with the users inputted time (in minutes)
     */
    public static int collectWorkoutTime(){
        Scanner sc = new Scanner(System.in); //Initilize scanner

        System.out.println("How long in minutes will your workout be?"); //Asks the user for how long the duration of their workout will be
        int time = sc.nextInt(); //Duration of workout saved in "time" variable

        return time;
    }
    /**
     * @Author Robert Todica
     * @Description Collects the users birth year
     * @return Returns the users birthyear in a variable
     */
    public static int collectBdayYear(){
        Scanner sc = new Scanner(System.in); //Initilize scanner
        int year = 0;

        Boolean continueAsking = true; //Sets boolean "continueAsking" to true agaiun to be used for a loop
        while(continueAsking) //While boolean "continueAsking" is true
        {
            System.out.println("What year were you born in?"); //User is asked what year they were born in
            year = sc.nextInt(); //Collects users birthyear as variable "year"
            if(year <= 1890) //If users birthyear is less than or equal to 1890 then the userinput is invalid
            {
                System.out.println("INVALID INPUT"); //Prints out "INVALID INPUT"
            }
            else //Usersinput satisfies previous birthyear question
            {
                continueAsking = false; //Boolean "continueAsking" is set to false so next code will display
            }
        }

        return year;
    }
    /**
     * @Description Collects the users birth month
     * @Author Robert Todica
     * @return Returns the users birth month as a variable
     */
    public static int collectBdayMonth(){
        Scanner sc = new Scanner(System.in); //Initilize scanner
        int month = 0;

        Boolean continueAsking = true; //Sets continueAsking to true
        while(continueAsking) //While Boolean "continueAsking" is true
        {
            System.out.println("What month were you born in?"); //Asks user for their birthmonth
            month = sc.nextInt(); //Collests usersinput for birthmonth as variable "month"
            if(month < 1 || month > 12) //Userinputted value for month must be at or above one, or at or below 12
            {
                System.out.println("INVALID INPUT"); //If previous conditions are not satisfied,"INVALID INPUT" is displayed
            }
            else //Else if previous requirements are satisfies the boolean is set to false so further code may continue
            {
                continueAsking = false; //Boolean set to false
            }
        }

        return month;
    }
    /**
     * @Author Robert Todica
     * @Description Collect the users Birth day 
     * @param month
     * @return Returns the users birth day in a variable
     */
    public static int collectBdayDay(int month){
        Scanner sc = new Scanner(System.in); //Initilize scanner
        int day = 0;

        Boolean continueAsking = true; //Sets continueAsking to true
        while(continueAsking) //While Boolean "continueAsking" is true
        {
            System.out.println("What day were you born in?"); //Asks user for their birth day
            day = sc.nextInt(); //Saves userinputted value for birth day as variable "day"
            if(dateCheck(month, day)) //This is a later method that checks to make sure userinputted date is correct
            {
                continueAsking = false; //If userinputted dates are correct boolean is set to false so future code may play
            }
            else //If previous userinputted values are invalid
            {
                System.out.println("INVALID INPUT"); //Displays "INVALID INPUT"
            }
        }

        return day;
    }

    
    /**
     * @Author Robert Todica
     * @Description returns true if the day and month are valid, makes sure they input correct day for a correct month
     * @param month
     * @param day
     * @return true/false
     */
    public static boolean dateCheck(int month, int day){ //dateCheck method
        if (month == 2 && day > 0 && day <= 28) //If userinputted month is Febuary, day inputted must be greater than 0 but equal to or less than 28
        {
            return true; //If above checks are satisfied, returns true
        }
        else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 0 && day <= 30) //If userinputted month is April,June,September,November then day inputted must be greater than 0 but equal to or less than 30
        {
            return true; //If above checks are satisfied, returns true
        }
        else if((month != 2 && month != 4 && month != 6 && month != 9 && month != 11) && day > 0 && day <= 31)//If userinputted month is not April,June,September,November or Febuary then day must be greater than 0 but less than or equal to 31
        {
            return true;//If above checks are satisfied, returns true
        }
        return false;//If above checks are not satisfied, returns false
    }

    // /**
    //  * @author Naomi Mezheritsky
    //  * @param args
    //  * @throws Exception
    //  * Desc: Api. Reads BMI
    //  */
    
    // private List<Double> getBMI(String name) throws IOException {
    //     // 1. Get the list of weight and height from the file
    //     // 2. Create the list of bmi where every member is calculated with bmiCalculator()
    //     // 3. return this list
    //     List<Double> bmi = (fileReadBMI(name));
    //     return bmi; // graph will display users input for the BMI
    // }

    /**
     * @author Naomi Mezheritsky
     * @param args
     * Desc: Api. Makes a line chart for the BMI
     */

    private void exportAsLineChart(List<Double> bmiList, Stage stage) {
        stage.setTitle("Daily Fitness Tracker BMI Line Chart"); // title of "application"

        // Create x-axis and y-axis objects
        CategoryAxis xAxis = new CategoryAxis();   // xAxis represents CategoryAxis       
        NumberAxis yAxis = new NumberAxis();       // yAxis represents NumberAxis

        // Create line chart object
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis); 
        lineChart.setTitle("Weekly BMI (weight / height) Fitness Tracker");

        // Set the axis labels
        xAxis.setLabel("Weekly Progress (52 Weeks)");
        yAxis.setLabel("BMI");

         // Add the series object (data) to the lineChart object
         XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
         for (int i=0; i < bmiList.size(); i++) {
             // makes sure that the user understands that the X-axis is counted by each week 
            series.getData().add(new XYChart.Data<String, Number>("Week " + (i+1), bmiList.get(i)));
        }

        // Sets a dimensions for your scene (screen) in pixels ..
        // .. the dimensions (resolutions) are measured in pixels
        Scene scene = new Scene(lineChart, 800, 600);  
        lineChart.getData().add(series);
 
         // Add scene to the stage, and display it to the screen
         stage.setScene(scene);
         stage.show();
    } 

     /**
     * @author Naomi Mezheritsky
     * @param args
     * @throws Exception
     * Desc: Api. Retrives the data then displays the BMI in a line chart
     */

   // @Override
    public void start(Stage stage, String name) throws Exception {  

        // 1. retrieve data
        List<Double> bmiList = fileReadBMI(name);

        // 2. display chart of BMI as line
        exportAsLineChart(bmiList, stage);
    }    
    
    /**
     * Calculates the age using system date and birthday
     * 
     * @author Anson Sy A Chin
     * @param bDayYear
     * @param bDayMonth
     * @param bDayDay
     * @param dateinfo
     * @return
     */
    public static int ageCalculator (int bDayYear, int bDayMonth, int bDayDay, String[] dateinfo){

        int year = Integer.parseInt(dateinfo [1]); // Changes from String[] to int
        int month = Integer.parseInt(dateinfo [2]);
        int day = Integer.parseInt(dateinfo [3]);

        if (month - bDayMonth >= 0){ // If the difference between system month and birthday is larger or equal to zero
            if (day - bDayDay >= 0){// And your birthday hasn't passed
                return (year - bDayYear)-1;// Difference between years - 1
            }
            else{
                return (year - bDayYear);// Else difference between years
            }

        }
        else{
            return (year - bDayYear)-1;// Difference between years - 1
        }
    }
    
    /**
     * This method reads the information of a specific column in the selected .csv
     * 
     * @param filename is the filename that should be read
     * @return returns an array list with the necessary information 
     * @throws IOException throws Input/Output exceptions 
     */
    public static List<Double> fileReadBMI(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename+".csv")); //Create buffered reader for file
        String line = ""; //Declare variable
        String delimeter = ","; //Set delimiter to ,

        List<Double> bmi = new ArrayList<>(); //Create array list

        while ((line = br.readLine()) != null) { //While there are still lines to be read loop
        String[] column = line.split(delimeter); //Read the file into an array

        String tempbmi = column[10];
        double pastebmi = Double.parseDouble(tempbmi);

        bmi.add(pastebmi); //Add 10th index into array list
        }
        br.close(); //Close buffered reader
        bmi.remove(0);//Remove the column header
        return bmi;
    }

    /**
     * Take birthday year from .csv
     * 
     * @author Anson Sy A Chin
     * @param filename
     * @return
     * @throws IOException
     */
    public static int fileReadBDayYear(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename+".csv")); //Create buffered reader for file
        String line = ""; //Declare variable
        String delimeter = ","; //Set delimiter to ,

        ArrayList<String> listtempBYear = new ArrayList<String>(); //Create array list

        while ((line = br.readLine()) != null) { //While there are still lines to be read loop
        String[] column = line.split(delimeter); //Read the file into an array
        listtempBYear.add(column[4]); //Add 4th index into array list
        }
        br.close(); //Close buffered reader
        String tempbYear = listtempBYear.get(1); //Get index one since 0 is the header 
        int bYear = Integer.parseInt(tempbYear); //Convert to integer
        return bYear; //Return
    }

    /**
     * Take birthday month from .csv
     * 
     * @author Anson Sy A Chin
     * @param filename
     * @return
     * @throws IOException
     */
    public static int fileReadBDayMonth(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename+".csv")); //Create buffered reader for file
        String line = ""; //Declare variable
        String delimeter = ","; //Set delimiter to ,

        ArrayList<String> listtempBMonth = new ArrayList<String>(); //Create array list

        while ((line = br.readLine()) != null) { //While there are still lines to be read loop
        String[] column = line.split(delimeter); //Read the file into an array
        listtempBMonth.add(column[5]); //Add 5th index into array list
        }
        br.close(); //Close buffered reader
        String tempbMonth = listtempBMonth.get(1); //Get index one since 0 is the header 
        int bMonth = Integer.parseInt(tempbMonth); //Convert to integer
        return bMonth; //Return
    }

    /**
     * Take birthday day from .csv
     * 
     * @author Anson Sy A Chin
     * @param filename
     * @return
     * @throws IOException
     */
    public static int fileReadBDayDay(String filename) throws IOException{
        BufferedReader br = new BufferedReader(new FileReader(filename+".csv")); //Create buffered reader for file
        String line = ""; //Declare variable
        String delimeter = ","; //Set delimiter to ,

        ArrayList<String> listtempBDay = new ArrayList<String>(); //Create array list

        while ((line = br.readLine()) != null) { //While there are still lines to be read loop
        String[] column = line.split(delimeter); //Read the file into an array
        listtempBDay.add(column[6]); //Add 6th index into array list
        }
        br.close(); //Close buffered reader
        String tempbDay = listtempBDay.get(1); //Get index one since 0 is the header
        int bDay = Integer.parseInt(tempbDay);
        return bDay; //Return
    }

    /**
     * 
     * @param filename filename that should be read
     * @param dateInfo an array that contains the system date information
     * @param bYear birth year
     * @param bMonth birth month
     * @param bDay birth day
     * @param gender gender
     * @param height inputted height 
     * @param weight inputted weight
     * @param bmi calculated BMI
     * @throws IOException
     */
    public static void existingFileWrite(String filename, String[] dateInfo, int bYear, int bMonth, int bDay, String gender, double height, double weight, double bmi) throws IOException{
        FileWriter pw = new FileWriter(filename+".csv", true);//Initialize FileWriter

        String date = dateInfo [0];
        String year = dateInfo [1];
        String month = dateInfo [2];
        String day = dateInfo [3];

        pw.append("\n"+date+","+year+","+month+","+day+","+bYear+","+bMonth+","+bDay+","+gender+","+height+","+weight+","+bmi);//Append user information

        pw.close();//Close FileWriter
    }

    /**
     * @author Anson Sy A Chin, Robert Todica
     * @return returns selected quote
     * @throws IOException
     */
    public static String dailyQuote() throws IOException{
        Random rand = new Random();
        String filename = "Quotes.csv"; //Sets a string as the file name
        BufferedReader csvReader = new BufferedReader(new FileReader(filename)); //Reads the string file
        String line = csvReader.readLine(); //Sets the first line as the string line
        
        String quotes[] = new String[30];
        int x = 0;
        int quote = rand.nextInt(30);

        while (line != null){ //While line still has something assigned to it
            quotes[x] = line;
            x = x + 1;
            line = csvReader.readLine(); //Advance to the next line
        }
      
        String selectedQuote = quotes[quote]; 
        csvReader.close();

        return selectedQuote;
    }

    /**
     * @author Anson Sy A Chin
     * @param name takes the user's name as it should double as the file's name
     * @return returns a boolean that is either false or true depending on if the file exists
     */
    public static Boolean doesFileExist (String name){

        File filename = new File(name+".csv");

        if (filename.isFile()){
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * @author Anson Sy A Chin
     * @return returns system date in a array
     */
    public static String[] fetchDate(){
        DateTimeFormatter ymd = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        DateTimeFormatter yyyy = DateTimeFormatter.ofPattern("yyyy");
        DateTimeFormatter mm = DateTimeFormatter.ofPattern("MM");
        DateTimeFormatter dd = DateTimeFormatter.ofPattern("dd");
        LocalDateTime now = LocalDateTime.now();

        String dateInfo[] = new String[4];
        dateInfo[0] =  (ymd.format(now));
        dateInfo[1] = (yyyy.format(now));
        dateInfo[2] = (mm.format(now));
        dateInfo[3] = (dd.format(now));
    
        return dateInfo;
    }

    /**
     * @author Anson Sy A Chin
     * @param filename the name of the file that should be made
     * @throws IOException
     */
    public static void newUserFile (String filename, String[] dateInfo, int bYear, int bMonth, int bDay, String gender, double height, double weight, double bmi) throws IOException{
        PrintWriter writer = new PrintWriter(new File(filename+".csv")); 
        StringBuilder sb = new StringBuilder(); 

        String date = dateInfo [0]; //Assign index to string
        String year = dateInfo [1];
        String month = dateInfo [2];
        String day = dateInfo [3];
        
        sb.append("Date,Year,Month,Day,Byear,Bmonth,Bday,Gender,Height,Weight,BMI\n");//Set up columns 
        sb.append(date+","+year+","+month+","+day+","+bYear+","+bMonth+","+bDay+","+gender+","+height+","+weight+","+bmi);//append user info *

        writer.write(sb.toString());//write to file

        writer.close();//close writer
    }

    /**
     * calculates BMI of the user using weight and height values,
     * with the formula (weight / height * height) and declares 
     * what their BMI value means. Ex. healthy, overweight, etc.
     * @author Ayeh Fartousi
     * @param weight - the weight of the user, inputted previously
     * @param height - the height of the user, inputted previously
     * @return BMI - the calculated BMI value for the user
     */
    public static double bmiCalculator (double weight, double height) {    
        double bmi = weight/(height*height);  // calculates BMI with formula
        System.out.println("Your BMI is " + String.valueOf(bmi).substring(0, 4));   // prints rounded BMI value
        
        // announces what the BMI value of the user means
        if (bmi > 50 || bmi < 10) {
            System.out.println("That doesn't sound right.");
            System.out.println("Please ensure that the correct information has been inputted.");
        }
        else if (bmi < 18) {
            System.out.println("That means you are underweight.");
        }
        else if (bmi >= 18 && bmi <= 24) {
            System.out.println("That means you are healthy.");
        }
        else if (bmi > 24 && bmi <= 29) {
            System.out.println("That means you are overweight.");
        }
        else if (bmi > 29 && bmi <= 39) {
            System.out.println("That means you are obese.");
        }
        else if (bmi > 39) {
            System.out.println("That means you are extremely obese.");
        }
        else {  // if BMI is above 50
            System.out.println("Are you okay?");
            System.out.println("That doesn't sound right.");
            System.out.println("Please ensure that the correct information has been inputted.");
        }
        return bmi; //returns the user bmi value
     }


    /**
     * calculating calories burned and storing it in a value at user input
     * uses formula (Duration of physical activity in minutes × 
     * (MET × 3.5 × your weight in kg) / 200 = Total calories burned) 
     * @author Ayeh Fartousi
     * @param time - duration of the physical activity in mins
     * @param met - the met value of the physical activity completed
     * @param weight - the weight of the user in kg
     * @return calories burned in inputted time
     */
     public static double caloriesCalculator (int time, double met, double weight) {
        double cals = time * (met * 3.5 * weight) / 200;    // uses calorie formula
        cals = cals*100;
        cals = Math.round(cals);                            // rounds calories to a few decimal places
        cals = cals /100;

        System.out.println("You have burned "+ cals + " calories!");   
        return cals;                                        // returns calorie value
     }


    /**
     * compares the user's BMI to the average, based on age and gender
     * uses files for specific ages of children, and uses a single bmi value for the adult standard
     * @author Ayeh Fartousi
     * @param gender - the gender of the user, used to compare to same gender people
     * @param age - how old the user is, used to compare to people the same age
     * @param bmi - the bmi of the user, used to compare to the average bmi based on above criteria
     * @return average BMI of people the same gender and age range as user
     */
     public static double avgCalculator (String gender, int age, double bmi) {
        double avg = 0;

        //    AGES:                5 ,   6 ,   7,    8 ,   9 ,  10 ,   11,   12,   13,   14,   15,   16,   17,   18,   19
        double [] boyBMIavg =   {15.3, 15.3, 15.5, 15.8, 16.1, 16.5, 17.0, 17.6, 18.3, 19.1, 19.8, 20.6, 21.2, 21.8, 22.2};
        double [] girlBMIavg =  {15.2, 15.3, 15.4, 15.7, 16.1, 16.7, 17.3, 18.1, 18.9, 19.6, 20.3, 20.6, 21.1, 21.3, 21.4};
        
        // C H I L D R E N 
        if (age < 19) {                                         
            if (gender.equals("male")) { 
                avg = boyBMIavg [age -5];                       // the bmi of an average boy based on age is retrieved from the array
            }
            else if (gender.equals("female")) { 
                avg = girlBMIavg [age -5];                      // the bmi of an average girl based on age is retrieved from the array
            }
            else {
                System.out.println("error in gender value");    // in case the gender value is not 'male' or 'female', sorry nonbinaries
            }
        }

        // A D U L T S 
        else {                                                
            if (gender.equals("male")) {
                avg = 26.5;                                     // the bmi of an average adult male is 26.5 
            }
            else if (gender.equals("female")) {
                avg = 26.6;                                     // the bmi of an average adult female is 26.6
            }
            else {
                System.out.println("error in gender value");    // in case the gender value is not 'male' or 'female', sorry nonbinaries
            }
        }

        System.out.println("The average BMI for " + gender + "s at the age of " + age + " is about " + avg );    // announces average
        
        //compares user to average
        double diff = 0;                                    // the difference of user vs average is preset to zero

        // HIGHER BMI THAN AVERAGE
        if (bmi > avg) {
            diff = bmi - avg;                               // difference is calculated
            if (diff <= 0.5) {                              // if they are within .5 of the average, it is considered close enough
                System.out.println("Your BMI, " + String.valueOf(bmi).substring(0, 2) + ", is only a little higher than the average, " + avg );
                System.out.println("You're doing pretty well :)");
            }
            else {                                          // if the gap is large, it is announced
                System.out.println("Your BMI, " + String.valueOf(bmi).substring(0, 2) + ", is much larger than the average, " + avg + ", with a difference of " + String.valueOf(diff).substring(0, 3));
                System.out.println("In comparison to the average, you're a little overweight.");
            }
        }

        // LOWER BMI THAN AVERAGE
        else if (bmi < avg) {
            diff = avg - bmi;                               // difference is calculated
            if (diff <= 0.5) {                              // if they are within .5 of the average, it is considered close enough
                System.out.println("Your BMI, " + String.valueOf(bmi).substring(0, 2) + ", is only a little lower than the average, " + avg );
                System.out.println("You're doing pretty well :)");
            }
            else {                                          // if the gap is large, it is announced
                System.out.println("Your BMI, " + String.valueOf(bmi).substring(0, 2) + ", is much smaller than the average, " + avg + ", with a difference of " + String.valueOf(diff).substring(0, 3));
                System.out.println("In comparison to the average, you're a little underweight.");
            }        
        }

        // SAME BMI AS AVERAGE
        else {                                              // if the difference is zero
            System.out.println("Your BMI, " + String.valueOf(bmi).substring(0, 2) + ", is the exact same as the average, " + avg );
            System.out.println("You're doing great :)");
        }
        return avg;
    }
    @Override
    public void start(Stage arg0) throws Exception {
        // TODO Auto-generated method stub
        
    }
}