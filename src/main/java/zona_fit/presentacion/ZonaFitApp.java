package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.List;
import java.util.Scanner;

public class ZonaFitApp {
    public static void main(String[] args) {
        zonaFitApp();
    }

    private static void zonaFitApp() {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        // Creamos un objeto de la clase clienteDao
        IClienteDAO clienteDAO = new ClienteDAO();
        while (!salir) {
            try {
                int opcion = mostrarMenu(sc);
                salir = ejecutarOpciones(sc,clienteDAO,opcion);
            }catch (Exception e) {
                System.out.println("Error al ejectuar opciones: "+e.getMessage());
            }
            System.out.println();
        }
    }

    private static int mostrarMenu(Scanner sc) {
        System.out.print("""
                *** Zona Fit (GYM)
                1. Listar Cliente 
                2. Buscar Cliente
                3. Agregar Cliente
                4. Modificar Cliente
                5. Eliminar Cliente
                6. Salir
                Elige una opcion:\s""");
        return sc.nextInt();
    }

    private static boolean ejecutarOpciones(Scanner sc, IClienteDAO clienteDAO,int opcion) {
        boolean salir = false;
        switch (opcion) {
            case 1 -> {// 1. Listar Clientes
                System.out.println("--- Listado de Clientes ---");
                List<Cliente> clientes = clienteDAO.listarClientes();
                clientes.forEach(System.out::println);
            }
            case 2 ->{ //2. Buscar cliente por id
                System.out.print("Digite el id del cliente a buscar: ");
                int idCliente = sc.nextInt();
                Cliente cliente = new Cliente(idCliente);
                boolean encontrado = clienteDAO.buscarClientePorId(cliente);
                if (encontrado) {
                    System.out.println("Cliente encontrado: "+cliente);
                }else{
                    System.out.println("Cliente no encontrado: "+cliente);
                }
            }
            case 3 -> { // 3. Agregar Cliente
                System.out.println("--- Agregar Cliente ---");
                System.out.print("Nombre: ");
                String nombreCliente = sc.next();
                System.out.print("Apellido: ");
                String apellidoCliente = sc.next();
                System.out.print("Membresia: ");
                int membresia = sc.nextInt();

                Cliente cliente = new Cliente(nombreCliente, apellidoCliente, membresia);
                boolean agregado = clienteDAO.agregarCliente(cliente);
                if (agregado) {
                    System.out.println("El cliente fue agregado correctamente: "+cliente);
                }else{
                    System.out.println("El cliente no fue agregado: "+cliente);
                }
            }
            case 4 ->{// 4. Modificar Cliente
                System.out.println("--- Modificar Cliente ---");
                System.out.print("Digite el id del cliente a editar: ");
                int idCliente = sc.nextInt();
                System.out.print("Nombre: ");
                String nombreCliente = sc.next();
                System.out.print("Apellido: ");
                String apellidoCliente = sc.next();
                System.out.print("Membresia: ");
                int membresia = sc.nextInt();

                Cliente cliente = new Cliente(idCliente,nombreCliente, apellidoCliente, membresia);
                boolean modificar = clienteDAO.modificarCliente(cliente);
                if (modificar){
                    System.out.println("Cliente modificado correctamente: "+cliente);
                }else{
                    System.out.println("Cliente no encontrado: "+cliente);
                }
            }
            case 5 ->{ // 5. Eliminar Cliente
                System.out.println("--- Eliminar Cliente ---");
                System.out.print("Digite el id del cliente a eliminar: ");
                int idCliente = sc.nextInt();

                Cliente cliente = new Cliente(idCliente);
                boolean eliminar = clienteDAO.eliminarCliente(cliente);
                if (eliminar){
                    System.out.println("El cliente se elimino: "+cliente);
                }else{
                    System.out.println("Error al eliminar cliente: "+cliente);
                }
            }
            case 6 ->{ // 6. Salir
                System.out.println("Saliendo...");
                salir = true;
            }
            default -> System.out.println("Opcion no reconocida: "+opcion);
        }
        return salir;
    }
}
