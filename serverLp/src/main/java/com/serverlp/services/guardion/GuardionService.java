/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.serverlp.services.guardion;

import com.serverlp.Launcher;
import com.serverlp.utils.Constants;
import com.serverlp.utils.Constants.StatusService;
import com.serverlp.utils.FileManagement;
import com.serverlp.utils.RunCommands;
import java.io.File;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author devel
 */
public class GuardionService {

    /**
     * LOOGER.
     */
    private final Logger LOGGER = LoggerFactory.getLogger(GuardionService.class);

    /**
     * Singleton pattern.
     */
    private static final AtomicReference<GuardionService> SINGLETON = new AtomicReference();

    /**
     * Launcher launcher.
     */
    private Launcher launcher = null;

    /**
     * TIMER.
     */
    private Timer GUARDION_TIMER;

    /**
     * Ip.
     */
    private String current_ip = "";

    /**
     * Service Status.
     */
    private StatusService ipCheck;
    private StatusService hostMaskCheck;
    private StatusService noIpServiceCheck;
      private StatusService morlaProjectCheck;

    /**
     * GuardionStatus.
     */
    private static boolean isAliveGuardion = false;

    public static boolean isIsAliveGuardion() {
        return isAliveGuardion;

    }

    private GuardionService() {
    }

    /**
     * Returns an instance of singleton class.
     *
     * @return The instance.
     */
    public static GuardionService getInstance() {
        if (null == SINGLETON.get()) {
            SINGLETON.set(new GuardionService());
            isAliveGuardion = true;
        }
        return SINGLETON.get();
    }

    public void setLauncher(Launcher launcher) {
        this.launcher = launcher;
    }

    public void start() {

        //CHECK IP
        if (FileManagement.fileExist(Constants.FILE_NAME_LAST_IP)) {
            //if exist           
            current_ip = FileManagement.readFile(Constants.FILE_NAME_LAST_IP).trim();
        } else {
            //not exist , get current ip
            current_ip = RunCommands.getIp();
            FileManagement.writeFile(Constants.FILE_NAME_LAST_IP, current_ip);
        }
        

        if (null != current_ip) {

            GUARDION_TIMER = new Timer("GUARDION");
            GUARDION_TIMER.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {

                    /**
                     * Validate ip.
                     */
                    String new_ip = RunCommands.getIp().trim();
                    
                    if (!new_ip.equalsIgnoreCase(current_ip)) {
                        //enviar email com o novo ip.   
                        LOGGER.info("ENVIAR EMAIL COM O NOVO IP  [{}]", new_ip);
                        current_ip = new_ip;
                        if (FileManagement.deleteFile(new File(Constants.FILE_NAME_LAST_IP))) {
                            if (FileManagement.writeFile(Constants.FILE_NAME_LAST_IP, current_ip)) {
                                LOGGER.info("[ERROR] Create file ... [{}]", Constants.FILE_NAME_LAST_IP);
                            }
                        } else {
                            LOGGER.info("[ERROR] Delete file ... [{}]", Constants.FILE_NAME_LAST_IP);
                        }
                        ipCheck = StatusService.NOT_OK; 

                    } else {
                        ipCheck = StatusService.OK;                        
                    }
                    LOGGER.info("["+ipCheck+"]Current IP ... [{}]", current_ip);
                    

                    /**
                     * VAlidate no-ip Process.
                     */
                    String noipExec = RunCommands.checkNoIp();
                    if (null==noipExec) {
                        RunCommands.execCmd(Constants.EXEC_NOIP_SERVICE);
                        noIpServiceCheck = StatusService.NOT_OK;
                    }else{
                        noIpServiceCheck = StatusService.OK;
                    }
                    LOGGER.info("["+noIpServiceCheck+"] No-IP Service.");

                   
                    /**
                     * Validate host if the name have correct ip.
                     */
                    //check host mask
                    String host_ip = RunCommands.checkHost(Constants.HOSTNAME_SERVER);
                    String[] host_ip_parts = host_ip.split(" ");

                    if (!current_ip.equals(host_ip_parts[host_ip_parts.length - 1])) {
                        //not equal its ok
                        hostMaskCheck = StatusService.NOT_OK;
                        LOGGER.info("ENVIAR EMAIL COM AVISO que o host tem um ip diferente  [{}]", current_ip);
                    } else {
                        hostMaskCheck = StatusService.OK;                         
                    }
                    LOGGER.info("["+hostMaskCheck+"]Host Name [{}] - IP [{}] -> Correct ip [{}]",Constants.HOSTNAME_SERVER, host_ip_parts[host_ip_parts.length - 1], current_ip);

                    /**
                     * Validate backOficce morla project process.
                     */
//                    String pId = RunCommands.getProcessIdByName(Constants.EXEC_MORLA_PROJECT,Constants.PROCESS_ID_COLUMN);
                    String pName = RunCommands.getProcessIdByName(Constants.EXEC_MORLA_PROJECT,Constants.PROCESS_NAME_COLUMN);
                    if (!Constants.EXEC_MORLA_PROJECT.equalsIgnoreCase(pName)) {
                         RunCommands.execCmd(Constants.EXEC_MORLA_PROJECT);
                         morlaProjectCheck=StatusService.NOT_OK;
                    }else{
                        morlaProjectCheck=StatusService.OK;
                    }
                    LOGGER.info("["+morlaProjectCheck+"] Morla WEB Project.  ");
                   
                }
            },
                    Constants.DELAY_TIMER_CHECK_IP,
                    Constants.PERIOD_TIMER_CHECK_IP
            );

        } else {
            // cant get ip
            isAliveGuardion = false;
            LOGGER.info("Something wrong happens! Canot get IP ... [NULL]");
        }
    }

    public static void main(String[] args) {

        String result = "serverlp.ddns.net has address 94.60.97.38";
        String[] part = result.split(" ");
        System.out.println("" + part[part.length - 1]);
    }
}
