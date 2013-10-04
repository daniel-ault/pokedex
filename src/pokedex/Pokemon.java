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
public class Pokemon {
    private int version_id = 1;
    private int local_language_id = 9;
    private int species_id;
    private String name;
    private String flavor_text;
    
    private Connection c;
    
    public Pokemon(int species_id) throws Exception {
        connectToDatabase();
        updatePokemon(species_id);
    }
    
    public int getSpeciesId() { return species_id; };
    public String getName() { return name; }
    public String getFlavorText() { return flavor_text; }
    
    public void updatePokemon(int species_id) throws Exception {
        this.species_id = species_id;
        updateName();
        updateFlavorText();
    }
    
    private void connectToDatabase() throws Exception {
        Class.forName("org.sqlite.JDBC");
        c = DriverManager.getConnection("jdbc:sqlite:veekun-pokedex.sqlite");
    }
    
    private void updateName() throws Exception {
        String query = "SELECT name FROM pokemon_species_names";
        query += " WHERE pokemon_species_id=" + species_id;
        query += " AND local_language_id=" + local_language_id;
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(query);
        
        name = rs.getString("name");
    }
    
    private void updateFlavorText() throws Exception {
        String query = "SELECT flavor_text FROM pokemon_species_flavor_text";
        query += " WHERE species_id=" + species_id + " AND language_id=9";
        query += " AND version_id=" + version_id;
        Statement statement = c.createStatement();
        ResultSet rs = statement.executeQuery(query);
        
        flavor_text = "<html>" + rs.getString("flavor_text") + "</html>";
    }
    
    
}
