package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-20 13:40:13 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.*;
import com.wm.app.b2b.server.ServiceThread;
import com.wm.lang.ns.NSName;
// --- <<IS-END-IMPORTS>> ---

public final class service

{
	// ---( internal utility methods )---

	final static service _instance = new service();

	static service _newInstance() { return new service(); }

	static service _cast(Object o) { return (service)o; }

	// ---( server methods )---




	public static final void get_service_name (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_service_name)>> ---
		// @sigtype java 3.5
		// [o] field:0:required serviceName
		IDataHashCursor curs = pipeline.getHashCursor();
		String caller = "SVC - ";
		
		try 
		{
			Thread t = Thread.currentThread();
			com.wm.app.b2b.server.ServerThread st = (com.wm.app.b2b.server.ServerThread)t;
			java.util.Stack stack = st.getState().getCallStack();
		
			for (int i = 0; i < stack.size(); i++)
			{
				if (i < stack.size() - 1)
				{
					caller += stack.elementAt(i).toString();
					caller += " ";
				}
			}
		
		} 
		catch (Exception e) 
		{ 
			caller = new String("non-determinable"); 
		}
		curs.last();
		curs.insertAfter("serviceName", caller);
		// --- <<IS-END>> ---

                
	}



	public static final void invoke_thread (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(invoke_thread)>> ---
		// @sigtype java 3.5
		// [i] field:0:required service
		// [i] field:0:required data
		// [i] record:0:required in
		// [i] object:0:required obj
    IDataHashCursor idc = pipeline.getHashCursor();
    idc.first( "service" );

	String svc_name = (String) idc.getValue();
	try
	{
		NSName nsName = NSName.create(svc_name);
		ServiceThread thrd = Service.doThreadInvoke(nsName, pipeline);
		thrd.getData();
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}
}

