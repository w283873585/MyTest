package jun.learn.foundation.patterns.builder2;

public interface Builder {
	void setPartA();
    void setPartB();
    void setPartC();
	Product getProduct();
	void clear();
}
