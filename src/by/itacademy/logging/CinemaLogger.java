package by.itacademy.logging;

import by.itacademy.view.CommandLineController;

import java.io.FileInputStream;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class CinemaLogger {
   public static Logger LOGGER;



    static {
        try (FileInputStream ins = new FileInputStream("D:\\itAcademy\\cinema\\src\\by\\itacademy\\logging\\log.properties")) {
            LogManager.getLogManager().readConfiguration(ins);
            LOGGER = Logger.getLogger(CommandLineController.class.getName());
            Handler fileHandler = new FileHandler();
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(fileHandler);
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
    }
}
