package it.polito.tdp.food.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.food.model.Condiment;
import it.polito.tdp.food.model.Coppie;
import it.polito.tdp.food.model.Food;
import it.polito.tdp.food.model.Portion;

public class FoodDao {
	public List<Food> listAllFoods(){
		String sql = "SELECT * FROM food" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Food> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Food(res.getInt("food_code"),
							res.getString("display_name")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Condiment> listAllCondiments(){
		String sql = "SELECT * FROM condiment" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Condiment> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Condiment(res.getInt("condiment_code"),
							res.getString("display_name"),
							res.getDouble("condiment_calories"), 
							res.getDouble("condiment_saturated_fats")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Portion> listAllPortions(){
		String sql = "SELECT * FROM portion" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Portion> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Portion(res.getInt("portion_id"),
							res.getDouble("portion_amount"),
							res.getString("portion_display_name"), 
							res.getDouble("calories"),
							res.getDouble("saturated_fats"),
							res.getInt("food_code")
							));
				} catch (Throwable t) {
					t.printStackTrace();
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			e.printStackTrace();
			return null ;
		}

	}
	
	public List<Food> dammiFood(int k){
		String sql="SELECT p.food_code, f.display_name, count(p.portion_id) AS conta " + 
				"FROM food f,`portion` p " + 
				"WHERE f.food_code=p.food_code " + 
				"GROUP BY p.food_code "  + 
				"HAVING conta<=? "
				+ "ORDER BY f.display_name ASC";
		List<Food> cibi=new ArrayList<>();
				try {
							Connection conn = DBConnect.getConnection() ;

							PreparedStatement st = conn.prepareStatement(sql) ;
							st.setInt(1, k);
							ResultSet res = st.executeQuery() ;
							
							while(res.next()) {
								cibi.add(new Food (res.getInt("food_code"),res.getString("display_name")));
				}
				conn.close();}
				catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								return null ;
							}
				return cibi;

	}
	
	
	public List<Coppie> dammiCoppie(int k){
		String sql="SELECT  f1.food_code as f1, f2.food_code as f2, AVG (c.condiment_calories) AS peso, COUNT(distinct c.condiment_code) AS numero_condimenti_in_comune\n" + 
				"FROM food f1, food f2, food_condiment fc1, food_condiment fc2, condiment c, `portion` p1, `portion` p2\n" + 
				"WHERE fc1.food_code=f1.food_code\n" + 
				"AND fc2.food_code=f2.food_code\n" + 
				"AND f1.food_code<f2.food_code\n" + 
				"AND fc1.condiment_code=c.condiment_code\n" + 
				"AND fc2.condiment_code=c.condiment_code\n" + 
				"AND fc1.condiment_code=fc2.condiment_code\n" + 
				"AND p1.food_code=f1.food_code\n" + 
				"AND p2.food_code=f2.food_code\n" + 
				"GROUP BY f1.food_code, f2.food_code\n" + 
				"HAVING COUNT(distinct p1.portion_id)<=?\n" + 
				"AND COUNT(distinct p2.portion_id)<=?\n" + 
				"";
		List<Coppie> c=new ArrayList<>();
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
            st.setInt(1,k);
            st.setInt(2, k);
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				Coppie c1=new Coppie(res.getInt("f1"),res.getInt("f2"),res.getInt("peso"));
				c1.setNumCond(res.getInt("numero_condimenti_in_comune"));
				c.add(c1);
}
conn.close();}
catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null ;
			}
		return c;

		
	}
	
	
	
	
}
