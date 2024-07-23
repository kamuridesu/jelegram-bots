package com.kamuri.telegram.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kamuri.telegram.model.Update;
import com.kamuri.telegram.model.message.Message;
import com.kamuri.telegram.model.update.CallbackQuery;
import com.kamuri.telegram.model.update.Result;
import com.kamuri.telegram.services.impl.UpdateHandler;

import lombok.RequiredArgsConstructor;

@ExtendWith(MockitoExtension.class)
@RequiredArgsConstructor
public class UpdateHandlerTest {

    @Mock
    private Consumer<CallbackQuery> callbackConsumer;

    @Mock
    private Consumer<Message> messageConsumer;

    @InjectMocks
    private UpdateHandler updateHandler;

    @Test
    void shouldRegisterCallbackHandler() {
        String expectedData = "test";
        updateHandler.registerCallbackHandler(expectedData, callbackConsumer);

        assertEquals(1, updateHandler.getCallbackHandlers().size());

        var h = updateHandler.getCallbackHandlers().get(0);
        assertEquals(expectedData, h.getIdentifier());
        assertEquals(callbackConsumer, h.getCallback());
    }

    @Test
    void shouldNotRegisterDuplicatedCallbackHandler() {
        String expectedData = "test";
        updateHandler.registerCallbackHandler(expectedData, callbackConsumer);
        updateHandler.registerCallbackHandler(expectedData, callbackConsumer);

        assertEquals(1, updateHandler.getCallbackHandlers().size());

        var h = updateHandler.getCallbackHandlers().get(0);
        assertEquals(expectedData, h.getIdentifier());
        assertEquals(callbackConsumer, h.getCallback());
    }

    @Test
    void shouldRegisterMessageHandler() {
        String expectedData = "test";
        updateHandler.registerMessageHandler(expectedData, messageConsumer);

        assertEquals(1, updateHandler.getMessageHandlers().size());

        var h = updateHandler.getMessageHandlers().get(0);
        assertEquals(expectedData, h.getIdentifier());
        assertEquals(messageConsumer, h.getCallback());
    }

    @Test
    void shouldRegisterGenericMessageHandler() {
        updateHandler.registerMessageHandler(messageConsumer);

        assertEquals(1, updateHandler.getMessageHandlers().size());

        var h = updateHandler.getMessageHandlers().get(0);
        assertEquals("*", h.getIdentifier());
        assertEquals(messageConsumer, h.getCallback());
    }

    @Test
    void shouldNotRegisterDuplicatedMessageHandler() {
        updateHandler.registerMessageHandler(messageConsumer);
        updateHandler.registerMessageHandler(messageConsumer);

        assertEquals(1, updateHandler.getMessageHandlers().size());

        var h = updateHandler.getMessageHandlers().get(0);
        assertEquals("*", h.getIdentifier());
        assertEquals(messageConsumer, h.getCallback());
    }

    @Test
    void checkCallbackHandler() throws InterruptedException {
        updateHandler.registerCallbackHandler("test", callbackConsumer);
        var cq = new CallbackQuery("0", null, null, "test");
        var update = new Update(true, List.of(new Result(123, null, cq)));
        updateHandler.handle(update);

        synchronized (callbackConsumer) {
            callbackConsumer.wait(200);
        }
        verify(callbackConsumer, times(1)).accept(cq);
    }

    @Test
    void checkMessageHandler() throws InterruptedException {
        updateHandler.registerMessageHandler("test", messageConsumer);
        var message = new Message(1, null, null, 1, 1, "test");
        var update = new Update(true, List.of(new Result(123, message, null)));
        updateHandler.handle(update);

        synchronized (messageConsumer) {
            messageConsumer.wait(200);
        }
        verify(messageConsumer, times(1)).accept(message);
    }

    @Test
    void checkGenericMessageHandler() throws InterruptedException {
        updateHandler.registerMessageHandler("*", messageConsumer);
        var message = new Message(1, null, null, 1, 1, "test");
        var update = new Update(true, List.of(new Result(123, message, null)));
        updateHandler.handle(update);

        synchronized (messageConsumer) {
            messageConsumer.wait(200);
        }
        verify(messageConsumer, times(1)).accept(message);
    }

    @Test
    void checkNoMessageHandler() throws InterruptedException {
        updateHandler.registerMessageHandler("ababa", messageConsumer);
        var message = new Message(1, null, null, 1, 1, "test");
        var update = new Update(true, List.of(new Result(123, message, null)));
        updateHandler.handle(update);

        synchronized (messageConsumer) {
            messageConsumer.wait(200);
        }
        verify(messageConsumer, times(0)).accept(message);
    }

    @Test
    void checkGenericCallbackHandler() throws InterruptedException {
        updateHandler.registerCallbackHandler("*", callbackConsumer);
        var cq = new CallbackQuery("0", null, null, "test");
        var update = new Update(true, List.of(new Result(123, null, cq)));
        updateHandler.handle(update);

        synchronized (messageConsumer) {
            messageConsumer.wait(200);
        }
        verify(callbackConsumer, times(1)).accept(cq);
    }

    @Test
    void checkEmptyUpdate() {
        var update = new Update(true, List.of());
        updateHandler.handle(update);

        verify(callbackConsumer, times(0)).accept(any());;
        verify(messageConsumer, times(0)).accept(any());;
    }

}
