/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pokedex;

import java.sql.*;

/**
 *
 * @author Daniel
 */
public class Pokedex {

    /**
     * @param args the command line arguments
     */
    public static void main2(String[] args) {
        try {
            //dbTest();
            //String names[] = getPokemonNameList();
            
            //for (String name : names)
                //System.out.println(name);
            System.out.println(getFlavorText(4));
        } catch (Exception e) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
        System.out.println("Opened database successfully!");
        
    }
    
    public static void dbTest() throws Exception {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:veekun-pokedex.sqlite");
        
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery( "select id from pokemon_species where identifier='bellsprout'" );
        
        int local_language_id = 9;
        
        int id = rs.getInt("id");
        
        rs = statement.executeQuery("select name FROM pokemon_species_names where pokemon_species_id=" + id + " AND local_language_id=" + local_language_id);
        
        String name = rs.getString("name");
        
        System.out.println(name);
    }
    
    public static String[] getPokemonNameList() throws Exception {
        String[] names = new String[151];
        
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:veekun-pokedex.sqlite");
        
        String query = "SELECT name FROM pokemon_species_names WHERE local_language_id=9";
        
        Statement statement = c.createStatement();
        ResultSet rs;
        
        for (int i=1; i<=151; i++) {
            rs = statement.executeQuery(query + " AND pokemon_species_id=" + i);
            names[i-1] = rs.getString(1);
        }
        
        c.close();
        return names;
    }
    
    public static String getFlavorText(int speciesID) throws Exception {
        Connection c = null;
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:veekun-pokedex.sqlite");
        
        String query = "SELECT flavor_text FROM pokemon_species_flavor_text WHERE species_id=" + speciesID + " AND language_id=9";
        query += " AND version_id=1";
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(query);
        
        return rs.getString("flavor_text");
    }
}
