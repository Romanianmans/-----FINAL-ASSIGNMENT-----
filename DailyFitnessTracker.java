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

class DailyFitnessTracker{
    public static void main(String[] args) throws IOException{
        Scanner reader = new Scanner(System.in);

        System.out.println("---Program Start---");
        String quote = dailyQuote();
        System.out.println(quote);
        String[] dateInfo = fetchDate();

        System.out.println("Please input your name");
        String name = reader.nextLine();

        Boolean test = doesFileExist(name);

        if (test==true){
            System.out.println("Welcome back "+name+"!");
        }
        else{
            newUserFile(name);
        }
        
        reader.close();
        
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