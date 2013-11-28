package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-25 14:30:21 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.util.*;
// --- <<IS-END-IMPORTS>> ---

public final class config

{
	// ---( internal utility methods )---

	final static config _instance = new config();

	static config _newInstance() { return new config(); }

	static config _cast(Object o) { return (config)o; }

	// ---( server methods )---




	public static final void getProperty (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getProperty)>> ---
		// @sigtype java 3.5
		// [i] field:0:required property
		// [i] field:0:required file
		// [o] field:0:required value
	IDataHashCursor idc = pipeline.getHashCursor();	
	idc.first("property");
	String property_name = (String)idc.getValue();
	idc.first("file");
	String file_name = (String)idc.getValue();

	try 
	{
		Properties config = new Properties();
		InputStream in_stream = (InputStream) new FileInputStream(file_name);
		config.load(in_stream);
		in_stream.close();
		String str = config.getProperty(property_name);
		idc.insertAfter("value",str);

		// clean up
		config = null;
		in_stream = null;
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getPropertyList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getPropertyList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file
		// [o] record:1:required value_list
		// [o] - field:0:required propertyname
		// [o] - field:0:required propertyvalue
	IDataHashCursor idc = pipeline.getHashCursor();	

	String property_name = "";
	idc.first("file");
	String file_name = (String)idc.getValue();

	Values data = null;
	Values[] property_list = null;

	try 
	{
		String str = "";
		Properties config = new Properties();
		InputStream in_stream = (InputStream) new FileInputStream(file_name);
		config.load(in_stream);
		// Get property name list
		Enumeration enum = config.propertyNames();
		int count = 0;
		while (enum.hasMoreElements())
		{
			property_name = (String)enum.nextElement();
			count++;
		}
		enum = config.propertyNames();
		property_list = new Values[count];
		int i = 0;
		while (enum.hasMoreElements())
		{
			property_name = (String)enum.nextElement();
			str = config.getProperty(property_name);
			data = new Values();
			data.put("propertyname",property_name);
			data.put("propertyvalue",str);
			property_list[i] = data;
			i++;
        }

		idc.insertAfter("value_list",property_list);

		// clean up
		config = null;
		in_stream.close();
		in_stream = null;
		enum = null;
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void getSystemPropery (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getSystemPropery)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required propertyName
		// [o] field:0:required property
		IDataHashCursor hCursor = pipeline.getHashCursor();
		hCursor.first("propertyName");
		String propertyName = (String)hCursor.getValue();
		
		String property = new String();
		
		property = System.getProperty(propertyName, "No property found");
		
		hCursor.first("property");
		hCursor.delete();
		hCursor.insertAfter("property", property);
		// --- <<IS-END>> ---

                
	}



	public static final void getSystemPropetyList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getSystemPropetyList)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:1:required propertyList
		IDataHashCursor hCursor = pipeline.getHashCursor();
		Properties prop = System.getProperties();
		Enumeration propKeyList = prop.propertyNames();
		Vector propVec = new Vector();
		
		outer:while(true){
			if(propKeyList.hasMoreElements() == true){
				propVec.addElement( (String)propKeyList.nextElement() );
			}
			else
				break outer;
		}
		
		String[] allProps = new String[propVec.size()];
		for(int i=0;i<propVec.size();i++){
			allProps[i] = (String)propVec.elementAt(i);
		}
		
		hCursor.first("propertyList");
		hCursor.delete();
		hCursor.insertAfter("propertyList", allProps);
		// --- <<IS-END>> ---

                
	}



	public static final void get_property_list (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_property_list)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required file
		IDataHashCursor idc = pipeline.getHashCursor();	
			String property_name = "";
			idc.first("file");
			String file_name = (String)idc.getValue();
		
			Values data = null;
			Values[] property_list = null;
		
			try 
			{
				String str = "";
				Properties config = new Properties();
				InputStream in_stream = (InputStream) new FileInputStream(file_name);
				config.load(in_stream);
				// Get property name list
				Enumeration enum = config.propertyNames();
				int count = 0;
				while (enum.hasMoreElements())
				{
					property_name = (String)enum.nextElement();
					count++;
				}
				enum = config.propertyNames();
				property_list = new Values[count];
				int i = 0;
				while (enum.hasMoreElements())
				{
					property_name = (String)enum.nextElement();
					str = config.getProperty(property_name);
					data = new Values();
					data.put(property_name,str);
					idc.insertAfter(property_name,str);
					property_list[i] = data;
					i++;
		        }
		
				//idc.insertAfter("value_list",property_list);
		
				// clean up
				config = null;
				in_stream.close();
				in_stream = null;
				enum = null;
			}
			catch(Exception e)
			{
				throw new ServiceException(e.getMessage());
			}
			idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void setProperty (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(setProperty)>> ---
		// @sigtype java 3.5
		// [i] field:0:required property
		// [i] field:0:required value
		// [i] field:0:required file
		// [o] field:0:required message
	IDataHashCursor idc = pipeline.getHashCursor();	
	idc.first("property");
	String property_name = (String)idc.getValue();
	idc.first("value");
	String property_value = (String)idc.getValue();
	idc.first("file");
	String file_name = (String)idc.getValue();

	try
	{
		if (property_name != null)
		{
			// Get server properties
			Properties config = new Properties();
			InputStream in_stream = (InputStream) new FileInputStream(file_name);
			config.load(in_stream);
			config.setProperty(property_name, property_value);
			// Stor properties to file
			OutputStream out = (OutputStream) new FileOutputStream(file_name);
			config.store(out, "Server property");

			out.flush();
			out.close();
			in_stream.close();

			idc.first();
			idc.insertAfter( "message", "Set property - " + property_name );
		}
		else
		{
			idc.first();
			idc.insertAfter( "message", "Error setting property - " + property_name );
		}
	}
	catch (Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void setPropertyList (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(setPropertyList)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file
	IDataCursor idc = pipeline.getCursor();	
	idc.first("file");
	String file_name = (String)idc.getValue();
	
	try
	{
			String key = "";
			String val = "";
			// Get properties from a given file
			Properties config = new Properties();
			InputStream in_stream = (InputStream) new FileInputStream(file_name);
			config.load(in_stream);
			// Loop over pipeline and get all property values and names
			idc.first();
			String temp = "";
			while (idc.hasMoreData())
			{
				key = idc.getKey();
				if (key.length() >= 4)
					temp = key.substring(key.length() - 4, key.length());
		
				if (!key.equals("file") && !key.equals("file_name") &&
				!key.equals("action") && !temp.equals("List"))
				{
					val = (String)idc.getValue();
					config.setProperty(key, val);
				}
				idc.next();
			}
			// Stor properties to file
			OutputStream out = (OutputStream) new FileOutputStream(file_name);
			config.store(out, "update property");
			out.flush();
			out.close();
			
			idc.first();
			idc.insertAfter( "message", "Set/update property file - " + file_name );
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}
}

