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
 * Interface of an Actor's mailbox.
 *
 * @author Miki Violetto
 * @version 1.0
 * @since 1.0
 * @param <T> Message subtype.
 */
public interface Mailbox<T extends Message> {

    /**
     * Push a MailMessage in the mailbox.
     *
     * @param message The MailMessage.
     * @throws ClosedMailboxException If the mailbox is close.
     */
    void push(MailMessage<ActorRef<T>,T> message);

    /**
     * Return the oldest MailMessage of the mailbox and deletes it from the mailbox.
     *
     * @return The oldest MailMessage.
     * @throws EmptyMailboxException If the mailbox is empty.
     */
    MailMessage<ActorRef<T>,T> pop();

    /**
    * Checks if the mailbox have MailMessage.
    *
    * @return true if the mailbox is empty, false otherwise
    */
    boolean isEmpty();

    /**
     * Close the mailbox, preventing from accepting new MailMessages.
     */
    void setClosed();

    /**
     * Checks if mailbox isn't close.
     *
     * @return true if the mailbox can receive MailMessages, false if it's close
     */
    boolean isOpen();

}