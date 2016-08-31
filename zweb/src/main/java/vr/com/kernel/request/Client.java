package vr.com.kernel.request;

import vr.com.kernel.Denominative;

public interface Client extends Denominative{
	
	public String httpRequest(Request req);
}
