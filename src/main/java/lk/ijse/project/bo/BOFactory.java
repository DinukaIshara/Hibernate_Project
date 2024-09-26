package lk.ijse.project.bo;

import lk.ijse.project.bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {}

    public static BOFactory getBOFactory() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public enum BOTypes{
        CUSTOMERBO
    }

    public SuperBO getBO(BOTypes boTypes){

        switch (boTypes){
            case CUSTOMERBO:
                return new CustomerBOImpl();
            default:
                return null;
        }
    }


}
