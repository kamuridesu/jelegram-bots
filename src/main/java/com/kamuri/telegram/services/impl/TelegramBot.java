package com.kamuri.telegram.services.impl;

import com.kamuri.telegram.config.FeignConfig;
import com.kamuri.telegram.model.MessageUpdate;
import com.kamuri.telegram.model.Update;
import com.kamuri.telegram.model.dto.AnswerCallbackQueryDTO;
import com.kamuri.telegram.model.dto.EditMessageDTO;
import com.kamuri.telegram.model.dto.MessageDTO;
import com.kamuri.telegram.model.dto.SendMessageDTO;
import com.kamuri.telegram.model.message.Message;
import com.kamuri.telegram.model.update.CallbackQuery;
import com.kamuri.telegram.services.ITelegramBot;
import java.util.Optional;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TelegramBot implements ITelegramBot {

  private final FeignConfig telegramClient;
  private final UpdateHandler updateHandler;

  private Integer lastUpdateID = 0;

  @Override
  public MessageUpdate editMessage(EditMessageDTO editMessageDTO) {
    return telegramClient.editMessageText(editMessageDTO);
  }

  @Override
  public MessageUpdate sendMessage(SendMessageDTO sendMessageDTO) {
    return telegramClient.sendMessage(sendMessageDTO);
  }

  @Override
  public Update getUpdate() {
    return getUpdate(null);
  }

  @Override
  public Update getUpdate(@Nullable MessageDTO messageDTO) {
    return telegramClient.getUpdate(
        Optional.ofNullable(messageDTO).orElseGet(() -> new MessageDTO(lastUpdateID)));
  }

  @Override
  public ResponseEntity<String> answerCallbackQuery(AnswerCallbackQueryDTO answerCallbackQueryDTO) {
    return telegramClient.answerCallbackQuery(answerCallbackQueryDTO);
  }

  @Override
  public void startPolling(Integer interval) throws InterruptedException {
    startPolling(interval, updateHandler::handle);
  }

  @Override
  public void startPolling(Integer interval, @Nullable Consumer<Update> callback)
      throws InterruptedException {
    while (true) {
      process(interval, callback);
      Thread.sleep(interval * 1000);
    }
  }

  public void process(Integer interval, @Nullable Consumer<Update> callback) {
    var lastUpdate = getUpdate();
    var lastResult = lastUpdate.getResult();
    if (!lastResult.isEmpty()) {
      lastUpdateID = lastResult.get(lastResult.size() - 1).getUpdateId() + 1;
    }
    Optional.ofNullable(callback).ifPresent(s -> s.accept(lastUpdate));
  }

  @Override
  public void registerMessageHandler(Consumer<Message> callback) {
    updateHandler.registerMessageHandler(callback);
  }

  @Override
  public void registerMessageHandler(String pattern, Consumer<Message> callback) {
    updateHandler.registerMessageHandler(pattern, callback);
  }

  @Override
  public void registerCallbackHandler(String expectedData, Consumer<CallbackQuery> callback) {
    updateHandler.registerCallbackHandler(expectedData, callback);
  }
}
