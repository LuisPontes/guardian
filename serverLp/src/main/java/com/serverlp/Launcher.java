package com.serverlp;

import com.serverlp.services.email.EmailService;
import com.serverlp.services.guardion.GuardionService;
import com.serverlp.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author devel
 */
public class Launcher {

    /**
     * LOOGER.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Launcher.class);

    /**
     * Status of Launcher.
     */
    private boolean runningLauncher = true;
    /**
     * Services.
     */
    private GuardionService guardionService = null;
    private EmailService serviceEmail = null;

    public static void main(String[] args) {
        Launcher launcher = new Launcher();
        LOGGER.info("[START] Server services ... ");
        launcher.init();
        launcher.run();
    }

    private void init() {
        serviceEmail = new EmailService("smtp.gmail.com", "465", Constants.EMAIL, Constants.PASS_EMAIL);
        LOGGER.info("[CREATE] Guardion service ... ");
        guardionService = GuardionService.getInstance();
        guardionService.setLauncher(this);
    }

    private void run() {
        LOGGER.info("[START] Guardion service ... ");
        guardionService.start();
//        do {
//            try {
//                Thread.sleep(5000);
//            } catch (Exception e) {
//            }
//
//        } while (guardionService.isIsAliveGuardion());
    }

    public EmailService getServiceEmail() {
        return serviceEmail;
    }

}
