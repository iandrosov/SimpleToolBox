package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-20 14:40:54 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
// --- <<IS-END-IMPORTS>> ---

public final class string

{
	// ---( internal utility methods )---

	final static string _instance = new string();

	static string _newInstance() { return new string(); }

	static string _cast(Object o) { return (string)o; }

	// ---( server methods )---




	public static final void multi_str_concat (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(multi_str_concat)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:1:required string_list
		// [o] field:0:required result_str
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String[]	string_list = IDataUtil.getStringArray( pipelineCursor, "string_list" );
		pipelineCursor.destroy();
		
		String result_str = "";
		for (int i=0; i < string_list.length; i++)
		{
		     if (string_list[i] != null)	
			 result_str += string_list[i];
		}
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result_str", result_str );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void string_compare (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(string_compare)>> ---
		// @sigtype java 3.5
		// [i] field:0:required string1
		// [i] field:0:required string2
		// [o] field:0:required areEqual
		
		try {
		// pipeline
		IDataHashCursor pipelineCursor = pipeline.getHashCursor();
		pipelineCursor.first( "string1" );
		String	s1 = (String) pipelineCursor.getValue();
		pipelineCursor.first( "string2" );
		String	s2 = (String) pipelineCursor.getValue();
		pipelineCursor.destroy();
		
		String areEqual = new String("FALSE");
		
		if ( (s1 != null) && (s2 != null) ) {
			if ( s1.compareTo(s2) == 0) {
				areEqual = "TRUE";
			}
		}
		
		// pipeline
		IDataHashCursor pipelineCursor_1 = pipeline.getHashCursor();
		pipelineCursor_1.last();
		pipelineCursor_1.insertAfter( "areEqual", areEqual );
		pipelineCursor_1.destroy();
		
		} catch (Exception e) {
			throw new ServiceException(e.getMessage());
		}
		
		// --- <<IS-END>> ---

                
	}
}

