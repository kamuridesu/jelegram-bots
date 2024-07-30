package com.kamuri.telegram.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.kamuri.telegram.config.FeignConfig;
import com.kamuri.telegram.model.MessageUpdate;
import com.kamuri.telegram.model.Update;
import com.kamuri.telegram.model.dto.EditMessageDTO;
import com.kamuri.telegram.model.dto.SendMessageDTO;
import com.kamuri.telegram.model.update.Result;
import com.kamuri.telegram.services.impl.TelegramBot;
import com.kamuri.telegram.services.impl.UpdateHandler;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class TelegramBotTest {

  @Mock private FeignConfig telegramClient;

  @Mock private UpdateHandler updateHandler;

  @InjectMocks private TelegramBot telegramBot;

  @Test
  void shouldRegisterCallback() {
    telegramBot.registerCallbackHandler("test", s -> {});
    verify(updateHandler, times(1)).registerCallbackHandler(anyString(), any());
  }

  @Test
  void shouldRegisterMessage() {
    telegramBot.registerMessageHandler("test", s -> {});
    verify(updateHandler, times(1)).registerMessageHandler(anyString(), any());
  }

  @Test
  void shouldRegisterMessageNoString() {
    telegramBot.registerMessageHandler(s -> {});
    verify(updateHandler, times(1)).registerMessageHandler(any());
  }

  @Test
  void shouldCallAnswerCallback() {
    var expected = ResponseEntity.ok().body("true");
    when(telegramClient.answerCallbackQuery(any())).thenReturn(expected);
    var response = telegramBot.answerCallbackQuery(any());
    assertEquals(expected, response);
  }

  @Test
  void shouldGetMessageUpdateWithNullResult() {
    var expectedUpdate = new Update(true, null);
    when(telegramClient.getUpdate(any())).thenReturn(expectedUpdate);
    assertEquals(expectedUpdate, telegramBot.getUpdate());
  }

  @Test
  void shouldReturnSentMessage() {
    var expected = new MessageUpdate(true, null);
    var dto = new SendMessageDTO("test", "test", null);
    when(telegramClient.sendMessage(dto)).thenReturn(expected);
    assertEquals(expected, telegramBot.sendMessage(dto));
  }

  @Test
  void shouldReturnEditedMessage() {
    var expected = new MessageUpdate(true, null);
    var dto = new EditMessageDTO("test", (long) 123, "test");
    when(telegramClient.editMessageText(dto)).thenReturn(expected);
    assertEquals(expected, telegramBot.editMessage(dto));
  }

  @Test
  void shouldStartPolling() {
    var update = new Update(true, Arrays.asList(new Result(200, null, null)));
    when(telegramClient.getUpdate(any())).thenReturn(update);
    telegramBot.process(1, s -> System.out.println(s));
    verify(telegramClient, times(1)).getUpdate(any());
  }
}
