package co.edu.uniquindio.poo.empresajfx.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import java.util.ArrayList;


public class Empresa {
    private String nombre;
    private Collection<Cliente> clientes;
    private List<Producto> productos=new ArrayList<>();

    public Empresa(String nombre) {
        this.nombre = nombre;
        clientes = new LinkedList<>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Collection<Cliente> getClientes() {
        return clientes;
    }

    public void setClientes(Collection<Cliente> clientes) {
        this.clientes = clientes;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

    public boolean agregarCliente(Cliente cliente) {
        boolean centinela = false;
        if (!verificarCliente(cliente.getCedula())) {
            clientes.add(cliente);
            centinela = true;
        }
        return centinela;
    }

    public boolean eliminarCliente(String cedula) {
        boolean centinela = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                clientes.remove(cliente);
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean actualizarCliente(String cedula, Cliente actualizado) {
        boolean centinela = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                cliente.setCedula(actualizado.getCedula());
                cliente.setNombre(actualizado.getNombre());
                cliente.setApellido(actualizado.getApellido());
                centinela = true;
                break;
            }
        }
        return centinela;
    }

    public boolean verificarCliente(String cedula) {
        boolean centinela = false;
        for (Cliente cliente : clientes) {
            if (cliente.getCedula().equals(cedula)) {
                centinela = true;
            }
        }
        return centinela;
    }

    public boolean agregarProducto(Producto producto) {
        if (buscarProducto(producto.getCodigo()).isEmpty()) {
            productos.add(producto);
            return true;
        }
        return false;
    }


    public Optional<Producto> buscarProducto(String codigo) {
        return productos.stream().filter(producto -> producto.getCodigo().equals(codigo)).findFirst();
    }


    public boolean verificarProducto(String codigo) {
        return buscarProducto(codigo).isPresent();
    }


    public boolean actualizarProducto(String codigo, Producto actualizado) {
        Optional<Producto> productoBuscado = buscarProducto(codigo);

        if (productoBuscado.isPresent()) {
            Producto producto = productoBuscado.get();

            producto.setCodigo(actualizado.getCodigo());
            producto.setNombre(actualizado.getNombre());
            producto.setPrecio(actualizado.getPrecio());
            producto.setCantidad(actualizado.getCantidad());

            return true;
        }

        return false;
    }


    public boolean eliminarProducto(String codigo) {
        Optional<Producto> productoBuscado = buscarProducto(codigo);

        if (productoBuscado.isPresent()) {productos.remove(productoBuscado.get());
            return true;
        }

        return false;
    }
}
