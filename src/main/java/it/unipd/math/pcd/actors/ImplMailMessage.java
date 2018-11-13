/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2016 Andrea Faggin
 * <p/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * <p/>
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * <p/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 * <p/>
 *
 * @author Miki Violetto
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

/**
 * Implements message pair.
 *
 * @author Miki Violetto
 * @version 1.0
 * @since 1.0
 * @param <T> Type of the sender.
 * @param <U> Type of the message.
 */
public final class ImplMailMessage<T,U> implements MailMessage<T, U> {

    private final T sender;

    private final U message;

    /**
     * @param sender The sender.
     * @param message The message.
     */
    public ImplMailMessage(T sender, U message) {
        this.sender = sender;
        this.message = message;
    }

    @Override
    public T getSender() {
        return sender;
    }

    @Override
    public U getMessage() {
        return message;
    }
} 
