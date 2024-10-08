package com.kamuri.telegram.executor;

import com.kamuri.telegram.model.MessageUpdate;
import com.kamuri.telegram.model.dto.AnswerCallbackQueryDTO;
import com.kamuri.telegram.model.dto.EditMessageDTO;
import com.kamuri.telegram.model.dto.InlineKeyboardMarkupDTO;
import com.kamuri.telegram.model.dto.SendMessageDTO;
import com.kamuri.telegram.model.dto.keyboard.InlineKeyboardButton;
import com.kamuri.telegram.model.update.CallbackQuery;
import com.kamuri.telegram.services.TelegramBot;
import com.kamuri.telegram.util.MessageUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Profile("default-runner")
public class MainExecutor implements CommandLineRunner {

  @Value("${spring.telegram.BOT_TOKEN}")
  private String TOKEN;

  @Value("${spring.telegram.CHAT_ID}")
  private String CHAT_ID;

  private final TelegramBot telegramBot;

  @Override
  public void run(String... args) throws InterruptedException {
    InlineKeyboardButton[][] button = {
      {new InlineKeyboardButton("Sysout", "sysout")},
      {new InlineKeyboardButton("Edit", "edit"), new InlineKeyboardButton("Exit", "exit")}
    };

    var markup = new InlineKeyboardMarkupDTO(button);
    var message = new SendMessageDTO(CHAT_ID, "test", markup);
    var response = telegramBot.sendMessage(message);

    telegramBot.registerCallbackHandler("*", s -> handleCQ(s, response));
    telegramBot.registerMessageHandler(
        "/start",
        s -> {
          System.out.println(s.getText());
          telegramBot.sendMessage(
              new SendMessageDTO(CHAT_ID, "Hello " + s.getFrom().getFirstName(), markup));
        });

    telegramBot.registerMessageHandler(s -> System.out.println(s));
    telegramBot.startPolling(1);
  }

  private void handleCQ(CallbackQuery callbackQuery, MessageUpdate response) {
    var textToAnswer = "main";

    if (callbackQuery.getData().equals("exit")) textToAnswer = "Bye!";

    if (callbackQuery.getData().equals("edit")) textToAnswer = "Edited!";

    var answer = new AnswerCallbackQueryDTO(callbackQuery.getId(), textToAnswer, true);

    try {
      telegramBot.answerCallbackQuery(answer);

      if (callbackQuery.getData().equals("exit")) System.exit(0);

      if (callbackQuery.getData().equals("edit")) {
        var msg = new EditMessageDTO(CHAT_ID, response.getResult().getMessageId(), "ababa");
        telegramBot.editMessage(msg);
      }

    } catch (feign.FeignException.BadRequest badRequest) {
      System.out.println(
          "Fail to answer callback: " + MessageUtils.maskToken(badRequest.getMessage(), TOKEN));
    }
  }
}
