package unicom.recive;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.hibernate.Transaction;

public class TransationHandler implements InvocationHandler {

 private Object target = null;
 
 public TransationHandler(Object target){
  this.target = target;
 }
 
 public Object invoke(Object proxy, Method method, Object[] args)
   throws Throwable {
  System.out.println("getSession");	
  //Transaction tran=BillingDao.getCon().getTransactionIsolation(); 
  Object result = method.invoke(target, args);
  //tran.commit();
  System.out.println("commit");
  return result;
 }

}