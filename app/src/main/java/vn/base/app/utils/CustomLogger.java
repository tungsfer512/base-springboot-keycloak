package vn.base.app.utils;

public class CustomLogger {

    public static void system(Object... objs) {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        String logStart = "############## START SYSTEM";
        String logEnd = "##############  END SYSTEM";
        if (stackTraces.length > 2) {
            logStart += " : " + stackTraces[2].getClassName() + " : " + stackTraces[2].getLineNumber();
            logEnd += " : " + stackTraces[2].getClassName() + " : " + stackTraces[2].getLineNumber();
        } else if (stackTraces.length > 1) {
            logStart += " : " + stackTraces[1].getClassName() + " : " + stackTraces[1].getLineNumber();
            logEnd += " : " + stackTraces[1].getClassName() + " : " + stackTraces[1].getLineNumber();
        }
        logStart += " ##############";
        logEnd += "  ##############";
        System.out.println(logStart);
        for (Object obj : objs) {
            System.out.println(obj);
        }
        System.out.println(logEnd);
    }

    public static void info(Object... objs) {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        String logStart = "============== START INFO";
        String logEnd = "==============  END INFO";
        if (stackTraces.length > 2) {
            logStart += " : " + stackTraces[2].getClassName() + " : " + stackTraces[2].getLineNumber();
            logEnd += " : " + stackTraces[2].getClassName() + " : " + stackTraces[2].getLineNumber();
        } else if (stackTraces.length > 1) {
            logStart += " : " + stackTraces[1].getClassName() + " : " + stackTraces[1].getLineNumber();
            logEnd += " : " + stackTraces[1].getClassName() + " : " + stackTraces[1].getLineNumber();
        }
        logStart += " ==============";
        logEnd += "  ==============";
        System.out.println(logStart);
        for (Object obj : objs) {
            System.out.println(obj);
        }
        System.out.println(logEnd);
    }

    public static void warn(Object... objs) {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        String logStart = "************** START WARN";
        String logEnd = "**************  END WARN";
        if (stackTraces.length > 2) {
            logStart += " : " + stackTraces[2].getClassName() + " : " + stackTraces[2].getLineNumber();
            logEnd += " : " + stackTraces[2].getClassName() + " : " + stackTraces[2].getLineNumber();
        } else if (stackTraces.length > 1) {
            logStart += " : " + stackTraces[1].getClassName() + " : " + stackTraces[1].getLineNumber();
            logEnd += " : " + stackTraces[1].getClassName() + " : " + stackTraces[1].getLineNumber();
        }
        logStart += " **************";
        logEnd += "  **************";
        System.out.println(logStart);
        for (Object obj : objs) {
            System.out.println(obj);
        }
        System.out.println(logEnd);
    }

    public static void error(Object... objs) {
        StackTraceElement[] stackTraces = Thread.currentThread().getStackTrace();
        String logStart = "-------------- START ERROR";
        String logEnd = "--------------  END ERROR";
        if (stackTraces.length > 2) {
            logStart += " : " + stackTraces[2].getClassName() + " : " + stackTraces[2].getLineNumber();
            logEnd += " : " + stackTraces[2].getClassName() + " : " + stackTraces[2].getLineNumber();
        } else if (stackTraces.length > 1) {
            logStart += " : " + stackTraces[1].getClassName() + " : " + stackTraces[1].getLineNumber();
            logEnd += " : " + stackTraces[1].getClassName() + " : " + stackTraces[1].getLineNumber();
        }
        logStart += " --------------";
        logEnd += "  --------------";
        System.out.println(logStart);
        for (Object obj : objs) {
            System.out.println(obj);
        }
        System.out.println(logEnd);
    }

}
