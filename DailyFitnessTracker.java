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
}