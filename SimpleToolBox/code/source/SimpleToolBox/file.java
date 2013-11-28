package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-20 13:00:50 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.*;
import java.io.*;
import java.util.zip.*;
// --- <<IS-END-IMPORTS>> ---

public final class file

{
	// ---( internal utility methods )---

	final static file _instance = new file();

	static file _newInstance() { return new file(); }

	static file _cast(Object o) { return (file)o; }

	// ---( server methods )---




	public static final void compress (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(compress)>> ---
		// @sigtype java 3.5
		// [i] field:0:required in_file
		// [i] field:0:required target
		// [o] field:0:required zip_file
   	IDataHashCursor idc = pipeline.getHashCursor();
	// Get input values
   	idc.first( "in_file" );
	String file_name = (String) idc.getValue();
   	idc.first( "target" );
	String target_location = (String) idc.getValue();
	try
	{	
		File source = new File(file_name);
		String zip_file = target_location + File.separator + source.getName() + ".zip";

		byte[] b_data = new byte[255];
		int rc = 0;
		FileInputStream istream = new FileInputStream(file_name);
		FileOutputStream ostream = new FileOutputStream(zip_file);
		ZipOutputStream z_out = new ZipOutputStream(ostream);
		ZipEntry zip_entry = new ZipEntry(file_name);
		z_out.putNextEntry(zip_entry);
		
		while (rc != -1)
		{
			// read data from file
			rc = istream.read(b_data);
			if (rc != -1)
			{
				z_out.write(b_data,0,rc);
			}
		}
		
		z_out.finish();
		z_out.close();
		ostream.close();
		istream.close();

		z_out = null;
		ostream = null;
		istream = null;
		b_data = null;
		source = null;
		zip_entry = null;

	   	idc.first();
		idc.insertAfter("zip_file",zip_file);
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void dir (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(dir)>> ---
		// @sigtype java 3.5
		// [i] field:0:required directory
		// [o] record:1:required DirList
		// [o] - field:0:required filename
		// [o] - field:0:required size
		// [o] - field:0:required time
		// [o] - field:0:required dir
		// [o] - field:0:required isfile
  	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first( "directory" );
	String dir = (String) idc.getValue();
	// Output values
	Values data_source = null;
	Values data = null;
	Values[] directory_list = null;
	Date date = null;

	try
	{	
		// Get list of files in a give directory
        File fname = new File(dir);
        String[] file_list = fname.list();
        String ff = "";
		System.out.println("### DIR - "+dir + " ####");
		// Find all files in a given location and return names and size
		directory_list = new Values[file_list.length];
        for (int i = 0; i < file_list.length; i++)
        {
            ff = dir;
            ff += File.separator;
            ff += file_list[i];
			fname = null;
            fname = new File(ff);

			System.out.println("### "+fname.getName());
			data = new Values();
			data.put("filename",fname.getName());
			data.put("size",fname.length());
			date = new Date(fname.lastModified());
			data.put("time",date.toString());
			data.put("dir",dir);	
			date = null;
            if (fname.isFile())
			{
				data.put("isfile","YES");
			}
			else
				data.put("isfile","NO");

			directory_list[i] = data;
        }
		System.out.println("### DIR ####");
		fname = null;
		date = null;
	   	idc.first();
		idc.insertAfter("DirList",directory_list);
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void filter_file (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(filter_file)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required filename
		// [i] field:0:required filter
		// [i] field:0:required option {"prefix","postfix"}
		// [o] field:0:required result
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	filename = IDataUtil.getString( pipelineCursor, "filename" );
			String	filter = IDataUtil.getString( pipelineCursor, "filter" );
			String	option = IDataUtil.getString( pipelineCursor, "option" );
		pipelineCursor.destroy();
		String result = "FALSE";
		String str = "";
		
		if(option.equals("prefix"))
		{
			str = filename.substring(0,filter.length());
			if (str.equals(filter))
				result = "TRUE";
			else
				result = "FALSE";
		}
		else if (option.equals("postfix"))
		{
			str = filename.substring(filename.length()-filter.length(),filename.length());
			if (str.equals(filter))
				result = "TRUE";
			else
				result = "FALSE";
		
		}
		else
		{
			result = "TRUE";
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void get_file_name (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_file_name)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file_name
		// [i] field:0:required path
		// [o] field:0:required value
   	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first( "file_name" );
	String file_name = (String) idc.getValue();
	idc.first( "path" );
	String path = (String) idc.getValue();

	file_name = file_name.replace('#','0');
	file_name = file_name.replace('&','0');
	String full_path = path;
	full_path += File.separator;
	full_path += file_name;

	idc.first( "file" );
	idc.insertAfter("value",full_path);
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void get_unique_number (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_unique_number)>> ---
		// @sigtype java 3.5
		// [o] field:0:required seq_number
	// Get date parms from IData
   	IDataHashCursor idc = pipeline.getHashCursor();
   	idc.first();

	count++;
	Long tmp = new Long(count);

	idc.insertAfter("seq_number",tmp.toString());

	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void is_file_exists (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(is_file_exists)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file
		// [o] field:0:required exists_flag
   	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first( "file" );
	String file = (String) idc.getValue();

	try
	{
		File source_file = new File(file);
		String result = "NO";
		if (source_file.exists())
			result = "YES";

	   	idc.first();
		idc.insertAfter("exists_flag",result);
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void move (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(move)>> ---
		// @sigtype java 3.5
		// [i] field:0:required source
		// [i] field:0:required target
		// [o] field:0:required status
   	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first( "source" );
	String source = (String) idc.getValue();
   	idc.first( "target" );
	String target = (String) idc.getValue();
	
	try
	{
		File source_file = new File(source);
		source_file.renameTo(new File(target));
		source_file = null;
	
	   	idc.first();
		idc.insertAfter("status","SUCCESS");
	}
	catch(Exception e)
	{
	   	idc.first();
		idc.insertAfter("status","ERROR");		
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void remove (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(remove)>> ---
		// @sigtype java 3.5
		// [i] field:0:required file
   	IDataHashCursor idc = pipeline.getHashCursor();
	// Get input values
   	idc.first( "file" );
	String source = (String) idc.getValue();

	try
	{
		File source_file = new File(source);
		if (source_file.isFile())
		{
			source_file.delete();
		}
		source_file = null;
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void rename (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(rename)>> ---
		// @sigtype java 3.5
		// [i] field:0:required source
		// [i] field:0:required target
		// [o] field:0:required status
   	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first( "source" );
	String source = (String) idc.getValue();
   	idc.first( "target" );
	String target = (String) idc.getValue();
	
	try
	{
		File source_file = new File(source);
		source_file.renameTo(new File(target));
		source_file = null;
	
	   	idc.first();
		idc.insertAfter("status","SUCCESS");
	}
	catch(Exception e)
	{
	   	idc.first();
		idc.insertAfter("status","ERROR");		
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void uncompress (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(uncompress)>> ---
		// @sigtype java 3.5
		// [i] field:0:required zip_file
		// [i] field:0:required target
		// [o] field:0:required file_out
   	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first( "zip_file" );
	String zip_file = (String) idc.getValue();
   	idc.first( "target" );
	String target = (String) idc.getValue();
	String file_name = target;
	FileInputStream istream = null;
	FileOutputStream ostream = null;
	InputStream read_file = null;
	ZipFile zipFileObj = null;

	try
	{
		File source_file = new File(zip_file);
		String temp = source_file.getName().substring(0,source_file.getName().length() - 4);
		file_name += File.separator + temp;

		zipFileObj = new ZipFile(source_file);
		Enumeration enum = zipFileObj.entries();
		ZipEntry zip_entry = null;
		if (enum.hasMoreElements())
			zip_entry = (ZipEntry)enum.nextElement();

		read_file = zipFileObj.getInputStream(zip_entry);
		byte[] b_data = new byte[255];
		int rc = 0;
		istream = new FileInputStream(zip_file);
		ostream = new FileOutputStream(file_name);

		while (rc != -1 && read_file != null)
		{

			// read data from file
			rc = read_file.read(b_data);
			if (rc != -1)
			{
				ostream.write(b_data,0,rc);
			}
		}
		if (read_file != null)
			read_file.close();

		enum = null;
		ostream.close();
		istream.close();
		zipFileObj.close();
		zipFileObj = null;
		ostream = null;
		istream = null;
		b_data = null;
		zip_entry = null;
		source_file = null;
	
	   	idc.first();
		idc.insertAfter("file_out",file_name);
	}
	catch(Exception e)
	{
		try{
		ostream.close();
		istream.close();
		read_file.close();
		ostream = null;
		istream = null;
		zipFileObj.close();
		zipFileObj = null;
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
		throw new ServiceException(e.getMessage());
	}
	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void write (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(write)>> ---
		// @sigtype java 3.5
		// [i] field:0:required string_data
		// [i] field:0:required file_name
		// [i] field:0:optional dir_path
		// [i] field:0:optional option {"true","false"}
		IDataCursor pipelineCursor = pipeline.getCursor();
		
		try{
				pipelineCursor.first( "string_data");
				String str_data = (String) pipelineCursor.getValue();
		       
				pipelineCursor.first( "dir_path");
				String path  = (String) pipelineCursor.getValue();
		 
				pipelineCursor.first( "file_name");
				String file_name = (String) pipelineCursor.getValue();
		
				pipelineCursor.first( "option");
				String option = (String) pipelineCursor.getValue();
		 
				String file_path = file_name;
		
				if (path != null)
				{
					file_path = path + File.separator +  file_name;
				}
		
				
		
		boolean append_flg = false;
				if (option != null)
				{
					if (option.equals("true"))
						append_flg = true;
				}
				FileWriter fw = new FileWriter(file_path, append_flg);
				fw.write(str_data + "\n");
		        
				fw.close();
				fw = null;
		
		 		pipelineCursor.destroy();
		
		} 
		catch(Exception e)
		{
			pipelineCursor.last();
			pipelineCursor.insertAfter("message","Error");
			pipelineCursor.destroy();
		}
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	static long count = 0;
	// --- <<IS-END-SHARED>> ---
}

