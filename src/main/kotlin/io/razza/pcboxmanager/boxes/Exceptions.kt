package io.razza.pcboxmanager.boxes

class BoxLockedException(
    val boxLock: BoxLock
) : RuntimeException() {
    override val message: String?
        get() = "The box '${boxLock.box.name}' is locked by PID ${boxLock.pid} (reason: ${boxLock.reason})."
}