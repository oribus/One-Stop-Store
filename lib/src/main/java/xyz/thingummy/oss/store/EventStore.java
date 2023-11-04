/*
 * Copyright (c) 2023, Jérôme ROBERT
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 *   this software and associated documentation files (the “Software”), to deal in the
 *   Software without restriction, including without limitation the rights to use, copy,
 *   modify, merge, publish, distribute, sublicense, and/or sell copies of the
 *   Software, and to permit persons to whom the Software is furnished to do so,
 *    subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 *    copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED “AS IS”, WITHOUT WARRANTY OF ANY KIND,
 *    EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
 *    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 *    NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT
 *    HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 *    WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 *    FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR
 *    OTHER DEALINGS IN THE SOFTWARE.
 *
 */

package xyz.thingummy.oss.store;

import xyz.thingummy.oss.model.DomainEvent;

import java.util.List;
import java.util.UUID;

/**
 * Interface for the event store.
 * Provides methods for persisting and retrieving domain events.
 */
public interface EventStore {
    /**
     * Appends a domain event to the store.
     *
     * @param event The domain event to append.
     */
    void append(DomainEvent event);

    /**
     * Retrieves all events for a given aggregate.
     *
     * @param aggregateUuid The UUID of the aggregate.
     * @return A list of domain events related to the aggregate.
     */
    List<DomainEvent> getEventsForAggregate(UUID aggregateUuid);

    /**
     * Retrieves the current juncture (or version) for an aggregate.
     *
     * @param aggregateUuid The UUID of the aggregate.
     * @return The current juncture of the aggregate.
     */
    long getCurrentJunctureForAggregate(UUID aggregateUuid);
}
