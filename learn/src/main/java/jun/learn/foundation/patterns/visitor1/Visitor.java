package jun.learn.foundation.patterns.visitor1;

public interface Visitor {
	// 应该是接受内奸的信息,如果直接接受主人的信息，似乎有点不合理
	// 并且访问者直接依赖主人，也会造成过度耦合的情况
	void visit(Traitor ac);
}
