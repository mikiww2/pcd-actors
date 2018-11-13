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

import it.unipd.math.pcd.actors.exceptions.NoSuchActorException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Implementation of AbsActorSystem.
 *
 * @author Miki Violetto
 * @version 1.0
 * @since 1.0
 */
public class ImplActorSystem extends AbsActorSystem {

    /**
     * Singleton.
    */
    private static ImplActorSystem instance;

    /**
     * Actor's tasks executor.
     */
    private final ExecutorService executor;

    /**
     * Constructor.
     */
    public ImplActorSystem() {
        super();
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        instance = this;
    }

    /**
     * Retrieves the instance of the ImplActorSystem like the Singleton pattern.
     *
     * @return instance The ImplActorSystem singleton instance.
     */
    public static ImplActorSystem getInstance() {
        if (instance == null) {
            instance = new ImplActorSystem();
        }
        return instance;
    }

    /**
     * Creates an Actor reference.
     *
     * @param mode from {@code ActorMode}.
     * @return Reference to an Actor.
     * @throws IllegalArgumentException If the {@code mode} isn't LOCAL
     */
    @Override
    protected ActorRef createActorReference(ActorMode mode) throws IllegalArgumentException {
        if (mode != ActorMode.LOCAL)
            throw new IllegalArgumentException();
        else
            return new ImplActorRef();
    }

    /**
     * Stop the actor and then remove from the map.
     *
     * @param actor The actor to be stopped
     * @throws NoSuchActorException if the Actor doesn't exist
     */
    @Override
    public synchronized void stop(ActorRef<?> actor) throws NoSuchActorException{
        if(actors.containsKey(actor)) {
            ((AbsActor) actors.get(actor)).stop();
            actors.remove(actor);
        }
        else
            throw new NoSuchActorException();
    }

    /**
     * Stops all Actors of the ActorSystem and clear the map (remove all the Actors).
     */
    @Override
    public void stop() {
        executor.shutdown();
        actors.clear();
    }

    /**
     * Submits an ActorRunnable to the executor service.
     *
     * @param runnable ActorRunnable task.
     */
    public void addActorRunnable(Runnable runnable) {
        executor.execute(runnable);
    }
}