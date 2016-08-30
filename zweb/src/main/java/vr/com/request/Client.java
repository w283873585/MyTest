package vr.com.request;

import vr.com.kernel.Denominative;

public interface Client extends Denominative{
	
	public String httpRequest(Request req);
}
