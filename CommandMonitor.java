package com.ricky.fastadbcommand;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Helper class to receive commands from shell.
 *
 * As Android SELinux blocks sendto, mkfifo, we use read/write file to simulate fifo, so we
 * can send message from shell to this process.
 */
public class CommandMonitor {
    // private static final String MONITOR_FILE_PATH = "/data/local/tmp/command_monitor";
    // private static final String DEX_TMP_FILE_PATH = "/data/local/tmp/";
    private static final int READ_RETRY_INTERVAL_MS = 5;

    public static void startMonitor(String jarPath, String mainClass, String monitorFilePath,
                                    String dexTmpPath) throws Exception {
        // Init original dex
        final DexClassLoader dcl = new DexClassLoader(jarPath,
                dexTmpPath, null, jarPath.getClass().getClassLoader());
        final Class<Object> classToLoad = (Class<Object>) dcl.loadClass(mainClass);
        final Method mainMethod = classToLoad.getDeclaredMethod("main", String[].class);

        // Remove and touch command monitor file
        try {
            new File(monitorFilePath).delete();
            new File(monitorFilePath).createNewFile();
        } catch (Exception e) {}
        BufferedReader br = new BufferedReader(new FileReader(monitorFilePath));
        String line;
        while (true) {
            line = br.readLine();
            if (line != null) {
                try {
                    String[] args = line.split("\"?( |$)(?=(([^\"]*\"){2})*[^\"]*$)\"?");
                    mainMethod.invoke(classToLoad, (Object) args);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Thread.sleep(READ_RETRY_INTERVAL_MS);
            }
        }
    }
}
