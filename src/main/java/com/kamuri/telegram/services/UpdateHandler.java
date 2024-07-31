package com.kamuri.telegram.services;

import com.kamuri.telegram.model.Update;
import com.kamuri.telegram.model.message.Message;
import com.kamuri.telegram.model.update.CallbackQuery;
import java.util.function.Consumer;

public interface UpdateHandler {

  /**
   * Register new message handler to handle messages that match `expectedData`.
   *
   * @param expectedData to be contained in `data` received by the callback query update.
   * @param callback to be executed when a match is found.
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  void registerCallbackHandler(String expectedData, Consumer<CallbackQuery> callback);

  /**
   * Register new message handler to handle every message without another handler registered.
   *
   * @param callback to be executed when a match is found.
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  void registerMessageHandler(Consumer<Message> callback);

  /**
   * Register new message handler to handle messages that match `pattern`.
   *
   * @param pattern to be matched by message, like /start
   * @param callback to be executed when a match is found.
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  void registerMessageHandler(String pattern, Consumer<Message> callback);

  /**
   * Handles updates comming from the Telegram API.
   *
   * @param update to be handled
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  void handle(Update update);
}
