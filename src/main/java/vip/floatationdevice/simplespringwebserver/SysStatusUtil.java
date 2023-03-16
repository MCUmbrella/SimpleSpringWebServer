package vip.floatationdevice.simplespringwebserver;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.util.Calendar;

public class SysStatusUtil
{
    private static final OperatingSystemMXBean os = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
    private static final Runtime rt = Runtime.getRuntime();

    public static String getSysMemMax(){return String.valueOf(os.getTotalPhysicalMemorySize() / 1024 / 1024);}

    public static String getSysMem(){return String.valueOf((os.getTotalPhysicalMemorySize() - os.getFreePhysicalMemorySize()) / 1024 / 1024);}

    public static String getJVMMemMax(){return String.valueOf(rt.maxMemory() / 1024 / 1024);}

    public static String getJVMMem(){return String.valueOf((rt.totalMemory() - rt.freeMemory()) / 1024 / 1024);}

    public static String getSysCpu()
    {
        String l = String.valueOf(os.getSystemCpuLoad() * 100);
        return l.substring(0, Math.min(l.length(), 5)) + "%";
    }

    public static String getJVMCpu()
    {
        String l = String.valueOf(os.getProcessCpuLoad() * 100);
        return l.substring(0, Math.min(l.length(), 5)) + "%";
    }

    public static String getTime(){return Calendar.getInstance().getTime().toString();}
}
