/**
 * The MIT License (MIT)
 * <p/>
 * Copyright (c) 2015 Riccardo Cardin
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
 * @author Riccardo Cardin
 * @version 1.0
 * @since 1.0
 */
package it.unipd.math.pcd.actors;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import it.unipd.math.pcd.actors.exceptions.EmptyMailboxException;
import it.unipd.math.pcd.actors.exceptions.UnsupportedMessageException;

/**
 * Defines common properties of all actors.
 *
 * @author Riccardo Cardin
 * @author Miki Violetto
 * @version 1.0
 * @since 1.0
 */
public abstract class AbsActor<T extends Message> implements Actor<T> {

    /**
     * Self-reference of the actor.
     */
    protected ActorRef<T> self;

    /**
     * Sender of the current message.
     */
    protected ActorRef<T> sender;

    /**
     * Actor's Mailbox.
     */
    protected Mailbox<T> mailbox;

    /**
     * Lock message's processing process.
     */
    protected final Lock lock = new ReentrantLock();

    /**
     * Condition for release the lock.
     */
    protected final Condition emptyMailbox = lock.newCondition();

    /**
     * Executor task.
     */
    private final Runnable actorRunnable;

    /**
     * Constructor
     */
    protected AbsActor() {
        this.mailbox = new ImplMailbox<>();
        this.actorRunnable = new ActorRunnable();
        ImplActorSystem.getInstance().addActorRunnable(this.actorRunnable);
    }

    /**
     * Sets the self-referece.
     *
     * @param self The reference to itself
     * @return The actor.
     */
    protected final Actor<T> setSelf(ActorRef<T> self) {
        this.self = self;
        return this;
    }

    /**
     * Stops actor by closing it's mailbox.
     */
    final void stop() {
        mailbox.setClosed();
    }

    /**
     * Tries to push a message and awake execution if successful.
     *
     * @param message The MailMessage to insert in the Actor's mailbox.
     * @throws UnsupportedMessageException if message is rejected {@code message}.
     */
    final void pushMailMessage(MailMessage<ActorRef<T>,T> message) throws UnsupportedMessageException {
        mailbox.push(message);
        lock.lock();
        try {
            emptyMailbox.signalAll();
        } finally {
            lock.unlock();
        }
    }

    /**
     * Defines the runnable of the AbsActor.
     *
     * @author Miki Violetto
     * @version 1.0
     * @since 1.0
     */
    public class ActorRunnable implements Runnable {

        /**
         * Executes head message.
         */
        private void executeMessage() {
            MailMessage<ActorRef<T>,T> message = mailbox.pop();
            sender = message.getSender();
            receive(message.getMessage());
        }

        /**
         * When an object implementing interface <code>Runnable</code> is used
         * to create a thread, starting the thread causes the object's
         * <code>run</code> method to be called in that separately executing
         * thread.
         * <p>
         * The general contract of the method <code>run</code> is that it may
         * take any action whatsoever.
         *
         * @see Thread#run()
         */
        @Override
        public void run() {
            try {
                while (mailbox.isOpen()) {
                    lock.lock();
                    while (mailbox.isEmpty()) {
                        emptyMailbox.await();
                    }
                    lock.unlock();
                    executeMessage();
                }
            } catch (InterruptedException e) {
            // TODO Manage interruption
            } finally {
                while (!mailbox.isEmpty()) {
                    executeMessage();
                }
            }
        }
    }
}