package ru.hiruma

import org.junit.Test
import kotlin.test.assertEquals

class HelloTest {
    @Test
    fun test(){
        assertEquals("a", OuterClass.Test2.A.v)
    }
}
