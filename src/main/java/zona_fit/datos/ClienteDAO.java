package zona_fit.datos;

import zona_fit.conexion.Conexion;
import zona_fit.dominio.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static zona_fit.conexion.Conexion.getConexion;

public class ClienteDAO implements IClienteDAO{

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM cliente ORDER BY idCliente";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setIdCliente(rs.getInt("idCliente"));
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                clientes.add(cliente);
            }
        }catch (Exception e) {
            System.out.println("Error al listar clientes: "+e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }
        return clientes;
    }

    @Override
    public boolean buscarClientePorId(Cliente cliente) {
        PreparedStatement ps;
        ResultSet rs;
        Connection con = getConexion();
        String sql = "SELECT * FROM cliente WHERE idCliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,cliente.getIdCliente());
            rs = ps.executeQuery();
            if(rs.next()) {
                cliente.setNombre(rs.getString("nombre"));
                cliente.setApellido(rs.getString("apellido"));
                cliente.setMembresia(rs.getInt("membresia"));
                return true;
            }
        }catch (Exception e) {
            System.out.println("Error al recuperar cliente por id: "+e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean agregarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "INSERT INTO cliente(nombre,apellido,membresia) VALUES(?,?,?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setInt(3,cliente.getMembresia());
            ps.execute();
            return true;
        }catch (Exception e) {
            System.out.println("Error al agregar cliente: "+e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean modificarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "UPDATE cliente SET nombre = ?,apellido = ?,membresia = ? WHERE idCliente = ?";
        try {

            ps = con.prepareStatement(sql);
            ps.setString(1,cliente.getNombre());
            ps.setString(2,cliente.getApellido());
            ps.setInt(3,cliente.getMembresia());
            ps.setInt(4,cliente.getIdCliente());
            ps.execute();
            return true;
        }catch (Exception e) {
            System.out.println("Error al modificar cliente: "+e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }
        return false;
    }

    @Override
    public boolean eliminarCliente(Cliente cliente) {
        PreparedStatement ps;
        Connection con = getConexion();
        String sql = "DELETE FROM cliente WHERE idCliente = ?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1,cliente.getIdCliente());
            ps.execute();
            return true;
        }catch (Exception e) {
            System.out.println("Error al eliminar cliente: "+e.getMessage());
        }finally {
            try {
                con.close();
            }catch (Exception e) {
                System.out.println("Error al cerrar conexion: "+e.getMessage());
            }
        }
        return false;
    }

    public static void main(String[] args) {
        IClienteDAO clienteDao = new ClienteDAO();

        //listar clientes
//        System.out.println("*** Listar Clientes ***");
//        boolean clientes = clienteDao.listarClientes();
//        clientes.forEach(System.out::println);

        //buscar por id
        Cliente cliente1 = new Cliente(2);
        System.out.println("Cliente antes de la busqueda: "+cliente1);
        boolean encontrado = clienteDao.buscarClientePorId(cliente1);
        if(encontrado) {
            System.out.println("Cliente encontrado: "+cliente1);
        }else{
            System.out.println("No se encontro registro: "+cliente1.getIdCliente());
        }

        //agregar cliente
//        Cliente nuevoCliente = new Cliente("Daniel","Ortiz",300);
//        boolean agregado = clienteDao.agregarCliente(nuevoCliente);
//        if(agregado) {
//            System.out.println("Cliente agregado: "+nuevoCliente);
//        }else{
//            System.out.printf("No se agrego el cliente: "+nuevoCliente);
//        }

        //modificar cliente
//        Cliente modificarCliente = new Cliente(5,"Carlos Daniel","Ortiz",300);
//        boolean modificado = clienteDao.modificarCliente(modificarCliente);
//
//        if (modificado){
//            System.out.println("Cliente modificado: "+modificarCliente);
//        }else{
//            System.out.println("No se modifico el cliente: "+modificarCliente);
//        }


        //Elminar cliente
        Cliente clienteEliminar = new Cliente(5);
        boolean eliminado = clienteDao.eliminarCliente(clienteEliminar);
        if (eliminado){
            System.out.println("Cliente eliminado: "+clienteEliminar);
        }else{
            System.out.println("No se elimino cliente: "+clienteEliminar);
        }

        //listar clientes
        System.out.println("*** Listar Clientes ***");
        var clientes = clienteDao.listarClientes();
        clientes.forEach(System.out::println);

    }
}
