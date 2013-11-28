package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-25 13:37:14 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.*;
import com.wm.app.b2b.server.Session;
import java.io.*;
import com.wm.app.b2b.server.ServerAPI;
import com.wm.app.b2b.server.*;
import com.wm.util.JournalLogger;
// --- <<IS-END-IMPORTS>> ---

public final class sys

{
	// ---( internal utility methods )---

	final static sys _instance = new sys();

	static sys _newInstance() { return new sys(); }

	static sys _cast(Object o) { return (sys)o; }

	// ---( server methods )---



    public static final Values get_object_from_session (Values in)
    {
        Values out = in;
		// --- <<IS-START(get_object_from_session)>> ---
		// @sigtype java 3.0
		// [i] field:0:required objectName
		// [o] object:0:required object
	String strObjectName = in.getString("objectName");
	Session session = Service.getSession();
	out.put("object", session.get(strObjectName));
		// --- <<IS-END>> ---
        return out;
                
	}



	public static final void get_package_config_dir (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_package_config_dir)>> ---
		// @sigtype java 3.5
		// [o] field:0:required pkg_config
		// [o] field:1:required config_file_list
 	IDataHashCursor idc = pipeline.getHashCursor();

	// Get input values
   	idc.first();
	String key = idc.getKey();
	
	Values vl = ValuesEmulator.getValues(pipeline, key);
	String pkg = Service.getPackageName(vl);
	File fl = ServerAPI.getPackageConfigDir(pkg);
	String config_dir = fl.getPath();

	try
	{	
		// Get list of files in a give directory
        File fname = new File(config_dir);
        String[] file_list = fname.list();
		fname = null;
	   	idc.first();
		idc.insertAfter("config_file_list", file_list);
	}
	catch(Exception e)
	{
		throw new ServiceException(e.getMessage());
	}

	// Setup output message
	idc.first();
	idc.insertAfter("pkg_config",config_dir);

	idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void get_server_url (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(get_server_url)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [o] field:0:required server_url
		
		// pipeline
		
		int port = ServerAPI.getCurrentPort();
		String svr_host = ServerAPI.getServerName();
		
		String server_url = "http://" + svr_host + ":" + Integer.toString(port);
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		IDataUtil.put( pipelineCursor, "server_url", server_url );
		pipelineCursor.destroy();
		// --- <<IS-END>> ---

                
	}


    public static final Values put_object_in_session (Values in)
    {
        Values out = in;
		// --- <<IS-START(put_object_in_session)>> ---
		// @sigtype java 3.0
		// [i] object:0:required object
		// [i] field:0:required objectName
	Object obj = in.get("object");
	String strObjectName = in.getString("objectName");
	Session session = Service.getSession();

	// Data type
	Object tobj = session.get(strObjectName);
	if (tobj != null)
		session.remove(strObjectName);

	session.put(strObjectName, obj);
		// --- <<IS-END>> ---
        return out;
                
	}



	public static final void run_garbage_collector (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(run_garbage_collector)>> ---
		// @sigtype java 3.5
		System.gc();
		// --- <<IS-END>> ---

                
	}



	public static final void run_sys_command (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(run_sys_command)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required sys_command
		// [i] field:0:optional timeout
		// [o] field:0:required successFlag
		// [o] field:0:required exitCode
		// [o] field:0:required error
		// [o] field:0:required TimeoutStatus
		//define input variables 
		IDataCursor idcPipeline = pipeline.getCursor();
		String 	strFileContent = null;
		
		//Output Variables 
		String successFlag = "false";
		String fullCommand;
		String proc_exit_code = "";
		String error_msg = "";
		long Timeout = 60000;
		long SLEEP_TIME = 100;
		String TimeoutStatus = new Boolean(false).toString();
		long starttime = new java.util.Date().getTime();
		
		// Check to see if the fullCommand object is in the pipeline
		if (idcPipeline.first("sys_command"))
		{	
			//get the fullCommand stream out of the pipeline					
			fullCommand = (String) idcPipeline.getValue();
		}
		//if it is not in the pipeline, then handle the error
		else
		{
			idcPipeline.destroy();
			JournalLogger.log(3, 90, 4, "Error executing system Exec: Required parameter missing.");
			return;
		}
		
		try
		{
			Timeout = Long.parseLong(IDataUtil.getString( idcPipeline, "timeout" ));
		}
		catch (NumberFormatException e) {}
		
		if(Timeout <= 0) Timeout = 60000;
		
		//Always destroy cursors that you created
		idcPipeline.destroy();
		
		//Run the command
			
		//Execute the command. Handle the exception if it fails.
		try 
		{
		 	Process child =   Runtime.getRuntime().exec(fullCommand);
			// Get I/O streams form new spawned child process
			BufferedInputStream err  = new BufferedInputStream(child.getErrorStream());
			BufferedInputStream in   = new BufferedInputStream(child.getInputStream());
			BufferedOutputStream out = new BufferedOutputStream(child.getOutputStream());
				
			StringBuffer i = new StringBuffer();
			StringBuffer e = new StringBuffer();
		
			boolean continue_run = true;
			while (continue_run)
			{
				try
				{
					int proc_status = child.exitValue(); // throws exception if spawned proc still running
					continue_run = false; // only executes once spawned proc has halted
					proc_exit_code = new Integer(proc_status).toString();
				} 
				catch ( IllegalThreadStateException itse )
				{ // Spawned process is still running. Gather output for this example
					while (in.available() > 0)
						i.append((char)in.read());
				
					while (err.available() > 0)
						e.append((char)err.read());
									
					long now = new java.util.Date().getTime();
					if (now-starttime >= Timeout)
					{
						continue_run = false;
						TimeoutStatus = new Boolean(true).toString();
					} 
					else 
					{
					    try 
					    {
						// better sleep awhile so we don't peg out the CPU
						Thread.sleep(SLEEP_TIME);
					    } catch (InterruptedException iex){}
					}
					
				}
			}
		
			// drain remaining text
			while(in.available() > 0)
				i.append((char)in.read());
			
			while(err.available() > 0)
				e.append((char)err.read());
		
			in.close();
			err.close();
			out.close();
			
			error_msg = e.toString();
		
			successFlag = "true";
		} 
		catch (IOException e) 
		{
			//Write the error to server.log
			JournalLogger.log(3, 90, 4, "Error executing system command: " + e.toString() + " COMMON_SYSTEM.run_sys_command");
			successFlag="false";
		}
		
		//insert the successFlag into the pipeline
		idcPipeline = pipeline.getCursor();
		idcPipeline.insertAfter("successFlag", successFlag);	
		idcPipeline.insertAfter("exitCode", proc_exit_code);
		idcPipeline.insertAfter("error", error_msg);
		idcPipeline.insertAfter("TimeoutStatus", TimeoutStatus);
		
		//Always destroy cursors that you created
		idcPipeline.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void sleep (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(sleep)>> ---
		// @sigtype java 3.5
		// [i] field:0:required sleep_time
		/* Service will put the current thread to sleep for given time seconds.
		 * time in milliseconds
		 *
		 * @author Igor Androsov
		 */
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	sleep_time = IDataUtil.getString( pipelineCursor, "sleep_time" );
		pipelineCursor.destroy();
		
		try 
		{
		   if (sleep_time != null)
		   {
			long st = Long.parseLong(sleep_time);	
			Thread.sleep(st);
		   }
		}
		catch(InterruptedException e){}
		// --- <<IS-END>> ---

                
	}
}

