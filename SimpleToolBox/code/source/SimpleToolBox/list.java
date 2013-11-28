package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-20 13:08:34 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.util.*;
import java.text.*;
// --- <<IS-END-IMPORTS>> ---

public final class list

{
	// ---( internal utility methods )---

	final static list _instance = new list();

	static list _newInstance() { return new list(); }

	static list _cast(Object o) { return (list)o; }

	// ---( server methods )---




	public static final void guarantee_list (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(guarantee_list)>> ---
		// @sigtype java 3.5
		// [i] record:1:required INPUT
		// [o] record:1:required OUTPUT
		try {
		IDataHashCursor curs = pipeline.getHashCursor();
		
		curs.first("INPUT");
		Object obj = curs.getValue();
		
		if (obj == null) {
		    curs.last();
			curs.insertAfter("OUTPUT", obj);
		}
		else {
		   Class c = obj.getClass();
		
		   if ( !c.isArray() ) {
		       // avoid class cast exceptions this way
			   Object output [] = (Object [])java.lang.reflect.Array.newInstance(c, 1);
			   output[0] = obj;
			   curs.last();
			   curs.insertAfter("OUTPUT", output);
		   } else {
			   curs.last();
			   curs.insertAfter("OUTPUT", obj);
		   }
		}
		
		} catch  (Exception e) {
		throw new ServiceException(e.getMessage());
		}
		// --- <<IS-END>> ---

                
	}



	public static final void is_list (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(is_list)>> ---
		// @sigtype java 3.5
		// [i] record:1:required recordList
		// [o] field:0:required value
		// pipeline
		IDataHashCursor pipelineCursor = pipeline.getHashCursor();
		try
		{
		if ( pipelineCursor.first( "recordList" ) )
		{
		
			// recordList
			IData[]	recordList = (IData[]) pipelineCursor.getValue();
			if ( recordList != null)
			{
				pipelineCursor.first();
				pipelineCursor.insertAfter("value","YES");
			}
			else
			{
				pipelineCursor.first();
				pipelineCursor.insertAfter("value","NO");
			}
		}
		}
		catch (Exception e)
		{
				pipelineCursor.first();
				pipelineCursor.insertAfter("value","NO");
		
		}
		
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void sort_list (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(sort_list)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] record:1:required List_IN
		// [i] field:0:required keyField
		// [i] field:0:required sortDescending {"true","false"}
		// [o] record:1:required List_OUT
		// [o] field:0:required sorted
		IData[] sortedItemList = null;  
		
		// pipeline 
		IDataCursor pipelineCursor = pipeline.getCursor(); 
		IDataCursor pipelineCursor1 = pipeline.getCursor(); 
		IData[] itemList = IDataUtil.getIDataArray( pipelineCursor, "List_IN" ); 
		
		int N = itemList.length;
		IData[] itemListClone = new IData[N];
		try {
		 
			if (itemList != null)
			{	
		  	 	for (int i=0;i<itemList.length;i++)
				{
					if (itemList[i] != null)
		        		itemListClone[i] = IDataUtil.deepClone(itemList[i]);
				}
			}
			String keyField = IDataUtil.getString( pipelineCursor, "keyField" ); 
			boolean sortDescending = (Boolean.valueOf(IDataUtil.getString(pipelineCursor, "sortDescending" ))).booleanValue(); 
			pipelineCursor.destroy();  
		
			if(itemList != null) 
			{ 
		  		sortedItemList = IDataUtil.sortIDataArrayByKey(itemListClone,keyField,IDataUtil.COMPARE_TYPE_COLLATION, null,sortDescending); 
			}  
		
		}catch (IOException e) {}
		
		// pipeline 
		pipelineCursor = pipeline.getCursor(); 
		IDataUtil.put( pipelineCursor, "sorted", sortedItemList==null ? "false" : "true"); 
		//pipelineCursor = pipeline.getCursor(); 
		IDataUtil.put( pipelineCursor,"List_OUT",itemListClone); 
		
		pipelineCursor.destroy();
		
		
		
		
		
		
		
		
		
		// --- <<IS-END>> ---

                
	}
}

