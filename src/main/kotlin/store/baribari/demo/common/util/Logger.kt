package store.baribari.demo.common.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory

inline val <reified T> T.log: Logger
    get() = LoggerFactory.getLogger(T::class.java)
