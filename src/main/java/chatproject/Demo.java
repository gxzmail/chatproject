package chatproject;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author : 何明胜
 *
 * 2017年9月21日
 */
public class Demo {
    public static void main(String[] args) {
        new Demo().testLog4j();
    }

    public  void testLog4j() {
        try {  
            Logger logger = LogManager.getLogger(Demo.class.getName());         
            logger.trace("this is trace");         
            logger.debug("this is debug");         
            logger.info("this is info");       
            logger.warn("this is warn");       
            logger.error("this is error"); 
            logger.fatal("this is fatal");
        } catch (Exception e) {  
            e.printStackTrace();  
        }      
    }
}
