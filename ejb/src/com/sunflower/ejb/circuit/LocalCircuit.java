package com.sunflower.ejb.circuit;

import javax.ejb.EJBLocalObject;

/**
 * Created by Алексей on 12/14/2014.
 */
public interface LocalCircuit extends EJBLocalObject {
    public  int getId_Circuit();
    public int getId_Port();
    public void setId_Port(int Id_Port);
    public int getId_Cable();
    public void setId_Cable(Integer Id_Cable);
}
