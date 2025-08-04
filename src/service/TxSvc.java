package service;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import model.Merchat;
import model.OpenBankingOBIE.Transaction;
import model.paymentDto;
import util.uti.orm.JpaUti;

import java.util.Map;

import static util.misc.util2026.copyProps;
import static util.uti.orm.JpaUti.persist;

public class TxSvc {


    /**
     *发起订单
     *
     * http://localhost:8888/tx/payment?transactionId=666&amout=88
     * @param m
     * @return
     */
    @PermitAll
  //  @RolesAllowed({"adm","opr"})
    public static Object payment(paymentDto m) {

        Transaction tx=new Transaction();
        copyProps(m,tx);

        // public  String accountId="999";
        tx.accountId="accidxxx";

               // copyPropertys(m,tx);
        //   System.out.println("fun svUHdl");
        persist(tx);
        return  m;
    }


//http://localhost:8888/tx/payments?transactionId=666
    public static Object payments(Map m) {

        Transaction tx=new Transaction();
        copyProps(m,tx);

        // public  String accountId="999";
        tx.accountId="accidxxx";

        // copyPropertys(m,tx);
     //   System.out.println("fun svUHdl");
        JpaUti.    find(Transaction.class,m.get("transactionId").toString());
        return  m;
    }
}
