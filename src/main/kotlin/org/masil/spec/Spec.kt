package org.masil.spec

interface Spec<T> {
    fun isSatisfy(candidate: T): Boolean

    companion object {
        fun <T> create(predicate: (T) -> Boolean): Spec<T> {
            return object : Spec<T> {
                override fun isSatisfy(candidate: T): Boolean {
                    return predicate(candidate)
                }
            }
        }
    }
}

class AndSpec<T>(private val left: Spec<T>, private val right: Spec<T>) : Spec<T> {
    override fun isSatisfy(candidate: T): Boolean {
        return left.isSatisfy(candidate) && right.isSatisfy(candidate)
    }
}

class OrSpec<T>(private val left: Spec<T>, private val right: Spec<T>) : Spec<T> {
    override fun isSatisfy(candidate: T): Boolean {
        return left.isSatisfy(candidate) || right.isSatisfy(candidate)
    }
}

class NotSpec<T>(private val spec: Spec<T>) : Spec<T> {
    override fun isSatisfy(candidate: T): Boolean {
        return !spec.isSatisfy(candidate)
    }
}
