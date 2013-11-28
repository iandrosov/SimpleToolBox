package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-20 13:36:57 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class exception

{
	// ---( internal utility methods )---

	final static exception _instance = new exception();

	static exception _newInstance() { return new exception(); }

	static exception _cast(Object o) { return (exception)o; }

	// ---( server methods )---




	public static final void runtimeException (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(runtimeException)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:optional exception_text
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	exception_text = IDataUtil.getString( pipelineCursor, "exception_text" );
		pipelineCursor.destroy();
		
		if (exception_text != null)
			throw new com.wm.app.b2b.server.ISRuntimeException(exception_text);
		else
			throw new com.wm.app.b2b.server.ISRuntimeException();
		// --- <<IS-END>> ---

                
	}



	public static final void serviceException (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(serviceException)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:optional exception_text
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	exception_text = IDataUtil.getString( pipelineCursor, "exception_text" );
		pipelineCursor.destroy();
		if (exception_text != null)
			throw new com.wm.app.b2b.server.ServiceException(exception_text);
		else
			throw new com.wm.app.b2b.server.ServiceException();
		// --- <<IS-END>> ---

                
	}
}

