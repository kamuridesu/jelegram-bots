package com.kamuri.telegram.services.impl;

import com.kamuri.telegram.model.Handler;
import com.kamuri.telegram.model.Update;
import com.kamuri.telegram.model.message.Message;
import com.kamuri.telegram.model.update.CallbackQuery;
import com.kamuri.telegram.services.UpdateHandler;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Getter
public class UpdateHandlerImpl implements UpdateHandler {

  private List<Handler<CallbackQuery>> callbackHandlers = new ArrayList<>();
  private List<Handler<Message>> messageHandlers = new ArrayList<>();
  private Set<CompletableFuture<Object>> runningTasks = new HashSet<>();

  private Comparator<? super Handler<?>> comparator =
      (x, y) ->
          x.getIdentifier().equals("*") && !y.getIdentifier().equals("*")
              ? 1
              : !x.getIdentifier().equals("*") && y.getIdentifier().equals("*") ? -1 : 0;

  /**
   * * Handles Callback updates, filtering the registered callbacks and calling the matching
   * callback function.
   *
   * @param cq {@link CallbackQuery} to be handled
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  private void handleCallbackQuery(CallbackQuery cq) {
    callbackHandlers.stream()
        .sorted(comparator)
        .filter(s -> cq.getData().equals(s.getIdentifier()) || s.getIdentifier().equals("*"))
        .findFirst()
        .ifPresent(s -> s.getCallback().accept(cq));
  }

  /**
   * Handles message updates, filtering the registered callbacks and calling the matching callback
   * function.
   *
   * @param message to be handled
   * @author Kamuri Amorim
   * @version 0.0.1
   * @since 0.0.1
   */
  private void handleMessage(Message message) {
    messageHandlers.stream()
        .sorted(comparator)
        .filter(
            s -> message.getText().startsWith(s.getIdentifier()) || s.getIdentifier().equals("*"))
        .findFirst()
        .ifPresent(s -> s.getCallback().accept(message));
  }

  @Override
  public void handle(Update update) {
    update
        .getResult()
        .forEach(
            result -> {
              Optional.ofNullable(result.getCallbackQuery())
                  .ifPresent(
                      cq ->
                          CompletableFuture.supplyAsync(
                                  () -> {
                                    handleCallbackQuery(cq);
                                    return null;
                                  })
                              .thenAccept(task -> runningTasks.remove(task)));

              Optional.ofNullable(result.getMessage())
                  .ifPresent(
                      msg ->
                          CompletableFuture.supplyAsync(
                                  () -> {
                                    handleMessage(msg);
                                    return null;
                                  })
                              .thenAccept(task -> runningTasks.remove(task)));
            });
  }

  @Override
  public void registerCallbackHandler(String expectedData, Consumer<CallbackQuery> callback) {
    if (callbackHandlers.stream().anyMatch(s -> s.getIdentifier().equals(expectedData))) {
      System.err.println("Callback handler already exists!");
      return;
    }

    callbackHandlers.add(Handler.of(expectedData, callback));
  }

  @Override
  public void registerMessageHandler(Consumer<Message> callback) {
    registerMessageHandler("*", callback);
  }

  @Override
  public void registerMessageHandler(String pattern, Consumer<Message> callback) {
    if (messageHandlers.stream().anyMatch(s -> s.getIdentifier().equals(pattern))) {
      System.err.println("Message handler already exists!");
      return;
    }

    messageHandlers.add(Handler.of(pattern, callback));
  }
}
