package com.sara.services.web.rest.Util;

import com.sara.services.domain.DefaultResponse;
import com.sara.services.domain.UserResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sara.services.domain.enumeration.SourceChannel;
import com.sara.services.web.rest.response.ResponseMessage;
import java.util.StringTokenizer;
import javax.servlet.http.HttpServletRequest;

public class GeneralUtils {

	public static SourceChannel getOriginAplicationValue(String sourceChannel) {
		if (sourceChannel.equals(SourceChannel.WEB.getValue()))
			return SourceChannel.WEB;
		else if (sourceChannel.equals(SourceChannel.WHATSAPP.getValue()))
			return SourceChannel.WHATSAPP;
		else if (sourceChannel.equals(SourceChannel.TELEGRAM.getValue()))
			return SourceChannel.TELEGRAM;
		else
			return null;
	}
	
	 public static  <T> List<T> convertToList(Set<T> fromSet) {
	        List<T> aList = fromSet.stream().collect(Collectors.toList());
	        return aList;	
	   }

         public static ResponseMessage covertToResponseMessage(UserResponse userResponse){
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setIsEndConversation(userResponse.getIsEndConversation());
            responseMessage.setMultimedia(userResponse.getMultimedia());
            responseMessage.setMultimediaContentType(userResponse.getMultimediaContentType());
            responseMessage.setMultimediaVoice(userResponse.getMultimediaVoice());
            responseMessage.setMultimediaVoiceContentType(userResponse.getMultimediaVoiceContentType());
            responseMessage.setPriority(userResponse.getPriority());
            responseMessage.setSaraAnimation(userResponse.getSaraAnimation());
            responseMessage.setSaraAnimationContentType(userResponse.getSaraAnimationContentType());
            responseMessage.setValueResponse(userResponse.getValueResponse());
            return responseMessage;
         }
         
          public static ResponseMessage covertToResponseMessage(DefaultResponse defaultResponse){
            ResponseMessage responseMessage = new ResponseMessage();
            responseMessage.setIsEndConversation(defaultResponse.getIsEndConversation());
            responseMessage.setMultimedia(defaultResponse.getMultimedia());
            responseMessage.setMultimediaContentType(defaultResponse.getMultimediaContentType());
            responseMessage.setMultimediaVoice(defaultResponse.getMultimediaVoice());
            responseMessage.setMultimediaVoiceContentType(defaultResponse.getMultimediaVoiceContentType());
            responseMessage.setPriority(defaultResponse.getPriority());
            responseMessage.setSaraAnimation(defaultResponse.getSaraAnimation());
            responseMessage.setSaraAnimationContentType(defaultResponse.getSaraAnimationContentType());
            responseMessage.setValueResponse(defaultResponse.getDefaultValueResponse());
            return responseMessage;
         }
          
        public static String getClientIpAddress(HttpServletRequest request) {
        String xForwardedForHeader = request.getHeader("X-Forwarded-For");
        if (xForwardedForHeader == null) {
            return request.getRemoteAddr();
        } else {
            return new StringTokenizer(xForwardedForHeader, ",").nextToken().trim();
        }
    }

}
