package com.api.parkingcontrol;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
@Lazy
public class LazyBean {

    public LazyBean() {
        System.out.println("LazyBean started!!!");
    }

}
