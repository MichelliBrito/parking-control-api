package com.api.parkingcontrol.documentation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;

import com.api.parkingcontrol.enums.RequestMethod;

@Inherited
@Documented
public @interface Request {

	  RequestMethod method();

	  String url() default "";
}
