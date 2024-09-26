package lk.ijse.project.dao;

import lk.ijse.project.dao.custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {}

    public static DAOFactory getDAOFactory() {
        return daoFactory == null ? daoFactory= new DAOFactory() : daoFactory;
    }

    public enum DAOTypes {
        CUSTOMERDAO
    }

    public SuperDAO getDAO(DAOTypes daoTypes){

        switch (daoTypes){
            case CUSTOMERDAO:
                return  new CustomerDAOImpl();
            default:
                return null;
        }
    }
}
