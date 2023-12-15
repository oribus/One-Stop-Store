/*
 *
 * The MIT License (MIT)
 *
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

package xyz.thingummy.oss.commons.validation;

import xyz.thingummy.oss.model.specification.SimpleTestSpecifications;
import xyz.thingummy.oss.model.specification.SpecificationCombinee;
import xyz.thingummy.oss.model.specification.Specifications;

import static xyz.thingummy.oss.commons.validation.ValidationOperations.soit;

public class ValidationCombineeTest implements ValidationBaseTest {

    private static <T> ValidationCombinee<T> identite(final Validation<T> validation) {
        return new ValidationCombinee<>(validation, soit(Specifications.toujoursVrai),
                (b1, b2) -> b1, SpecificationCombinee.ShortCut.UNARY, null, null);
    }

    @Override
    public Validation<String> commenceParA() {
        return identite(soit(SimpleTestSpecifications.commenceParA()));
    }


    @Override
    public Validation<String> finiParA() {
        return identite(soit(SimpleTestSpecifications.finiParA()));
    }

    @Override
    public Validation<Integer> supperieurA4() {
        return identite(soit(SimpleTestSpecifications.supperieurA4()));
    }


}