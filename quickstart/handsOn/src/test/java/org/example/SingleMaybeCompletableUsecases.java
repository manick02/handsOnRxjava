package org.example;

import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import org.junit.Test;

public class SingleMaybeCompletableUsecases {
    @Test
    public void testSingle(){
        Single<Integer> just = Single.just(100);
        just.subscribe(System.out::println,Throwable::printStackTrace);
    }

    @Test
    public void testMaybe(){
        Maybe.just(1).subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("I am done"));
    }
    @Test
    public void testMaybeEmpty(){
        Maybe.empty().subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("I am done"));
    }


}
