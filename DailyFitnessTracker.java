import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
import java.util.Arrays;
import java.util.List;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

class DailyFitnessTracker{

    public static void main(String[] args) throws Exception {
        launch(args);
    }
    
    /**
     * @Author Robert Todica
     * @param args
     * @throws IOException
     */
    public static void collectInfo(String[] args) throws IOException {
        // Initialise
        Scanner sc = new Scanner(System.in);
        boolean continueAsking;
        int year = 0;
        int month = 0;
        int day = 0;

        // Gather information
        System.out.println("What is your first name?");
        String nofirstName = sc.nextLine();
        System.out.println("What is your height in inches?");
        int height = sc.nextInt();

        continueAsking = true;
        while(continueAsking)
        {
            System.out.println("Do you know what a MET value is?");
            String input = sc.next();
            if(input.equals("no"))
            {
                Scanner reader = new Scanner(new File("MET.csv"));
                while(reader.hasNextLine())
                {
                    System.out.println(reader.nextLine());
                }
            }
            else if(input.equals("yes"))
            {
                continueAsking = false;
            }
            else
            {
                System.out.println("INVALID INPUT");
            }
        }

        System.out.println("What is the MET value for your workout?");
        double metValue = sc.nextDouble();

        System.out.println("What is your weight in pounds?");
        int weight = sc.nextInt();

        continueAsking = true;
        while(continueAsking)
        {
            System.out.println("What year were you born in?");
            year = sc.nextInt();
            if(year <= 1890)
            {
                System.out.println("INVALID INPUT");
            }
            else
            {
                continueAsking = false;
            }
        }

        continueAsking = true;
        while(continueAsking)
        {
            System.out.println("What month were you born in?");
            month = sc.nextInt();
            if(month < 1 || month > 12)
            {
                System.out.println("INVALID INPUT");
            }
            else
            {
                continueAsking = false;
            }
        }

        continueAsking = true;
        while(continueAsking)
        {
            System.out.println("What day were you born in?");
            day = sc.nextInt();
            if(dateCheck(month, day))
            {
                continueAsking = false;
            }
            else
            {
                System.out.println("INVALID INPUT");
            }
        }
    }

    
    /**
     * @Author Robert Todica
     * @Description returns true if the day and month are valid, makes sure they input correct day for a correct month
     * @param month
     * @param day
     * @return true/false
     */
    public static boolean dateCheck(int month, int day){
        if (month == 2 && day > 0 && day <= 28)
        {
            return true;
        }
        else if ((month == 4 || month == 6 || month == 9 || month == 11) && day > 0 && day <= 30)
        {
            return true;
        }
        else if((month != 2 && month != 4 && month != 6 && month != 9 && month != 11) && day > 0 && day <= 31)
        {
            return true;
        }
        return false;
    }

    /**
     * @author Naomi Mezheritsky
     * @param args
     * @throws Exception
     * Desc: Api. Makes a line chart for the BMI
     */

    private List<Double> getBMI() {
        return Arrays.asList(23.6, 4.5, 2.0, 15.3, 62.4, 34.5, 12.4, 34.6, 72.1);
    }

    private void exportAsLineChart(List<Double> bmiList, Stage stage) {
        stage.setTitle("Daily Fitness Tracker BMI Line Chart");

        // Create x-axis and y-axis objects
        CategoryAxis xAxis = new CategoryAxis();        
        NumberAxis yAxis = new NumberAxis();

        // Create line chart object
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Weekly BMI (weight / height) Fitness Tracker");

        // Set the axis labels
        xAxis.setLabel("Weekly Progress (52 Weeks)");
        yAxis.setLabel("BMI");

         // Add the series object (data) to the lineChart object
         XYChart.Series<String, Number> series = new XYChart.Series<String, Number>();
         for (int i=0; i < bmiList.size(); i++) {
            series.getData().add(new XYChart.Data<String, Number>("Week " + (i+1), bmiList.get(i)));
        }

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().add(series);
 
         // Add scene to the stage, and display it to the screen
         stage.setScene(scene);
         stage.show();
    }

    @Override
    public void start(Stage stage) throws Exception {

        // 1. retrieve data
        List<Double> bmiList = getBMI();

        // 2. display chart of BMI as line
        exportAsLineChart(bmiList, stage);
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
            line = csvReader.readLine();//Advance to the next line
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
    public static void newUserFile (String filename) throws IOException{
        PrintWriter writer = new PrintWriter(new File(filename+".csv")); 
        StringBuilder sb = new StringBuilder(); 
        
        sb.append("Date,Year,Month,Day,BYear,BMonth,Bday,Gender,Height,Weight\n");//Set up columns 
        sb.append("a");//append user info *WIP*

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
        System.out.println("Your BMI is " + String.valueOf(bmi).substring(0, 2));   // prints rounded BMI value
        
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
}