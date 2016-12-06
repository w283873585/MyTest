package jun.learn.scene.chain;

public interface Valve {

    public String getInfo();

    public void invoke(ValveContext context);
}
