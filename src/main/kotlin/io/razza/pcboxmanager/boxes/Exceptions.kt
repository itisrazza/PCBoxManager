package io.razza.pcboxmanager.boxes

class BoxLockedException(
    val boxLock: BoxLock
) : RuntimeException("The box '${boxLock.box.name}' is locked by PID ${boxLock.pid} (reason: ${boxLock.reason}).") {
    val box = boxLock.box
}