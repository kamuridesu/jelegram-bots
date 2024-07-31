package com.kamuri.telegram.services;

import com.kamuri.telegram.model.MessageUpdate;
import com.kamuri.telegram.model.Update;
import com.kamuri.telegram.model.dto.AnswerCallbackQueryDTO;
import com.kamuri.telegram.model.dto.EditMessageDTO;
import com.kamuri.telegram.model.dto.MessageDTO;
import com.kamuri.telegram.model.dto.SendMessageDTO;
import com.kamuri.telegram.model.message.Message;
import com.kamuri.telegram.model.update.CallbackQuery;
import java.util.function.Consumer;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;

public interface TelegramBot {

  /**
   * Send message to chat by using the data inside the SendMessageDTO class
   *
   * @param sendMessageDTO parameters to be used to send the message
   * @return New sent message
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  MessageUpdate sendMessage(SendMessageDTO sendMessageDTO);

  /**
   * Edit message by using the data inside the EditMessageDTO class.
   *
   * @param editMessageDTO parameters to be used to edit the message
   * @return Edited message
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  MessageUpdate editMessage(EditMessageDTO editMessageDTO);

  /**
   * Get updates from the Telegram API.
   *
   * @return Update cotaining Message or Callback actions inside a list.
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  Update getUpdate();

  /**
   * Get updates from the Telegram API.
   *
   * @param messageDTO Last message to register the update id.
   * @return Update cotaining Message or Callback actions inside a list.
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  Update getUpdate(@Nullable MessageDTO messageDTO);

  /**
   * Answer to callback query for user actions.
   *
   * @param answerCallbackQueryDTO parameters to be used to answer the callback.
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  ResponseEntity<String> answerCallbackQuery(AnswerCallbackQueryDTO answerCallbackQueryDTO);

  /**
   * Starts long polling to fetch updates from the api and handle those updates. By default it uses
   * {@link UpdateHandler}.
   *
   * @param interval Integer to sleep the thread while waits for updates to avoid spamming on the
   *     Telegram API.
   * @throws InterruptedException
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  void startPolling(Integer interval) throws InterruptedException;

  /**
   * Starts long polling to fetch updates from the api and handle those updates.
   *
   * @param interval Integer to sleep the thread while waits for updates to avoid spamming on the
   *     Telegram API.
   * @param callback callback to be used to process the updates fetched from the Telegram API.
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   * @throws InterruptedException
   */
  void startPolling(Integer interval, @Nullable Consumer<Update> callback)
      throws InterruptedException;

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
   * Register new message handler to handle messages that match `expectedData`.
   *
   * @param expectedData to be contained in `data` received by the callback query update.
   * @param callback to be executed when a match is found.
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  void registerCallbackHandler(String expectedData, Consumer<CallbackQuery> callback);
}
