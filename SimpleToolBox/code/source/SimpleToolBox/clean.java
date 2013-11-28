package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-20 13:52:40 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.io.*;
import java.util.*;
import com.wm.app.b2b.server.ServerAPI;
import com.wm.util.xform.DateTimeDT;
import java.text.SimpleDateFormat;
// --- <<IS-END-IMPORTS>> ---

public final class clean

{
	// ---( internal utility methods )---

	final static clean _instance = new clean();

	static clean _newInstance() { return new clean(); }

	static clean _cast(Object o) { return (clean)o; }

	// ---( server methods )---




	public static final void removeCustomLog (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeCustomLog)>> ---
		// @sigtype java 3.5
 	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first();
	String key = idc.getKey();
	
	Values vl = ValuesEmulator.getValues(pipeline, key);
	String pkg = Service.getPackageName(vl);
	File fl = ServerAPI.getPackageConfigDir(pkg);
	String config_path = fl.getPath();
	String prop = config_path + File.separator + "clean.properties";

	try
	{	
		Properties config = new Properties();
		InputStream in_stream = (InputStream) new FileInputStream(prop);
		config.load(in_stream);
		String dir = config.getProperty("server.clean.dir");
		String mask = config.getProperty("server.clean.file");

		// Get list of files in a server logs directory
        File fname = new File(dir);
        String[] file_list = fname.list();
        String ff = "";
		String file_name = "";
		String tmp = "";

		// Find all files in a given location and return names and size
        for (int i = 0; i < file_list.length; i++)
        {
			file_name = file_list[i];
            ff = dir;
            ff += File.separator;
            ff += file_name;
			fname = null;
            fname = new File(ff);
			
            if (fname.isFile())
			{
				if (mask.equals(file_name))
					fname.delete();			
			}	
        }

		fname = null;
		config = null;
		in_stream = null;
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
		// --- <<IS-END>> ---

                
	}



	public static final void removeOldLogs (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(removeOldLogs)>> ---
		// @sigtype java 3.5

	try
	{	
		// Get list of files in a server logs directory
        File fname = new File("logs");
        String[] file_list = fname.list();
        String ff = "";
		String file_name = "";
		String tmp = "";
		String cur_date = "";
		String dir = fname.getAbsolutePath();

		// Find all files in a given location and return names and size
        for (int i = 0; i < file_list.length; i++)
        {
			file_name = file_list[i];
            ff = dir;
            ff += File.separator;
            ff += file_name;
			fname = null;
            fname = new File(ff);

            if (fname.isFile())
			{
				// Test the name
				for (int j = 0; j < file_name.length(); j++)
				{					
					if (file_name.charAt(j) == '.' && file_name.length() > 12)
					{
						tmp = file_name.substring(j-8, j);
						// terminate loop
						j = file_name.length();
						cur_date = DateTimeDT.currentDate("yyyyMMdd", null);
					}
				}
				// If file is old log remove it
				if (!tmp.equals(cur_date))
					System.out.println(file_name);
					fname.delete();
			}
			
			
        }

		fname = null;
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
		// --- <<IS-END>> ---

                
	}
}

