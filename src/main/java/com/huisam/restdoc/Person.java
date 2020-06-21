package com.huisam.restdoc;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Builder
public class Person {
    private final String name;
    private final int age;
}
