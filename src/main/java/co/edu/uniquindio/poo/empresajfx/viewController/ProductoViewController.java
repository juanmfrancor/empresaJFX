
package co.edu.uniquindio.poo.empresajfx.viewController;

import java.net.URL;
import java.util.ResourceBundle;

import co.edu.uniquindio.poo.empresajfx.App;
import co.edu.uniquindio.poo.empresajfx.controller.ProductoController;
import co.edu.uniquindio.poo.empresajfx.model.Producto;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ProductoViewController {

    private ProductoController productoController;
    private ObservableList<Producto> listProductos =
            FXCollections.observableArrayList();
    private Producto selectedProducto;
    private App app;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtCodigo;

    @FXML
    private TextField txtNombre;

    @FXML
    private TextField txtPrecio;

    @FXML
    private TextField txtCantidad;

    @FXML
    private TableView<Producto> tblListProducto;

    @FXML
    private TableColumn<Producto, String> tbcCodigo;

    @FXML
    private TableColumn<Producto, String> tbcNombre;

    @FXML
    private TableColumn<Producto, Double> tbcPrecio;

    @FXML
    private TableColumn<Producto, Integer> tbcCantidad;

    @FXML
    private Button btnAgregarProducto;

    @FXML
    private Button btnActualizarProducto;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnLimpiar;

    @FXML
    void onAgregarProducto() {
        agregarProducto();
    }

    @FXML
    void onActualizarProducto() {
        actualizarProducto();
    }

    @FXML
    void onEliminar() {
        eliminarProducto();
    }

    @FXML
    void onLimpiar() {
        limpiarSeleccion();
    }

    @FXML
    void initialize() {
        initDataBinding();

        tblListProducto.setItems(listProductos);

        listenerSelection();
    }

    private void initView() {
        initDataBinding();
        obtenerProductos();

        tblListProducto.setItems(listProductos);

        listenerSelection();
    }

    private void initDataBinding() {
        tbcCodigo.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getCodigo()));

        tbcNombre.setCellValueFactory(
                cellData -> new SimpleStringProperty(
                        cellData.getValue().getNombre()));

        tbcPrecio.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(
                        cellData.getValue().getPrecio()));

        tbcCantidad.setCellValueFactory(
                cellData -> new SimpleObjectProperty<>(
                        cellData.getValue().getCantidad()));
    }

    private void obtenerProductos() {
        listProductos.clear();
        listProductos.addAll(productoController.obtenerListaProductos());
    }

    private void listenerSelection() {
        tblListProducto.getSelectionModel()
                .selectedItemProperty()
                .addListener((obs, oldSelection, newSelection) -> {
                    selectedProducto = newSelection;
                    mostrarInformacionProducto(selectedProducto);
                });
    }

    private void mostrarInformacionProducto(Producto producto) {
        if (producto != null) {
            txtCodigo.setText(producto.getCodigo());
            txtNombre.setText(producto.getNombre());
            txtPrecio.setText(String.valueOf(producto.getPrecio()));
            txtCantidad.setText(String.valueOf(producto.getCantidad()));
        }
    }

    private Producto buildProducto() {
        return new Producto(
                txtCodigo.getText(),
                txtNombre.getText(),
                Double.parseDouble(txtPrecio.getText()),
                Integer.parseInt(txtCantidad.getText())
        );
    }

    private void agregarProducto() {
        try {
            Producto producto = buildProducto();

            if (productoController.crearProducto(producto)) {
                listProductos.add(producto);
                limpiarCamposProducto();
            }
        } catch (NumberFormatException e) {
            System.out.println("El precio y la cantidad deben ser números.");
        }
    }

    private void actualizarProducto() {
        if (selectedProducto != null) {
            try {
                Producto actualizado = buildProducto();

                if (productoController.actualizarProducto(
                        selectedProducto.getCodigo(), actualizado)) {

                    int index = listProductos.indexOf(selectedProducto);

                    if (index >= 0) {
                        listProductos.set(index, actualizado);
                    }

                    tblListProducto.refresh();
                    limpiarSeleccion();
                }
            } catch (NumberFormatException e) {
                System.out.println("El precio y la cantidad deben ser números.");
            }
        }
    }

    private void eliminarProducto() {
        if (selectedProducto != null) {
            if (productoController.eliminarProducto(
                    selectedProducto.getCodigo())) {

                listProductos.remove(selectedProducto);
                limpiarSeleccion();
            }
        }
    }

    private void limpiarSeleccion() {
        tblListProducto.getSelectionModel().clearSelection();
        selectedProducto = null;
        limpiarCamposProducto();
    }

    private void limpiarCamposProducto() {
        txtCodigo.clear();
        txtNombre.clear();
        txtPrecio.clear();
        txtCantidad.clear();
    }
    public void setApp(App app) {
        this.app = app;
        productoController = new ProductoController(App.empresa);

        obtenerProductos();
    }
    @FXML
    void onVolverMenu() {
        app.volverAlMenu();
    }
}
