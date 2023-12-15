/*
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

package xyz.thingummy.oss.model.specification;

public interface Specifications {
    Specifications0<Object> toujoursFaux = () -> false;
    Specifications0<Object> toujoursVrai = () -> true;

    static <T> Specifications0<T> toujoursFaux() {
        return () -> false;
    }

    static <T> Specifications0<T> toujoursVrai() {
        return () -> true;
    }


//    interface Specifications2<T1, T2> {
//        boolean sontSatisfaitsPar(T1 t1, T2 t2);
//
//        boolean sontSatisfaitsPar(T1 t1, T2 t2, CollecteurNotifications c);
//
//        Specifications0 differer(T1 t1, T2 t2);
//
//        Specifications0 differer(T1 t1, T2 t2, CollecteurNotifications c);
//
//    }
//
//
//    record Specifications3<T1, T2, T3>(Specification<T1> s1, Specification<T2> s2, Specification<T3> s3) {
//    }
//
//
//    record Specifications4<T1, T2, T3, T4>(Specification<T1> s1, Specification<T2> s2, Specification<T3> s3,
//                                           Specification<T4> s4) {
//    }
//
//
//    record Specifications5<T1, T2, T3, T4, T5>(Specification<T1> s1, Specification<T2> s2, Specification<T3> s3,
//                                               Specification<T4> s4, Specification<T5> s5) {
//    }
//
//
//    record Specifications6<T1, T2, T3, T4, T5, T6>(Specification<T1> s1, Specification<T2> s2, Specification<T3> s3,
//                                                   Specification<T4> s4, Specification<T5> s5, Specification<T6> s6) {
//    }
//
//
//    record Specifications7<T1, T2, T3, T4, T5, T6, T7>(Specification<T1> s1, Specification<T2> s2, Specification<T3> s3,
//                                                       Specification<T4> s4, Specification<T5> s5, Specification<T6> s6,
//                                                       Specification<T7> s7) {
//    }
//
//
//    record Specifications8<T1, T2, T3, T4, T5, T6, T7, T8>(Specification<T1> s1, Specification<T2> s2,
//                                                           Specification<T3> s3, Specification<T4> s4,
//                                                           Specification<T5> s5, Specification<T6> s6,
//                                                           Specification<T7> s7, Specification<T8> s8) {
//    }
//
//
//    record Specifications9<T1, T2, T3, T4, T5, T6, T7, T8, T9>(Specification<T1> s1, Specification<T2> s2,
//                                                               Specification<T3> s3, Specification<T4> s4,
//                                                               Specification<T5> s5, Specification<T6> s6,
//                                                               Specification<T7> s7, Specification<T8> s8,
//                                                               Specification<T9> s9) {
//    }


}
