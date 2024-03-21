package com.ssafy.ibalance.common;

import org.assertj.core.util.Lists;
import org.springframework.restdocs.constraints.Constraint;
import org.springframework.restdocs.snippet.Attributes;

import java.util.Collections;
import java.util.List;

public class DocumentFormatProvider {

    public static Attributes.Attribute required() {
        List<Constraint> constraints = Lists.newArrayList(new Constraint("javax.validation.constraints.NotNull", Collections.emptyMap()));
        return Attributes.key("validationConstraints").value(constraints);
    }
}
