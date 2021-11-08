package egovframework.com.cmm.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import egovframework.com.cmm.LoginVO;
import egovframework.com.cmm.exception.EgovXssException;

public class EgovXssChecker {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(EgovXssChecker.class);
	
	public static boolean checkerUserXss(HttpServletRequest request, String sUniqId) throws Exception {
		
		boolean bLog = false;
		
		try {

	    	if(bLog){
	    		LOGGER.debug("@Step1. XSS Check uniqId  : {}", sUniqId);
	    	}

		//2017.03.03 	조성원 	시큐어코딩(ES)-오류 메시지를 통한 정보노출[CWE-209]
		} catch(IllegalArgumentException e) {
			LOGGER.error("[IllegalArgumentException] Try/Catch...usingParameters Runing : "+ e.getMessage());
		} catch(Exception e) {
			LOGGER.error("["+e.getClass()+"] Try/Catch...Exception : " + e.getMessage());
		}
		return true;
	}


}
