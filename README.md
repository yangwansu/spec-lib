# spec-libs

[![Kotlin](https://img.shields.io/badge/kotlin-1.3.40-blue.svg)](http://kotlinlang.org)
<!-- 
[![jcenter](https://api.bintray.com/packages/kittinunf/maven/Result/images/download.svg)](https://bintray.com/kittinunf/maven/Result/_latestVersion)
[![Build Status](https://travis-ci.org/kittinunf/Result.svg?branch=master)](https://travis-ci.org/kittinunf/Result)
-->
 

## Usage

import by Gradle 

TBD

## 구성

- Spec
- Fact
- Rule

## Spec 
specification pattern 을 구현한 라이브러리입니다.
논리 연산자를 통해 조합가능합니다.
- And 
- Or
- Not 

```kotlin
    val even = Spec.create<Int> { i -> i % 2 == 0 }
    val multipleOfThree = Spec.create<Int> { i -> i % 3 == 0 }

    assertFalse { 1 isSatisfyThat even }
    assertTrue { 1 isSatisfyThat !even }

    assertFalse(9 isSatisfyThat { even and gte(10) })
    assertTrue(10 isSatisfyThat { even and gte(10) })
    
    assertFalse { 1 isSatisfyThat { even and gte(10) and multipleOfThree } }
    assertTrue { 3 isSatisfyThat { even and gte(10) or multipleOfThree } }
    assertTrue { 3 isSatisfyThat { (even and gte(10)) or multipleOfThree } }

```


## Fact 

Rule 의 만족 하는 대상을 가르킵니다.


## Rule

Rule 은 Fact 를 판단하기 위한 Spec 의 구현체입니다.  

```kotlin

    val fact = factOf(
        "location" to "seoul",
        "date" to LocalDate.parse("2019-01-10"),
        "temperature" to 5.0
    )

    assertTrue { fact isSatisfyThat { ("location" eq "seoul") and ("temperature" eq 5.0) } }

```
