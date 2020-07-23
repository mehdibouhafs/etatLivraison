package ma.munisys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;





public class ContratUtil {
	
	
	/**
	 * Get a diff between two dates
	 * @param date1 the oldest date
	 * @param date2 the newest date
	 * @param timeUnit the unit in which you want the diff
	 * @return the diff value, in the provided unit
	 * @throws ParseException 
	 */
	public static long getDateDiff(Date issueDate1, Date issueDate2, TimeUnit timeUnit) throws ParseException {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		
		return ChronoUnit.MONTHS.between( LocalDate.parse(sdf.format(issueDate1)).withDayOfMonth(1), LocalDate.parse(sdf.format(issueDate2)).withDayOfMonth(1));
		
		
	}

}
