/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serverlp.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 *
 * @author devel
 */
public class RunCommands {

    public static String getIp() {
        String[] cmd = {"/bin/bash","-c","wget -qO- http://ipecho.net/plain | xargs echo"};
        return RunTimeExec(cmd);
    }

    public static String checkNoIp() {
        String[] cmd = {"/bin/bash", "-c", Constants.EXEC_NOIP_SERVICE};
        return RunTimeExec(cmd);
    }

    public static String checkHost(String serverlpddnsnet) {
        String[] cmd = {"/bin/bash","-c","host " + serverlpddnsnet};
        return RunTimeExec(cmd);
    }

    public static String getProcessIdByName(String processName,int column) {
        String[] cmd = {"/bin/bash", "-c", "ps aux | grep '" + processName + "' | awk '{print $"+column+"}'"};
        return RunTimeExec(cmd);

    }

    public static String execCmd(String EXEC_CMD) {
        String[] cmd = {"/bin/bash", "-c", EXEC_CMD};
        return RunTimeExec(cmd);
    }

    private static String RunTimeExec(String[] cmd) {
        String result = "",line="";
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((line = stdInput.readLine()) != null) {
                if (null!=line) {
                    result += line;
                }
//                System.out.println(result);
            }
            p.waitFor();
        } catch (Exception e) {
        }
        return result;
    }

//    public static void main(String[] args) {
//         System.out.println(RunCommands.getIp());
//         System.out.println(RunCommands.checkNoIp());
//        System.out.println(RunCommands.checkHost("serverlp.ddns.net"));
//System.out.println(RunCommands.getProcessIdByName("sudo screen java -Xmx1256m  -jar /var/server_java/MORLA.app-0.0.1-SNAPSHOT.jar") );
//    System.out.println(RunCommands.getProcessIdByName("java") );
//    }
}
