package unicom.hiber;
import java.util.List;


public class testMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		FeePolicyObjDAO dao=new FeePolicyObjDAO();
		List<FeePolicyObj> alllist=dao.findAll();
		//dao.

	}

}
