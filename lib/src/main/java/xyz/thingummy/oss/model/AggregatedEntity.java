/*
 * Copyright (c) 2023, Jérôme ROBERT
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package xyz.thingummy.oss.model;

/**
 * Base interface for entities which are part of an aggregate (the root of this aggregate being an {@link AggregateRoot}).
 * @param <E> The type of the entity
 * @param <I> The type of the entity's identifier'
 * @param <R> The type of the entity's aggregate root'
 */
public interface AggregatedEntity<E extends AggregatedEntity<E, I, R>, I extends LocalId<E, ?>, R extends AggregateRoot<?, ?>> extends BaseEntity<E, I> {
    /**
     * @return The root of the aggregate this entity belongs to.
     */
    R getAggregateRoot();

    /**
     * @return The identifier of the entity.
     */
    I getId();
}