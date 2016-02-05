package unicom.recive;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import unicom.comm.model.Record;

public class testMain {

	/**
	 * @param args
	 * @throws ClassNotFoundException 
	 * @throws NoSuchMethodException 
	 * @throws SecurityException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException 
	 */
	public static void main(String[] args) throws SecurityException, NoSuchMethodException, ClassNotFoundException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		// TODO Auto-generated method stub
		Class type=int.class;
		Record record=new Record();
		//Method method =Class.forName("unicom.comm.model.Record").getDeclaredMethod(methodname.toString(), parameterTypes);
		Method method=Class.forName("unicom.comm.model.Record").getDeclaredMethod("setType", type);
		method.invoke(record, 1);
		

	}

}
