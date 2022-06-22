package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Archi;
import it.polito.tdp.genes.model.Genes;
import it.polito.tdp.genes.model.Interactions;


public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Integer> getAllChromosome(){
		String sql = "SELECT DISTINCT `Chromosome` "
				+ "FROM genes "
				+ "WHERE `Chromosome` > 0";
		List<Integer> result = new ArrayList<Integer>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getInt("Chromosome"));
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	
	public List<Archi> getArchi(){
		String sql = "SELECT DISTINCT g1.`Chromosome` as c1, g2.`Chromosome` as c2, SUM(DISTINCT i.`Expression_Corr`) as peso "
				+ "FROM interactions i, genes g1, genes g2 "
				+ "WHERE g1.`Chromosome` != g2.`Chromosome` AND g1.`Chromosome` > 0 AND g2.`Chromosome` > 0 AND i.`GeneID1` != i.`GeneID2` AND g1.`GeneID` = i.`GeneID1` AND g2.`GeneID` = i.`GeneID2` "
				+ "GROUP BY g1.`Chromosome`, g2.`Chromosome`";
		List<Archi> result = new ArrayList<Archi>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				Archi a = new Archi(res.getInt("c1"), res.getInt("c2"), res.getDouble("peso"));
				result.add(a);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
	


	
}
