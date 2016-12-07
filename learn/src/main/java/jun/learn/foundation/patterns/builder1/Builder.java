package jun.learn.foundation.patterns.builder1;

abstract class Builder {  
    public abstract void setPartA();
    public abstract void setPartB();
    public abstract void setPartC();
    public abstract Product getProduct();  
} 
