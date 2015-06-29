package com.github.rayedchan.pokemonvirtualbinder.utils;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author rayedchan
 */
public class GeneratePokedexSQLScript
{
    private static final Logger LOGGER =  Logger.getLogger(GeneratePokedexSQLScript.class.getName());
    private static final String POKEMON_NUMBER = "Id";
    private static final String POKEMON_NAME = "Name";
    private static final String [] FILE_HEADER_MAPPING = {POKEMON_NUMBER, POKEMON_NAME};
    private static final String CSV_FILE_INPUT = "others/PokemonList.csv";
    private static final String SQL_SCRIPT_OUTPUT = "sqlscripts/Pokedex.sql";
    private static final String SQL_INSERT_TEMPLATE = "INSERT INTO Pokedex (Id, Name) VALUES (''{0}'',''{1}'');";
    
    public static void main(String[] args)
    {
        Reader reader = null;
        CSVParser csvFileParser = null;
        PrintWriter writer = null;
        Map<String,String> pokedexMap = new LinkedHashMap<>();
                
        try
        {
            // Create reader for CSV file which contains all pokemon
            reader = new FileReader(CSV_FILE_INPUT);
            
            // Create the CSVFormat object with the header mapping; Skip header record
            CSVFormat csvFileFormat = CSVFormat.DEFAULT.withHeader(FILE_HEADER_MAPPING).withSkipHeaderRecord();
            
            // Initialize CSVParser object
            csvFileParser = new CSVParser(reader, csvFileFormat);

            // Get all entries in CSV file
            List<CSVRecord> records = csvFileParser.getRecords();
            
            // Iterate each entry in CSV file and store into Map
            for (CSVRecord record : records) 
            {
                String pokemonId = record.get(POKEMON_NUMBER);
                String pokemonName = record.get(POKEMON_NAME);
                pokedexMap.put(pokemonId, pokemonName);
            }
            
            LOGGER.log(Level.INFO, "Pokedex: {0}", new Object[]{pokedexMap});
            
            // Get current working directory of project
            String currentDirectory = System.getProperty("user.dir");
            LOGGER.log(Level.INFO, "Working Directory: {0}", new Object[]{currentDirectory});
        
            // Used to write to file
            writer = new PrintWriter(SQL_SCRIPT_OUTPUT, "UTF-8");
            
            // Create a SQL INSERT statement for each pokemon
            for(Map.Entry<String, String> entry : pokedexMap.entrySet())
            {
                String id = entry.getKey();
                String name = entry.getValue().replaceAll("'","''");;
                String result = MessageFormat.format(SQL_INSERT_TEMPLATE, new Object[]{id, name});
                writer.println(result);
            }
        } 
        
        catch (FileNotFoundException | UnsupportedEncodingException ex)
        {
            LOGGER.log(Level.SEVERE, null, ex);
        } 
        
        catch (IOException ex) 
        {
            LOGGER.log(Level.SEVERE, null, ex);
        }
        
        finally
        {
            if(reader != null)
            {
                try 
                {
                    reader.close();
                } 
                
                catch (IOException ex)
                {
                    LOGGER.log(Level.SEVERE, null, ex);
                }
            }
            
            if(csvFileParser != null)
            {
                try 
                {
                    csvFileParser.close();
                } 
                
                catch (IOException ex) 
                { 
                    LOGGER.log(Level.SEVERE, null, ex);   
                }
            }
            
            if(writer != null)
            {
                writer.close();
            }
        }
    }
}
