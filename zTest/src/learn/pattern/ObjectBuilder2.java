package learn.pattern;

//构建器模式
public class ObjectBuilder2 {
  private int attrA;
  private int attrB;
  private int attrC;
  private int attrD;
   
  public static class Bulider{
       
      private int attrA;
      private int attrB;
      private int attrC;
      private int attrD;
       
      public  Bulider setA(int a){
          attrA = a;
          return this;
      }
      public  Bulider setB(int a){
          attrB = a;
          return this;
      }
      public  Bulider setC(int a){
          attrC = a;
          return this;
      }
      public  Bulider setD(int a){
          attrD = a;
          return this;
      }
       
      public ObjectBuilder2 build(){
    	  ObjectBuilder2  objBuilder = new ObjectBuilder2();
          objBuilder.attrA = attrA;
          objBuilder.attrB = attrB;
          objBuilder.attrC = attrC;
          objBuilder.attrD = attrD;
          return objBuilder;
      }
      
      
      public static void main(String[] args) {
    	  ObjectBuilder2 obj = new ObjectBuilder2.Bulider().setA(1).setB(2).setC(3).build();
      }
       
  }
   
  //调用
  //ObjectBuilder obj = new ObjectBuilder.Builder().seta(1).setb(2).setc(3).build();
  /*
   * 有没有办法解决呢？既能提高代码可读性，提高参数灵活性，又不会增加代码
	   行数，并保证线程安全呢？
	    这样就解决了以上所说的问题，但是他的缺点同样也是存在的，就是：
	
	  1.构造器写起来很复杂
	
	  2.创建对象开销比较大
	
	  所以构建器模式只适用于需要传入很多种情况参数的时候，比如大于4种参数的配
	
	合，才比较划算。
	
	  而且值得注意的是：最好在类的设计之初就考虑是否使用构建器，否则日后扩展
	
	起来新构建器旧构造器一起用维护起来不方便。
  */
}