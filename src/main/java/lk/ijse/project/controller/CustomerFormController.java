package lk.ijse.project.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import lk.ijse.project.bo.BOFactory;
import lk.ijse.project.bo.custom.CustomerBO;
import lk.ijse.project.dto.CustomerDTO;
import lk.ijse.project.view.CustomerTm;

import java.util.List;
import java.util.Optional;

public class CustomerFormController {

    @FXML
    private TextField txtcId;

    @FXML
    private TextField txtcName;

    @FXML
    private TextField txtcAddress;

    @FXML
    private TextField txtcTel;

    @FXML
    private TableView<CustomerTm> tblCustomer;

    @FXML
    private TableColumn<?, ?> colCid;

    @FXML
    private TableColumn<?, ?> colCname;

    @FXML
    private TableColumn<?, ?> colCaddress;

    @FXML
    private TableColumn<?, ?> colCtel;

    CustomerBO customerBO = (CustomerBO) BOFactory.getBOFactory().getBO(BOFactory.BOTypes.CUSTOMERBO);

    public void initialize() {
        setCellValueFactory();
        loadAllCustomers();
        getCurrentId();
    }

    private void getCurrentId() {
        String id = customerBO.generateNewID();
        txtcId.setText(id);
    }

    private void setCellValueFactory() {
        colCid.setCellValueFactory(new PropertyValueFactory<>("cid"));
        colCname.setCellValueFactory(new PropertyValueFactory<>("cname"));
        colCaddress.setCellValueFactory(new PropertyValueFactory<>("caddress"));
        colCtel.setCellValueFactory(new PropertyValueFactory<>("ctel"));
    }

    private void loadAllCustomers() {
        ObservableList<CustomerTm> obList = FXCollections.observableArrayList();

        try {
            List<CustomerDTO> customerList = customerBO.getAllCustomers();
            for (CustomerDTO customer : customerList) {
                CustomerTm tm = new CustomerTm(
                        customer.getCid(),
                        customer.getCname(),
                        customer.getCaddress(),
                        customer.getCtel()
                );

                obList.add(tm);
            }

            tblCustomer.setItems(obList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnSerachOnAction(ActionEvent event) {
        try {
            String id = txtcId.getText();

            CustomerDTO customer = customerBO.searchCustomer(id);

            if (customer != null) {
                txtcId.setText(customer.getCid());
                txtcName.setText(customer.getCname());
                txtcAddress.setText(customer.getCaddress());
                txtcTel.setText(customer.getCtel());
            } else {
                new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            }
        }catch (Exception e) {
            new Alert(Alert.AlertType.INFORMATION, "customer not found!").show();
            throw new RuntimeException(e);
        }
    }

    private void clear(){
        txtcId.setText("");
        txtcName.setText("");
        txtcAddress.setText("");
        txtcTel.setText("");
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Delete Customer?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String id = txtcId.getText();

            try {
                boolean isDeleted = customerBO.deleteCustomer(id);
                if (isDeleted) {
                    new Alert(Alert.AlertType.CONFIRMATION, "customer deleted!").show();
                    clear();
                    initialize();
                }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String id = txtcId.getText();
        String name = txtcName.getText();
        String address = txtcAddress.getText();
        String contact = txtcTel.getText();

        CustomerDTO customer = new CustomerDTO(id, name, address, contact);

        try {
            boolean isSaved = customerBO.saveCustomer(customer);
            if (isSaved) {
                new Alert(Alert.AlertType.CONFIRMATION, "customer saved!").show();
                clear();
                initialize();
            } else {
                new Alert(Alert.AlertType.WARNING, "Your input data incorrect").show();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

        Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to Update Customer?", yes, no).showAndWait();

        if (type.orElse(no) == yes) {
            String id = txtcId.getText();
            String name = txtcName.getText();
            String address = txtcAddress.getText();
            String contact = txtcTel.getText();

            CustomerDTO customer = new CustomerDTO(id, name, address,contact); // Set Customer Data

            try {
                 boolean isUpdated = customerBO.updateCustomer(customer);
                 if (isUpdated) {
                     new Alert(Alert.AlertType.CONFIRMATION, "customer updated!").show();
                     clear();
                     initialize();
                 } else {
                    new Alert(Alert.AlertType.INFORMATION, "The data you entered is incorrect").show();
                 }
            } catch (Exception e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
        }
    }


    @FXML
    void cAddressOnAction(ActionEvent event) {
        txtcTel.requestFocus();
    }

    @FXML
    void cNameOnAction(ActionEvent event) {
        txtcAddress.requestFocus();
    }

    @FXML
    void cTelOnAction(ActionEvent event) {
    }

    @FXML
    void cidOnAction(ActionEvent event) {
        txtcName.requestFocus();
    }

}
