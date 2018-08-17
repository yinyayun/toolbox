package org.yinyayun.jvm;

/**
 * 类加载测试 JVM只有在四种场景下才会对类进行“初始化”
 * <p>
 * 1.
 * 遇到new、getstatic(读静态字段)、putstatic、invokestatic(调用静态方法)四条指令，类还未初始化，则必须初始化<br>
 * 2. 初始化一个类时，发现父类未初始化<br>
 * 3. main方法对应的类<br>
 * 4. 使用java.lang.reflect进行反射调用的类<br>
 * </p>
 * 
 * @author yinyayun
 *
 */
public class ClassInitTest {
	public static void main(String[] args) {
		// 并不会对子类进行初始化,因为并未涉及调用子类的static字段
		// System.out.println(Child.p);
		// 既不会触发父类，也不会触发子类初始化
		Child[] c = new Child[100];
		//
		System.out.println(Child.VV);
	}
}

class Parent {
	static int p = 100;
	static {
		System.out.println("parent class");
	}
}

class Child extends Parent {
	static {
		System.out.println("child class");
	}
	// 常量在编译时期就被放入了调用类的常量池
	public final static int VV = 100;
}
