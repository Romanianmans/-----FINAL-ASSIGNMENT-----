/**
 * Date: May 20, 2021
 * Name: Ayeh Fartousi
 * Teachter: Mr. Ho
 * Description: My methods
 * 
 * methods that deal with calculations and determining things like which average category the user fits into. 
 * - Using age and gender, calculates which average the user fits into, and will be compared to.
 * - calculating BMI using height and weight through the formula (weight/height^2) and storing it in a value
 * - calculating calories burned and storing it in a value at user input, (or after the use of the timer). uses formula 
 * (Duration of physical activity in minutes × (MET × 3.5 × your weight in kg) / 200 = Total calories burned) with the previously inputted information from other methods. 
 */

 import java.util.Scanner;
 class Ayeh {
     public static void main(String [] args) {
        Scanner reader = new Scanner (System.in);

        /*  PRESET VALUES (FOR TESTING CODE)
        String gender = "female";
        int age = 5;
        int time = 2;           // in minutes
        double met = 6.1;
        double weight = 43;     // in kilograms
        double height = 1.40;   // in meters
        */

        // asks for user input
        System.out.println("age:");
        int age = reader.nextInt();

        System.out.println("gender: (male/female)");
        String gender = reader.nextLine();

        System.out.println("time spent excercising: (minutes)");
        int time = reader.nextInt();           // in minutes

        System.out.println("weight: (kilograms)");
        double weight = reader.nextDouble();     // in kilograms

        System.out.println("height (meters)");
        double height = reader.nextDouble();  // in meters
        
        double met = 6.1;
        
        // runs through methods
        double bmi = bmiCalculator(weight, height);
        System.out.println("");
        caloriesCalculator (time, met, weight);
        System.out.println("");
        avgCalculator (gender, age, bmi);
        System.out.println("\nEnd of Program.\n");   
        reader.close();    
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