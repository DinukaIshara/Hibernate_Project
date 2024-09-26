package lk.ijse.project.bo.custom.impl;

import javafx.fxml.FXML;
import lk.ijse.project.bo.custom.CustomerBO;
import lk.ijse.project.dao.DAOFactory;
import lk.ijse.project.dao.custom.CustomerDAO;
import lk.ijse.project.dto.CustomerDTO;
import lk.ijse.project.entity.Customer;

import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerBOImpl implements CustomerBO {

    CustomerDAO customerDAO = (CustomerDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.CUSTOMERDAO);

    @FXML
    public ArrayList<CustomerDTO> getAllCustomers() throws SQLException, ClassNotFoundException {
        ArrayList<Customer> customers = customerDAO.getAll();
        ArrayList<CustomerDTO> customerDTO = new ArrayList<>();

        for (Customer customer: customers){
            customerDTO.add(new CustomerDTO(customer.getCid(), customer.getCname(), customer.getCaddress(),customer.getCtel()));
        }
        return customerDTO;
    }

    @Override
    public boolean saveCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.save(new Customer(customer.getCid(), customer.getCname(), customer.getCaddress(), customer.getCtel()));
    }

    @Override
    public boolean updateCustomer(CustomerDTO customer) throws SQLException, ClassNotFoundException {
        return customerDAO.update(new Customer(customer.getCid(), customer.getCname(), customer.getCaddress(), customer.getCtel()));
    }

    @Override
    public boolean deleteCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerDAO.delete(id);
    }

    @Override
    public CustomerDTO searchCustomer(String id) throws SQLException, ClassNotFoundException {
        Customer customer = (Customer) customerDAO.search(id);
        CustomerDTO customerDTO = new CustomerDTO(customer.getCid(), customer.getCname(), customer.getCaddress(), customer.getCtel());
        return customerDTO;
    }

    @Override
    public String generateNewID() {
        return customerDAO.generateNewID();
    }
}
