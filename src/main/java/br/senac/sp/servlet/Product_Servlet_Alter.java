/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.senac.sp.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import br.senac.sp.dao.*;
import br.senac.sp.model.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel
 */
public class Product_Servlet_Alter extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //int productId = Integer.parseInt(request.getParameter("codProduto"));
        
      
        
        try {   
            ProductDAO dao = new ProductDAO();
            Product result = dao.findProductById(1);
            
            request.setAttribute("res", result);
            request.setAttribute("id", 1);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Form_Alter_Prod.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
        
        
        
    }
  @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String productId = request.getParameter("Product-id");
        String productNameStr = request.getParameter("product-name");
        String longNameStr = request.getParameter("long-name");
        String starsStr = request.getParameter("stars");
        String stockStr = request.getParameter("stock");
        String priceStr = request.getParameter("price");
        
    

        boolean validProductName = (productNameStr.trim().length() > 3 && productNameStr != null);//Valida????o Nome do produto
        System.out.println(validProductName);
        boolean validLongName = (longNameStr.trim().length() <= 2000);

        boolean validStarsValue = starsStr.matches("[0-9]+") && (starsStr.trim().length() <6);
        System.out.println(validStarsValue);

        boolean validStockValue = stockStr.matches("[0-9]+");

        boolean ValidStatus = starsStr.trim().length() > 0;

        boolean validFields = validProductName && validLongName && validStarsValue && validStockValue && ValidStatus;

        if (!validFields) {

            if (!validProductName) {
                request.setAttribute("ProductNameError", "O campo nome do produto deve ter mais de 3 caracters");
            }

            if (!validStarsValue) {
                request.setAttribute("StarsValueError", "Este campo aceita apenas n??meros");
            }

            request.setAttribute("product-name", productNameStr);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/Form_Alter_Prod.jsp");
            dispatcher.forward(request, response);
            return;
        }

        try {
            ProductDAO dao = new ProductDAO();
           Product prod = new Product();
           prod.setProductId(Integer.parseInt(productId));
           prod.setProductName(productNameStr);
           prod.setProductFullName(longNameStr);
           prod.setStars(Integer.parseInt(starsStr));
           prod.setPrice(Double.parseDouble(priceStr));
           prod.setQuantity(Integer.parseInt(stockStr));
            dao.updateProduct(prod);
             System.out.println("deu bom");
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
       
    }

}
