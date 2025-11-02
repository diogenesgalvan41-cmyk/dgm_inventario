package com.mycompany.dgm_inventario;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Dgm_inventario {

    public static void main(String[] args) {
        
        String url = "jdbc:mysql://localhost:3307/inventario_jdbc";
        String username = "root";
        String password = "";

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            Statement stmt = conn.createStatement();
            System.out.println("Conexión establecida con la base de datos");

            stmt.execute("""
                         CREATE TABLE IF NOT EXISTS productos (
                             id INT AUTO_INCREMENT PRIMARY KEY,
                             nombre VARCHAR(100),
                             categoria VARCHAR(50),
                             precio DOUBLE,
                             cantidad INT
                         );
                         """);
            System.out.println("Tabla 'productos' verificada o creada correctamente");

            stmt.execute("INSERT INTO productos (id, nombre, categoria, precio, cantidad) "
                    + "VALUES (NULL, 'Martillo', 'Herramientas', 12500, 10);");
            System.out.println("Producto insertado correctamente");

            obtenerProductos(stmt);

            stmt.executeUpdate("UPDATE productos SET precio = 14500, cantidad = 8 WHERE nombre = 'Martillo';");
            System.out.println("Producto actualizado correctamente");

            obtenerProductos(stmt);

            stmt.execute("DELETE FROM productos WHERE nombre = 'Martillo';");
            System.out.println("Producto eliminado correctamente");

            obtenerProductos(stmt);

        } catch (SQLException ex) {
            Logger.getLogger(Dgm_inventario.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void obtenerProductos(Statement stmt) throws SQLException {
        ResultSet rs = stmt.executeQuery("SELECT * FROM productos");

        System.out.println("LISTADO DE PRODUCTOS:");
        boolean hayDatos = false;

        while (rs.next()) {
            hayDatos = true;
            int id = rs.getInt("id");
            String nombre = rs.getString("nombre");
            String categoria = rs.getString("categoria");
            double precio = rs.getDouble("precio");
            int cantidad = rs.getInt("cantidad");

            System.out.println("ID: " + id + " | Nombre: " + nombre 
                    + " | Categoría: " + categoria 
                    + " | Precio: $" + precio 
                    + " | Cantidad: " + cantidad);
        }

        if (!hayDatos) {
            System.out.println("No hay productos registrados");
        }
        System.out.println("--------------------------------------------------\n");
    }
}
