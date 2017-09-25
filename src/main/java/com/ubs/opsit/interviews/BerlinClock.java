package com.ubs.opsit.interviews;

import java.util.Arrays;
import java.util.Collections;
import org.apache.commons.lang.StringUtils;

public class BerlinClock implements TimeConverter {
    private String formattedTime;
    private static final String NEW_LINE = System.getProperty("line.separator");
    private static final String NO_TIME_ERROR = "No time provided";
    private static final String INVALID_TIME_ERROR = "Invalid time provided.";
    private static final String NUMERIC_TIME_ERROR = "Time values must be numeric.";

    /**
     * Create a new BerlinClock instance with a string representing time.
     *
     * @param aTime - The 24 hour time in the format of HH:MM:SS
     */
    public String convertTime(String aTime){
      int hours, minutes, seconds = 0;
    
      // Check null input
      if(aTime == null) throw new IllegalArgumentException(NO_TIME_ERROR);
          
      // Split times into hours, minutes and seconds
      String[] times = aTime.split(":", 3);
      
      // Validate the input format
      if(times.length != 3) throw new IllegalArgumentException(INVALID_TIME_ERROR);
      
      // Convert the input to hours, minutes and seconds 
      try {
            hours = Integer.parseInt(times[0]);
            minutes = Integer.parseInt(times[1]);
            seconds = Integer.parseInt(times[2]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(NUMERIC_TIME_ERROR);
        }
      
      if (hours < 0 || hours > 24) throw new IllegalArgumentException("Hours out of bounds.");
      if (minutes < 0 || minutes > 59) throw new IllegalArgumentException("Minutes out of bounds.");
      if (seconds < 0 || seconds > 59) throw new IllegalArgumentException("Seconds out of bounds.");

      this.formattedTime = processTime(hours, minutes, seconds);
    }

    /**
     * Convert individual hours, minutes and seconds into a BerlinTime object.
     * State of each of rows is encoded as a String consisting of characters 'R','Y' and 'O'.
     *
     * @param hours - an int representing Hours
     * @param minutes - an int representing Minutes
     * @param seconds - an int representing Seconds
     *
     * @return BerlinTime object created using the parameters.
     */
    private String processTime(int hours, int minutes, int seconds) {
    	String line1 = formatSecondsRow(seconds);
    	String line2 = formatHours1Row(hours);
    	String line3 = formatHours2Row(hours);
    	String line4 = formatMinutes1Row(minutes);
    	String line5 = formatMinutes2Row(minutes);
	    
	return String.join(NEW_LINE, Arrays.asList(line1, line2, line3, line4, line5)); 
      }
      
    /** Format seconds in line 1
    *
    * @param seconds - an int representing Seconds
    *
    * @return line 1 in BerlinTime object
    */	
    private String formatSecondsRow(int seconds){
	if(seconds%2==0) 
            return "Y"; 
        else 
            return "O";
    }

    /** Format hours in line 2
    *
    * @param hours - an int representing Hours
    *
    * @return line 2 in BerlinTime object
    */
    private String formatHours1Row(int hours){
        int numberOfRedCells = hours / 5;

        return formatRow("R", numberOfRedCells, 4);
    }

    /** Format hours in line 3
    *
    * @param hours - an int representing Hours
    *
    * @return line 3 in BerlinTime object
    */
    private String formatHours2Row(int hours){
    	int numberOfRedCells = hours % 5; 
    	return formatRow("R", numberOfRedCells, 4);
    }

    /** Format minutes in line 4
    *
    * @param minutes - an int representing Minute      
    *
    * @return line 4 in BerlinTime object
    */
    private String formatMinutes1Row(int minutes){
        int numberOfYellowCells = minutes / 5;
   	char[] row = formatRow("Y", numberOfYellowCells, 11).toCharArray();

	changeToRedIfYellow(row, 2);
	changeToRedIfYellow(row, 5);
	changeToRedIfYellow(row, 8);

	return new String(row);
    }

    /** Format minutes in line 5
    *
    * @param minutes - an int representing Minutes
    *
    * @return line 5 in BerlinTime objec      
    */
    private String formatMinutes2Row(int minutes){
    int numberOfRedCells = minutes % 5;

    return formatRow("Y", numberOfRedCells, 4);
	    }

    /** Format rows
    *
    * @param light - a string representing (Y)ellow or (R)ed lamp
    * @param times - an int representing number of lamp lighted up
    * @param length - an int representing number of character in the line
    *
    * @return line in BerlinTime object
    */
    private String formatRow(String light, int times, int length){
	    	return StringUtils.repeat(light, times) + StringUtils.repeat("O", length - times);
	    }

    /** Change the colour from Yellow to Red
    *
    * @param row - a character array representing a line 
    * @param index - an int representing the index in a line
    *
    */
    private void changeToRedIfYellow(char[] row, int index){
    	if(row[index]=='Y') 
            row[index] = 'R';
    }
}
