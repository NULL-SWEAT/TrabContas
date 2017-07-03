package br.univel;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaDAO {
	
	public List<Conta> getContas() {
		
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		List<Conta> lista = new ArrayList<>();
		try (PreparedStatement ps = con
					.prepareStatement("SELECT * FROM conta;");
				ResultSet rs = ps.executeQuery()) {
			
			while (rs.next()) {
				Conta c = new Conta();
				c.setId(rs.getInt(1));
				c.setCodigo(rs.getString(2));
				if(rs.getString(3) != null)
					c.setValor(new BigDecimal(rs.getString(3)));
				lista.add(c);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return lista;
	}
	
	public void insert(Conta c) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		
		try (PreparedStatement ps = con.prepareStatement("INSERT INTO conta (codigo, valor) VALUES ('" + c.getCodigo() + "'," + c.getValor() +");"))
		{
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		Connection con = ConexaoDB
				.getInstance()
				.getConnection();
		try (PreparedStatement ps = con.prepareStatement("DELETE FROM conta WHERE id = " + id +";"))
		{
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
