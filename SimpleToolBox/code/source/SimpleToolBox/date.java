package SimpleToolBox;

// -----( IS Java Code Template v1.2
// -----( CREATED: 2005-04-20 13:50:23 JST
// -----( ON-HOST: xiandros-c640

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.*;
import java.text.SimpleDateFormat;
// --- <<IS-END-IMPORTS>> ---

public final class date

{
	// ---( internal utility methods )---

	final static date _instance = new date();

	static date _newInstance() { return new date(); }

	static date _cast(Object o) { return (date)o; }

	// ---( server methods )---




	public static final void compare_date (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(compare_date)>> ---
		// @sigtype java 3.5
		// [o] field:0:required status
	// This method compares 2 dates 
	// Get date parms from IData hash for validation
   	IDataHashCursor pipelineCursor = pipeline.getHashCursor();

	// Get start date
   	pipelineCursor.first( "Date_Start" );
	IData start_date = (IData) pipelineCursor.getValue();
	IDataHashCursor start_idc = start_date.getHashCursor();

	// Get end date
	pipelineCursor.next( "Date_End" );
	IData end_date = (IData) pipelineCursor.getValue();
	IDataHashCursor end_idc = end_date.getHashCursor();

	String str = "none";
	try
	{
		start_idc.first( "YEAR" );
		String strY = (String)start_idc.getValue();
		start_idc.next( "MONTH" );
		String strM = (String)start_idc.getValue();
		start_idc.next( "DAY" );
		String strD = (String)start_idc.getValue();
		
		System.out.println(strY);
		int yy = Integer.parseInt(strY);
		int mm = Integer.parseInt(strM);
		int dd = Integer.parseInt(strD);

		Calendar start_cal = Calendar.getInstance();
		start_cal.set(yy,mm,dd);

		end_idc.first( "YEAR" );
		strY = (String)end_idc.getValue();
		end_idc.next( "MONTH" );
		strM = (String)end_idc.getValue();
		end_idc.next( "DAY" );
		strD = (String)end_idc.getValue();
		System.out.println(strY);
		yy = Integer.parseInt(strY);
		mm = Integer.parseInt(strM);
		dd = Integer.parseInt(strD);

		Calendar end_cal = Calendar.getInstance();
		end_cal.set(yy,mm,dd);

		if (start_cal.equals(end_cal))
		{
			str = "EQUALS";
		}
		else if (start_cal.before(end_cal))
		{
			str = "BEFORE";
		}
		else if (start_cal.after(end_cal))
		{
			str = "AFTER";
		}
		pipelineCursor.last();
		pipelineCursor.insertAfter( "status", str );
		pipelineCursor.destroy();

	}
	catch(Exception e)
	{
		// Date format is invalid
		str = "INVALID";
		pipelineCursor.last();
		pipelineCursor.insertAfter( "status", str );
		pipelineCursor.destroy();
	}		
		// --- <<IS-END>> ---

                
	}



	public static final void convert_milli_to_time (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convert_milli_to_time)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required logtime_milli
		// [o] field:0:required timestamp
		 
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	logtime_milli = IDataUtil.getString( pipelineCursor, "logtime_milli" );
		pipelineCursor.destroy();
		
