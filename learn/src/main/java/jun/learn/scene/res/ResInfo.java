package jun.learn.scene.res;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
public @interface ResInfo{
	
	public String resCode();
	public String resDesc();
}