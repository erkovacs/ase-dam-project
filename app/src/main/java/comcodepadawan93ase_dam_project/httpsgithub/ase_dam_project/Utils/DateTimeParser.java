package comcodepadawan93ase_dam_project.httpsgithub.ase_dam_project.Utils;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeParser {
    static public final long parseDate(String date) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y");
        Date parsedDate = dateFormat.parse(date);
        // Timestamp timestamp = new java.sql.Timestamp(parsedDate.getTime());
        return parsedDate.getTime();
    }

    static public final String parseTimestamp(long timestamp) throws Exception {
        SimpleDateFormat dateFormat = new SimpleDateFormat("d.M.y");
        return dateFormat.format(new Date(timestamp));
    }
}
