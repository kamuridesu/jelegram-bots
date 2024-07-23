package com.kamuri.telegram.config;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.kamuri.telegram.model.MessageUpdate;
import com.kamuri.telegram.model.Update;
import com.kamuri.telegram.model.dto.AnswerCallbackQueryDTO;
import com.kamuri.telegram.model.dto.EditMessageDTO;
import com.kamuri.telegram.model.dto.MessageDTO;
import com.kamuri.telegram.model.dto.SendMessageDTO;

import feign.Headers;

@FeignClient(value = "client", url = "https://api.telegram.org")
@Headers("Accept: application/json")
public interface FeignConfig {

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/bot${spring.telegram.BOT_TOKEN}/sendMessage"
    )
    MessageUpdate sendMessage(SendMessageDTO sendMessageDTO);

    @RequestMapping(
        method = RequestMethod.GET,
        value = "/bot${spring.telegram.BOT_TOKEN}/editMessageText"
    )
    MessageUpdate editMessageText(EditMessageDTO editMessageDTO);

   @RequestMapping(
        method = RequestMethod.GET,
        value = "/bot${spring.telegram.BOT_TOKEN}/getUpdates"
   )
   Update getUpdate(MessageDTO messageDTO);

   @RequestMapping(
        method = RequestMethod.GET,
        value = "/bot${spring.telegram.BOT_TOKEN}/answerCallbackQuery"
   )
   ResponseEntity<String> answerCallbackQuery(AnswerCallbackQueryDTO answerCallbackQueryDTO);
}
