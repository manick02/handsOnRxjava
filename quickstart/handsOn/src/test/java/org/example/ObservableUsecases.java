package org.example;
import static org.junit.Assert.assertTrue;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collector;

public class AppTest1 {

    @Test
    public void createAnObservable(){
        Observable<String> observable1 = Observable.just("list1", "list2", "list3");
        Disposable subscribe = observable1.subscribe(x -> System.out.println(x));
        subscribe.dispose();
        System.out.println(subscribe.isDisposed());
    }

    @Test
    public void createAnObservableWithCom() {
        Observable<String> objectObservable = Observable.create(emitter -> {
            try {
                emitter.onNext("First1");
                emitter.onNext("First2");
                emitter.onNext("First3");
                emitter.onComplete();
            } catch (Throwable throwable) {
                emitter.onError(throwable);
            }
        });
        Observable<Integer> map = objectObservable.map(String::length);
        map.subscribe(x->System.out.println(x));
        objectObservable.subscribe(y->System.out.println(y));
    }
    @Test
    public void createAnObservableWithIterator() {
        List<Integer> integerList = new LinkedList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        Observable<Integer> integerObservable = Observable.fromIterable(integerList);
        integerObservable.map(y -> y.toString());
        integerObservable.subscribe(x -> System.out.println(x));
        Observable<Integer> integerObservable1 = integerObservable.cacheWithInitialCapacity(1);
    }

    @Test
    public void testObserverInterface() {
        List<Integer> integerList = new LinkedList<>();
        integerList.add(1);
        integerList.add(2);
        integerList.add(3);
        Observer<Integer> observer = new Observer<Integer>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("Subscribed");
            }

            @Override
            public void onNext(@NonNull Integer integer) {
              System.out.println(integer);
            }

            @Override
            public void onError(@NonNull Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onComplete() {
               System.out.println("Completed!");
            }
        };
        Observable.fromIterable(integerList).subscribe(observer);

       }

      @Test
      public void testObservableRange() {
          Observable.range(1,3).subscribe(x->{System.out.println(x);});
      }

}
