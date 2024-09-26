package lk.ijse.project.dao.custom.impl;

import lk.ijse.project.config.FactoryConfiguration;
import lk.ijse.project.dao.custom.CustomerDAO;
import lk.ijse.project.entity.Customer;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.NativeQuery;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDAOImpl implements CustomerDAO {
    @Override
    public ArrayList getAll() throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery quary = session.createNativeQuery("select * from customer");
        quary.addEntity(Customer.class);
        ArrayList<Customer> customers = (ArrayList<Customer>) quary.list();

        transaction.commit();
        session.close();

        return customers;
    }

    @Override
    public boolean save(Customer customer) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Object ob = session.save(customer);

        if (ob != null) {
            transaction.commit();
            session.close();

            return true;
        }
        return false;
    }

    @Override
    public boolean update(Customer customer) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.update(customer);

            transaction.commit();
            session.close();

            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean delete(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery delete = session.createNativeQuery("delete from customer where cid = ?1");
        delete.setParameter(1,id);

        int result = delete.executeUpdate();
        boolean re;

        if(result<=1){
            re = true;
        }else {
            re = false;
        }

        transaction.commit();
        session.close();

        return re;
    }

    @Override
    public Customer search(String id) throws SQLException, ClassNotFoundException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        NativeQuery query = session.createNativeQuery("select * from customer where cid = ?1");
        query.addEntity(Customer.class);
        query.setParameter(1, id);

        Customer customer = (Customer) query.uniqueResult();

        transaction.commit();
        session.close();

        return customer;
    }

    @Override
    public String generateNewID() {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();

        Object query = session.createNativeQuery("SELECT cid FROM customer ORDER BY CAST(SUBSTRING(cid, 2) AS UNSIGNED) DESC LIMIT 1").uniqueResult();
        String nextId = "";

        if (query != null) {
            String id = query.toString();

            String[] split = id.split("C");

            int idNum = Integer.parseInt(split[1]);

            nextId = "C" + String.format("%03d", ++idNum);
        }else {
            nextId = "C001";
        }

        transaction.commit();
        session.close();

        return nextId;
    }
}
