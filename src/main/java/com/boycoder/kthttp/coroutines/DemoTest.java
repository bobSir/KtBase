package com.boycoder.kthttp.coroutines;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import org.jetbrains.annotations.NotNull;

/**
 * created by cly on 2022/9/15
 */
public class DemoTest {
    public static void main(String[] args) {

        System.out.println("start");

        DemoKt.getUserInfo(new Continuation<String>() {
            @NotNull
            @Override
            public CoroutineContext getContext() {
                return EmptyCoroutineContext.INSTANCE;
            }

            @Override
            public void resumeWith(@NotNull Object o) {
                System.out.println("111");
                System.out.println(o);
            }
        });
    }
}
