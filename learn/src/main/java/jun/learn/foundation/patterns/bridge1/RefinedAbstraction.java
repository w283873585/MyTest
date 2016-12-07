package jun.learn.foundation.patterns.bridge1;

public class RefinedAbstraction extends Abstraction{

    // 约束子类必须实现该构造函数
    public RefinedAbstraction(Implementor _imp) {
        super(_imp);
    }
 
    // 自身的行为和属性
    public void request() {
    	 /*
         * 业务处理
         * 这个部分可以将
         * 抽象层之间的东西
         * 搭上一条方便桥了
         * 比如 我需要另外一个抽象层的功能组合在一起
         */
        super.request();
        super.getImp().doAnything();	
    }
    
    public static void main(String[] args) {
		
    	 // 定义一个实现化角色
        Implementor imp = new ConcreteImplementor1();
        
        // 定义一个抽象化角色
        Abstraction abs = new RefinedAbstraction(imp);
 
        // 执行行文
        abs.request();
	}
}
