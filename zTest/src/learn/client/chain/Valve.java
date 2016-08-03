package learn.client.chain;

public interface Valve {

    public String getInfo();

    public void invoke(ValveContext context);
}
