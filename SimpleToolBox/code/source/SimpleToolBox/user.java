package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-20 13:41:06 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.*;
import com.wm.app.b2b.server.Session;
import com.wm.app.b2b.server.User;
// --- <<IS-END-IMPORTS>> ---

public final class user

{
	// ---( internal utility methods )---

	final static user _instance = new user();

	static user _newInstance() { return new user(); }

	static user _cast(Object o) { return (user)o; }

	// ---( server methods )---




	public static final void getUser (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getUser)>> ---
		// @sigtype java 3.5
		// [o] field:0:required user_id
	IDataHashCursor idc = pipeline.getHashCursor();
	Session session = Service.getSession();
	User user = session.getUser();

	String user_id = user.getName();

	idc.first();
	idc.insertAfter("user_id",user_id);
		// --- <<IS-END>> ---

                
	}



	public static final void getUserGroup (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(getUserGroup)>> ---
		// @sigtype java 3.5
		// [i] field:0:required userid
		// [o] field:1:required group_list
		// [o] field:0:required userid
	IDataHashCursor idc = pipeline.getHashCursor();
	idc.first("userid");
	String user_id = (String)idc.getValue();

	Session session = Service.getSession();
	User user = session.getUser();
	if (user_id == null)
	{
		user_id = user.getName();
		idc.first();
		idc.insertAfter("userid",user_id);
	}
	String group_name = "";
	Enumeration enum = user.membership();
	int count = 0;
	while(enum.hasMoreElements())
	{
		group_name = (String)enum.nextElement();
		count++;
	}
	String group_array[] = new String[count];
	enum = user.membership();
	count = 0;
	while(enum.hasMoreElements())
	{
		group_name = (String)enum.nextElement();
		group_array[count] = group_name;
		count++;
	}

	idc.first();
	idc.insertAfter("group_list", group_array);

	idc.destroy();
	
		// --- <<IS-END>> ---

                
	}
}

