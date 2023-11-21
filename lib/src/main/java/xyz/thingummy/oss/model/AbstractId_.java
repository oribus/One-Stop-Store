/*
 * The MIT License (MIT)
 *
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
/*package xyz.thingummy.oss.model;

import lombok.*;
import lombok.experimental.NonFinal;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;
import java.util.UUID;


@NonFinal
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class AbstractId_<T extends BaseEntity<T, ? extends Id<T>>> implements ValueObject<Id<T>> {
    @NonNull*/
/*
 *  The id of the entity as a UUID
 */
//private UUID id;
/*
 * The juncture of the entity as an object of type Long. Used as an "Immutable cached" for the value obtained
 * from the junctureSupplier.
 * The juncture complements the immutable id of the entity with an indication of a point in time.
 */
//private Long juncture;


/**
 * Creates an instance of the Id class from a String representation of the id formed of a UUID and an optional juncture.
 *
 * @param uuid  the String representation of the id
 * @param clazz the class of the entity
 * @param <U>   actual type of the Id
 * @return an instance of the Id class
 * @throws InstantiationException if the actual class of the Id entity does not meet the requirements
 * <p>
 * Returns whether the id is a plain ID or an ID with a juncture
 * @return true if the id is a plain ID, false otherwise
 * <p>
 * Returns whether the id is an ID with a juncture or a plain ID
 * @return true if the id is an ID with a juncture, false otherwise
 * <p>
 * Returns the String representation of the id formed of a UUID and an optional juncture separated by a colon
 * @return the String representation of the id
 * <p>
 * Returns whether the id is a plain ID or an ID with a juncture
 * @return true if the id is a plain ID, false otherwise
 * <p>
 * Returns whether the id is an ID with a juncture or a plain ID
 * @return true if the id is an ID with a juncture, false otherwise
 * <p>
 * Returns the String representation of the id formed of a UUID and an optional juncture separated by a colon
 * @return the String representation of the id
 */
  /*  static protected <U extends AbstractId_<?>> @NonNull U of(@NonNull String uuid, @NonNull Class<U> clazz) throws InstantiationException {
        final String[] crumbs = uuid.split(":");
        return of(UUID.fromString(uuid), Long.parseLong(crumbs[1]), clazz);
    }

    static protected <U extends AbstractId_<?>> @NonNull U of(@NonNull UUID uuid, Long juncture, @NonNull Class<U> clazz) throws InstantiationException {
        try {
            return clazz.getDeclaredConstructor(UUID.class, Long.class).newInstance(uuid, juncture);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw new InstantiationException(e.fillInStackTrace().toString());
        }
    }*/

/**
 * Returns whether the id is a plain ID or an ID with a juncture
 *
 * @return true if the id is a plain ID, false otherwise
 */
    /*public final boolean isPlainId() {
        return juncture == null;
    }*/

/**
 * Returns whether the id is an ID with a juncture or a plain ID
 *
 * @return true if the id is an ID with a juncture, false otherwise
 */
    /*public final boolean isWithJuncture() {
        return !isPlainId();
    }*/

/**
 * Returns the String representation of the id formed of a UUID and an optional juncture separated by a colon
 *
 * @return the String representation of the id
 */
    /*public final String asString() {
        return (isPlainId()) ? id.toString() : "%s:%d".formatted(id.toString(), juncture);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractId_<?> id1 = (AbstractId_<?>) o;
        if (isPlainId() != id1.isPlainId()) return false;
        return Objects.equals(id, id1.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }

    @Override
    final public String toString() {
        return "Id{ " + getClass().getSimpleName() + " " +
                "id=" + id +
                ", juncture=" + juncture +
                '}';
    }
}*/
