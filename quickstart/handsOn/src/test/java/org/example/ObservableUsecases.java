package org.example;
import static org.junit.Assert.assertTrue;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.*;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ObservableUsecases {



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

      @Test
      public void testObservableInterval() throws InterruptedException {
        Observable.timer(1, TimeUnit.SECONDS).subscribe(x->System.out.println("Called"));
//        Thread.sleep(30000);
      }

      @Test
      public void testObservable() {
//        Observable.timer(1, TimeUnit.SECONDS, new Scheduler() {
//            @Override
//            public @NonNull Worker createWorker() {
//                return null;
//            }
//        })
//        Observable.zip(Observable.just(1,2,3),Observable.just(1,2,3));
//        Observable.zip().subscribe();
//        Observable.fromIterable()

          Observable<Object> empty = Observable.empty();
          empty.subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Done!"));

      }

      @Test
      public void testFromMaybeSource() {
        Observable.fromMaybe(Maybe.empty()).subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Done"));
        Observable.fromMaybe(Maybe.just(1)).subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Done"));
//        Disposable done = Observable.zip(Observable.just(5,6,7), Observable.just(4, 5, 6)).subscribe(System.out::println, Throwable::printStackTrace, () -> System.out.println("Done"));

//       Observable.zip(new ).subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Done"));
    }

      public class SomeMaybeSource implements MaybeSource<Integer> {

          @Override
          public void subscribe(@NonNull MaybeObserver<? super Integer> observer) {
              observer.onSuccess(1);
          }
      }

//      public class ExampleObservableSource<Integer> implements ObservableSource<Integer>, @NonNull Function<Object[], R> {
//
//          @Override
//          public void subscribe(@NonNull Observer<? super Integer> observer) {
//              observer.onNext(1);
//              observer.onNext(2);
//              observer.onNext(3);
//          }
//
//          @Override
//          public R apply(Object[] objects) throws Throwable {
//              return null;
//          }
//      }

      @Test
      public void onTestNever() {
        Observable.never().subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("Am done"));

      }

      @Test
      public void testDeferUnderStandProblem() {
          int start = 0;
          int count = 3;
          Observable<Integer> source = Observable.range(start, count);
          source.subscribe(i -> System.out.println("Observer 1: " + i));
          //modify count
          count = 5;
          source.subscribe(i -> System.out.println("Observer 2: " + i));
    }

    @Test
    public void testDeferUnderStandDefer() {
        int start = 0;
        final int count = 3;
        Observable<Integer> source = Observable.defer(()->Observable.range(start, count));
        source.subscribe(i -> System.out.println("Observer 1: " + i));
        //modify count
        source.subscribe(i -> System.out.println("Observer 2: " + i));
    }

    static int count = 5;
    @Test
    public void testDefer() {
        int start = 0;
        Observable<Integer> source = Observable.defer(() ->
                Observable.range(start, count));
        source.subscribe(i -> System.out.println("Observer 1: " + i));
        count = 9;
        source.subscribe(i -> System.out.println("Observer 2: " + i));
    }

    @Test
    public void testFromCallable() {
        Observable.fromCallable(()->2).map(x->x*x).subscribe(System.out::println,Throwable::printStackTrace,()->System.out.println("This is complete"));
    }

    @Test
    public void testFromCallable2() {
        Observable.fromCallable(()->{
            List<Integer> integerList = new LinkedList<Integer>();
            for(int i = 0; i < 10; i ++)
                integerList.add(i);

            return integerList;
        });
    }

   @Test
   public void testFromCallable3() {


   }


}
