/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serverlp.utils;

/**
 *
 * @author devel
 */
public class Constants {

   

    private Constants() {
    }

    /**
     * GUARDION IP CONSTANTS.
     */
    public static final long DELAY_TIMER_CHECK_IP = 0;
    public static final long PERIOD_TIMER_CHECK_IP = 86400000;//86400000=24hours | 10000;// 10 sec
    public static final String FILE_NAME_LAST_IP = "guardion.txt";

    public static final String HOSTNAME_SERVER = "serverlp.ddns.net";

    //94.60.97.38
    /**
     * COMMANDS.
     */
    public static final int PROCESS_ID_COLUMN = 2;
    public static final int PROCESS_NAME_COLUMN = 11;

    public static final String EXEC_MORLA_PROJECT = "screen java -Xmx1256m  -jar /var/server_java/MORLA.app-0.0.1-SNAPSHOT.jar";
    public static final String EXEC_NOIP_SERVICE = "/usr/local/bin/noip2";

    /**
     * Email.
     */
    public static final String EMAIL = "xxxxxx@gmail.com";
    public static final String PASS_EMAIL = "xxxxxxxx";
    public static String SUBJECT_EMAIL = "Reports GUARDION ServerLp!";
     public static String buildMsg(String new_ip, StatusService ipCheck, StatusService hostMaskCheck, StatusService noIpServiceCheck, StatusService morlaProjectCheck,String warningMsg) {
        StringBuilder b = new StringBuilder();
        b.append(warningMsg).append("\n");
        b.append("Services State :").append("\n");
        b.append("Public IP = [ ").append(new_ip).append(" ]").append("\n");
        b.append("Host Mask = [ ").append(hostMaskCheck).append(" ]").append("\n");
        b.append("NO-IP Service = [ ").append(noIpServiceCheck).append(" ]").append("\n");
        b.append("MORLA Project  = [ ").append(morlaProjectCheck).append(" ]").append("\n");

        return b.toString();
    }

    public enum StatusService {

        NOT_OK,
        OK;

        private StatusService() {
        }

    }

}
