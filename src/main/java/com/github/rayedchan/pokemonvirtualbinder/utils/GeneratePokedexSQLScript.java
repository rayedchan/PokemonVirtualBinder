package com.github.rayedchan.pokemonvirtualbinder.utils;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rayedchan
 */
public class GenerateSQLScript
{
    private static final Logger LOGGER =  Logger.getLogger(GenerateSQLScript.class.getName());
    
    public static void main(String[] args)
    {
        PrintWriter writer = null;
                
        try
        {
            
            
    
            // Get current working directory of project
            String currentDirectory = System.getProperty("user.dir");
            System.out.println("Working Directory = " + currentDirectory);
        
            String fileName = "sqlscripts/Pokedex.sql"; // Relative to project
            writer = new PrintWriter(fileName, "UTF-8");
            
            writer.println("Hello2");
        } 
        
        catch (FileNotFoundException | UnsupportedEncodingException ex)
        {
           LOGGER.log(Level.SEVERE, null, ex);
        }
        
        finally
        {
            if(writer != null)
            {
                writer.close();
            }
        }
    }
}
