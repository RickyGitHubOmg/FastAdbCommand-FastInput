package com.ricky.fastadbcommand;

public class Main {
    public static void main(String[] args) {
        final String jarFile = args[0];
        final String mainClass = args[1];
        final String fileMonitorPath = args[2];
        final String tmpDexPath = args[3];
        System.out.println("Jar file: " + jarFile);
        System.out.println("Main class: " + mainClass);
        System.out.println("File monitor path: " + fileMonitorPath);
        System.out.println("Tmp dex path: " + tmpDexPath);
        try {
            CommandMonitor.startMonitor(jarFile, mainClass, fileMonitorPath, tmpDexPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
