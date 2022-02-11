package com.api.parkingcontrol.documentation;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;

import com.api.parkingcontrol.enums.Author;

@Inherited
@Documented
public @interface Documentation {
	
	  String doc();

	  Author author();

	  String date() default "";

	  String link() default "";
	  
	  Request[] api() default {};
}
