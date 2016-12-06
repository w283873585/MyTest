package jun.learn.scene.chain;


public interface Pipeline {

    public Valve getBasic();

    public void setBasic(Valve valve);

    public void addValve(Valve valve);

    public Valve[] getValves();

    public void invoke();

    public void removeValve(Valve valve);
}
