/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.dao;

import bd.ConexaoDB;
import br.senac.sp.model.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import static java.sql.Statement.RETURN_GENERATED_KEYS;
import java.util.ArrayList;

/**
 *
 * @author Gabriel
 */
public class ProductDAO {

    public int addNewProduct(Product prod) throws SQLException, ClassNotFoundException {

        String sql = "Insert into products(name_prod,long_name,amount_stars,status_prod,stock,price,date_register) values (?,?,?,?,?,?,sysdate());";
        int idProd = 0;
        try (Connection conn = ConexaoDB.abrirConexao()) {
            // DESLIGAR AUTO-COMMIT -> POSSIBILITAR DESFAZER OPERACOES EM CASOS DE ERROS
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setString(1, prod.getProductName());
                stmt.setString(2, prod.getProductFullName());
                stmt.setString(3, String.valueOf(prod.getStars()));
                stmt.setString(4, prod.getStatus());
                stmt.setString(5, String.valueOf(prod.getQuantity()));
                stmt.setString(6, String.valueOf(prod.getPrice()));
                stmt.executeUpdate();

                ResultSet generatedKeys = stmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    idProd = generatedKeys.getInt(1);
                    prod.setProductId(generatedKeys.getInt(1));

                } else {
                    throw new SQLException("Falha ao obter o código do Pedido.");
                }


                conn.commit();

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        }
        return idProd;
    }

    public ArrayList<Product> findProduct() {
        String sql = "select * from products_list limit 3";
        ArrayList<Product> prodBd = new ArrayList<>();

        try (Connection conn = ConexaoDB.abrirConexao(); // abre e fecha a conexão
                PreparedStatement stmt = conn.prepareStatement(sql); ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {// enquanto tiver empresas adiciona no array

                Product prod = new Product();
                prod.setProductId(rs.getInt("prod_id"));
                prod.setProductName(rs.getString("name_prod"));
                prod.setQuantity(rs.getInt("stock"));
                prod.setStatus(rs.getString("status_prod"));
                prodBd.add(prod);
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return prodBd;
    }

    public Product findProductById(int idProd) throws SQLException, ClassNotFoundException {
        String sql = "select * from products where prod_id = ?";
        Product prod = new Product();
        try (Connection conn = ConexaoDB.abrirConexao(); // abre e fecha a conexão
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setInt(1, idProd);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {

                    prod.setProductId(rs.getInt("prod_id"));
                    prod.setProductName(rs.getString("name_prod"));
                    prod.setProductFullName(rs.getString("long_name"));
                    prod.setStars(rs.getInt("amount_stars"));
                    prod.setStatus(rs.getString("status_prod"));
                    prod.setQuantity(rs.getInt("stock"));
                    prod.setPrice(rs.getDouble("price"));

                }

            }
        }
        return prod;
    }

    public void updateProduct(Product prod) throws ClassNotFoundException, SQLException {
        String sql = " update products set name_prod =?,long_name =?,amount_stars =?,stock =?,price=? where prod_id =?;";

        try (Connection conn = ConexaoDB.abrirConexao(); // abre e fecha a conexão
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, prod.getProductName());
            stmt.setString(2, prod.getProductFullName());
            stmt.setInt(3, prod.getStars());
            stmt.setInt(4, prod.getQuantity());
            stmt.setDouble(5, prod.getPrice());
            stmt.setInt(6, prod.getProductId());
            stmt.executeUpdate();
        }
    }

    public ArrayList<Product> findProduct(String pesquisa) {
        String sql = "select * from products where name_prod like '%"+pesquisa+"%'";
        ArrayList<Product> prodBd = new ArrayList<>();

        try (Connection conn = ConexaoDB.abrirConexao(); // abre e fecha a conexão
                PreparedStatement stmt = conn.prepareStatement(sql);) {


            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {// enquanto tiver empresas adiciona no array

                    Product prod = new Product();
                    prod.setProductId(rs.getInt("prod_id"));
                    prod.setProductName(rs.getString("name_prod"));
                    prod.setQuantity(rs.getInt("stock"));
                    prod.setStatus(rs.getString("status_prod"));
                    prodBd.add(prod);
                }
            }

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return prodBd;
    }

    public void StatusUpdate(int productId, String newStatus) {
        String sql = "  update products set status_prod = ? where prod_id =?";

        try (Connection conn = ConexaoDB.abrirConexao(); // abre e fecha a conexão
                PreparedStatement stmt = conn.prepareStatement(sql);) {

            stmt.setString(1, newStatus);
            stmt.setInt(2, productId);
            stmt.executeUpdate();

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    public ArrayList<Product> findProductPaginationProx(int lastIdPag) {
        String sql = "select * from products_list where prod_id >? limit 3";
        ArrayList<Product> prodBd = new ArrayList<>();

        try (Connection conn = ConexaoDB.abrirConexao(); // abre e fecha a conexão
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, lastIdPag);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {// enquanto tiver empresas adiciona no array

                    Product prod = new Product();
                    prod.setProductId(rs.getInt("prod_id"));
                    prod.setProductName(rs.getString("name_prod"));
                    prod.setQuantity(rs.getInt("stock"));
                    prod.setStatus(rs.getString("status_prod"));
                    prodBd.add(prod);
                }
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return prodBd;
    }

    public ArrayList<Product> findProductPaginationPrev(int firstId) {
        String sql = "select * from products_list where prod_id <? limit 3";
        ArrayList<Product> prodBd = new ArrayList<>();

        try (Connection conn = ConexaoDB.abrirConexao(); // abre e fecha a conexão
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            stmt.setInt(1, firstId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {// enquanto tiver empresas adiciona no array

                    Product prod = new Product();
                    prod.setProductId(rs.getInt("prod_id"));
                    prod.setProductName(rs.getString("name_prod"));
                    prod.setQuantity(rs.getInt("stock"));
                    prod.setStatus(rs.getString("status_prod"));
                    prodBd.add(prod);
                }
            }
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }

        return prodBd;
    }

    public int LastItemId() {
        String sql = "select prod_id from products ORDER BY prod_id DESC LIMIT 1;";
        int id = 0;
        try (Connection conn = ConexaoDB.abrirConexao(); // abre e fecha a conexão
                PreparedStatement stmt = conn.prepareStatement(sql);) {
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {// enquanto tiver empresas adiciona no array
                    id = rs.getInt("prod_id");

                }
            }

        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return id;

    }

}