		    Long l = new Long(logtime_milli);
		    long ld = l.longValue();
		    java.util.Date dt = new java.util.Date(ld);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "timestamp", dt.toString() );
		pipelineCursor_1.destroy();
		
		//Sample timestamp in milliseconds
		// Long l = new Long("1049325765591"); //April 3 2003 08:22:45
		    
		// --- <<IS-END>> ---

                
	}



	public static final void convert_time_to_milli (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convert_time_to_milli)>> ---
		// @subtype unknown
		// @sigtype java 3.5
		// [i] field:0:required dateStr
		// [i] field:0:required pattern
		// [o] field:0:required logtime_milli
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	dateStr = IDataUtil.getString( pipelineCursor, "dateStr" );
			String	pattern = IDataUtil.getString( pipelineCursor, "pattern" );
		pipelineCursor.destroy();
		long ld = 0;
		try
		{
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			Date d = df.parse(dateStr);
			ld = d.getTime();
		}
		catch(Exception e)
		{
			throw new ServiceException(e.getMessage());
		}
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "logtime_milli", Long.toString(ld) );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void create_db_date_format (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(create_db_date_format)>> ---
		// @sigtype java 3.5
		// [i] field:0:required dateStr
		// [i] field:0:required pattern
		// [o] field:0:required dateStr
		/** This service gets a date string and checks if it is a valid date as per
		the given pattern, if it is, the date is changed to a Oracle db format dd-MMM-yy and output within quotes
		so that it can be used directly in the dbSQL without using quotes in the 
		variable substitution stage.
		if it is not a valid date, the datestr is changed to null
		**/
			IDataHashCursor idc = pipeline.getHashCursor();
		
			try{
				idc.first("pattern");
				String pattern = (String) idc.getValue();
				String dbPattern = "dd-MMM-yy";
				idc.first("dateStr");
				String dateStr = (String) idc.getValue();
				// Remove quotes for parsing
		        dateStr = dateStr.replace('\'',' ');
		        dateStr = dateStr.trim();
		
				SimpleDateFormat df = new SimpleDateFormat(pattern);
				SimpleDateFormat sdf = new SimpleDateFormat(dbPattern);
		
				Date d = df.parse(dateStr);
		
				Calendar cal_1950 = Calendar.getInstance();
				// Compare to 1950 date
				cal_1950.set(1950,Calendar.JANUARY,1);
				Calendar cal_2050 = Calendar.getInstance();
				// Compare to 2050 date
				cal_2050.set(2050,Calendar.JANUARY,1);
		
				Calendar end_cal = Calendar.getInstance();
				end_cal.setTime(d);
				
				if (end_cal.after(cal_1950))
				{
					dateStr = sdf.format(d);
					idc.first("dateStr");
					idc.delete();
					idc.insertAfter("dateStr","'" + dateStr+ "'");
				}
				else
				{
					idc.first("dateStr");
					idc.delete();
					idc.insertAfter("dateStr","null");
				}
			}
			catch(Exception ex)
			{
				idc.first("dateStr");
				idc.delete();
				idc.insertAfter("dateStr","null");
			}
			idc.destroy();
		// --- <<IS-END>> ---

                
	}



	public static final void validate_date (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(validate_date)>> ---
		// @sigtype java 3.5
		// [i] field:0:required day
		// [i] field:0:required month
		// [i] field:0:required year
		// [o] field:0:required status
    // This service validates DATE to appropriate range and format

    // Get date parms from IData hash for validation
    IDataHashCursor pipelineCursor = pipeline.getHashCursor();
    pipelineCursor.first( "day" );
	String dd = (String) pipelineCursor.getValue();
	pipelineCursor.next("month");
	String mm = (String) pipelineCursor.getValue();
	pipelineCursor.next("year");
	String yy = (String) pipelineCursor.getValue();
	String rc = "TRUE";

	try
	{
        int int_DD = Integer.parseInt(dd);
        int int_MM = Integer.parseInt(mm);
        int int_YYYY = Integer.parseInt(yy);
	
        if (int_DD > 31 || int_DD < 1)
		{
            rc = "FALSE";
			pipelineCursor.last();
			pipelineCursor.insertAfter( "status", rc );
			pipelineCursor.destroy();
			return;
		}
        if (int_MM > 12 || int_MM < 1)
		{
            rc = "FALSE";
			pipelineCursor.last();
			pipelineCursor.insertAfter( "status", rc );
			pipelineCursor.destroy();
			return;
		}

        if (int_YYYY < 1999 || int_YYYY > 2050)
		{
            rc = "FALSE";
			pipelineCursor.last();
			pipelineCursor.insertAfter( "status", rc );
			pipelineCursor.destroy();
			return;
		}

		pipelineCursor.last();
		pipelineCursor.insertAfter( "status", rc );
		pipelineCursor.destroy();
	}
	catch (Exception e)
	{
		pipelineCursor.last();
		pipelineCursor.insertAfter( "status", rc );
		pipelineCursor.destroy();

	}
		// --- <<IS-END>> ---

                
	}
}

