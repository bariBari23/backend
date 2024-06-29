package store.baribari.demo.common.annotation

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.springframework.stereotype.Component
import store.baribari.demo.common.util.log

@Target(AnnotationTarget.TYPE, AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Timer

@Aspect
@Component
class TimerAop {

    @Around("@annotation(store.baribari.demo.common.annotation.Timer)")
    @Throws(Throwable::class)
    fun around(joinPoint: ProceedingJoinPoint) { // 메서드 실행시 걸린시간 측정
        log.info("function ${joinPoint.signature.name} start")
        val start = System.currentTimeMillis()
        val result = joinPoint.proceed() // 메서드가 실행되는 부분
        val executionTime = System.currentTimeMillis() - start
        log.info("function ${joinPoint.signature.name} end in $executionTime ms")
    }
}
