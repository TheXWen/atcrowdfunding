package junit.test;

import java.util.List;

public class Test03 {

	public static void main(String[] args) throws Exception {
		//String str = null ;
		//str.toUpperCase(); //成员方法 : 依赖于对象才能调用的.
		
		
		//Thread t = null ;
		//t.sleep(3000); //静态方法,正常是通过类名称来调用(Thread.sleep(3000)),而不推荐用对象来调用.
		
		
		//Exception in thread "main" java.lang.NumberFormatException: null
		//String i = null;
		//int j = Integer.parseInt(i);
		//System.out.println(j);
		
		//Exception in thread "main" java.lang.NullPointerException
		//Integer i = null ;
		//int j = i ; //自动拆箱
		//System.out.println(j);
		
		List<String> list = null ;
		
		//Exception in thread "main" java.lang.NullPointerException
		/*for (int i = 0; i < list.size(); i++) {
			
		}*/
		
		//Exception in thread "main" java.lang.NullPointerException
		for (String str : list) {
			System.out.println("str="+str);
		}
		
	}

}
