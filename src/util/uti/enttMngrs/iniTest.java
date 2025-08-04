package util.uti.enttMngrs;

import jakarta.persistence.EntityManager;
import util.u.User;

public class iniTest {

    public static void main(String[] args) {
        EntityManager ett=new IniEnttMngr("db2");
        User u=    new User(2, "Bob", 30);
        ett.persist(u);
    }
}
