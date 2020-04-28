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
    public static final long PERIOD_TIMER_CHECK_IP = 10000;// 10 sec
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

    public enum StatusService {

        NOT_OK,
        OK;

        private StatusService() {
        }

    }

}
