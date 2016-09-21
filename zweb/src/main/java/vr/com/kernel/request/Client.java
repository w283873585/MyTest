package vr.com.kernel.request;

import vr.com.kernel.Denominative;
import vr.com.kernel.request.HttpUtil.MyResponse;

public interface Client extends Denominative{
	
	public MyResponse httpRequest(Request req);
}
