package zona_fit.presentacion;

import zona_fit.datos.ClienteDAO;
import zona_fit.datos.IClienteDAO;
import zona_fit.dominio.Cliente;

import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        IClienteDAO clienteDAO = new ClienteDAO();
        boolean salir = false;
        while (!salir) {
            try {
                int opcion = menu(sc);
                opciones(sc, opcion, clienteDAO);
            }catch (Exception e) {
                System.out.println("Error al ejecutar opciones: "+e.getMessage());
            }
        }
    }

    private static int menu(Scanner sc) {
        System.out.print("""
                \n----- Zona Fit -----
                1. Mostrar Clientes
                2. Buscar Clientes(id)
                3. Agregar Nuevo Cliente
                4. Modificar Cliente
                5. Eliminar Cliente
                6. Salir
                Digite una opcion:\s""");
        return sc.nextInt();
    }

    private static void opciones(Scanner sc, int opcion, IClienteDAO clienteDAO) {
        try {
            switch (opcion) {
                case 1:
                    mostarClientes(clienteDAO);
                    break;
                case 2:
                    buscarCliente(sc, clienteDAO);
                    break;
                case 3:
                    agregarCliente(sc, clienteDAO);
                    mostarClientes(clienteDAO);
                    break;
                case 4:
                    modificarCliente(sc, clienteDAO);
                    mostarClientes(clienteDAO);
                    break;
                case 5:
                    eliminarCliente(sc, clienteDAO);
                    mostarClientes(clienteDAO);
                    break;
                case 6:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opcion no valida");
            }
        }catch (Exception e) {
            System.out.println("Error al digitar la opcion: "+e.getMessage());
            sc.next();
        }
    }

    private static void mostarClientes(IClienteDAO clienteDAO) {
        System.out.println("\n--- Clientes ---");

        List<Cliente> clientes = clienteDAO.listarClientes();

        for (Cliente cliente : clientes) {
            System.out.println(cliente.getIdCliente() + ". Nombre: " + cliente.getNombre() + ", Apellido: " + cliente.getApellido() + ", Membresia: " + cliente.getMembresia());
        }
    }

    private static void buscarCliente(Scanner sc, IClienteDAO clienteDAO) {
        try {
            System.out.print("Digite el (id) del cliente: ");
            int id = sc.nextInt();
            Cliente cliente1 = new Cliente(id);
            boolean encontrado = clienteDAO.buscarClientePorId(cliente1);

            if (encontrado) {
                System.out.println("\nEl cliente con el id " + id + " es: ");
                formatoTexto(cliente1);
            } else {
                System.out.println("\nEl cliente no existe");
            }
        } catch (Exception e) {
            System.out.println("Error al buscar cliente: " + e.getMessage());
            sc.next();
        }
    }

    private static void agregarCliente(Scanner sc, IClienteDAO clienteDAO) {
        try {
            System.out.print("Digite el nombre del cliente: ");
            String nombre = sc.next();
            System.out.print("Digite el apellido del cliente: ");
            String apellido = sc.next();
            System.out.print("Digite la membresia: ");
            int membresia = sc.nextInt();

            Cliente nuevoCliente = new Cliente(nombre, apellido, membresia);

            boolean agregado = clienteDAO.agregarCliente(nuevoCliente);

            if (agregado) {
                System.out.println("\nEl cliente se agrego con exito");
                formatoTexto(nuevoCliente);
            } else {
                System.out.println("\nNo se agrego el cliente");
            }
        } catch (Exception e) {
            System.out.println("Error al agregar Cliente: " + e.getMessage());
            sc.next();
        }
    }

    private static void modificarCliente(Scanner sc, IClienteDAO clienteDAO) {
        try {
            System.out.print("Digite id del cliente: ");
            int id = sc.nextInt();
            System.out.print("Digite nuevo nombre: ");
            String nombre = sc.next();
            System.out.print("Digite nuevo apellido: ");
            String apellido = sc.next();
            System.out.print("Digite la nueva Membresia: ");
            int membresia = sc.nextInt();
            Cliente cliente = new Cliente(id, nombre, apellido, membresia);

            boolean modificado = clienteDAO.modificarCliente(cliente);
            if (modificado) {
                System.out.println("\nEl cliente se modifico con exito");
                formatoTexto(cliente);
            } else {
                System.out.println("\nNo se modifico el cliente");
            }
        } catch (Exception e) {
            System.out.println("Error al modificar Cliente: " + e.getMessage());
            sc.next();
        }
    }

    private static void eliminarCliente(Scanner sc, IClienteDAO clienteDAO) {
        try {
            System.out.print("Digite el id del cliente: ");
            int id = sc.nextInt();

            Cliente clienteEliminar = new Cliente(id);

            boolean eliminado = clienteDAO.eliminarCliente(clienteEliminar);

            if (eliminado) {
                System.out.println("\nEl cliente se elimino con exito");
                formatoTexto(clienteEliminar);
            } else {
                System.out.println("\nNo se elimino el cliente");
            }
        } catch (Exception e) {
            System.out.println("Error al eliminar Cliente: " + e.getMessage());
            sc.next();
        }
    }

    private static void formatoTexto(Cliente cliente) {
        System.out.println("Nombre: " + cliente.getNombre());
        System.out.println("Apellido: " + cliente.getApellido());
        System.out.println("Membresia: " + cliente.getMembresia());
    }

}
