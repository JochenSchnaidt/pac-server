package com.prodyna.pac.monitoring;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

@ManagedResource
public class StatisticData {

    private final static long ZERO = 0L;

    private final String identifier;
    private AtomicLong counter = new AtomicLong(ZERO);
    private AtomicLong minDurationInMs = new AtomicLong(Long.MAX_VALUE);
    private AtomicLong maxDurationInMs = new AtomicLong(ZERO);
    private AtomicLong durationAllRequests = new AtomicLong(ZERO);
    private AtomicLong averageDurationInMs = new AtomicLong(ZERO);

    public StatisticData(String identifier) {
        super();
        this.identifier = identifier;

    }

    public synchronized void updateValues(long duration) {

        counter.incrementAndGet();

        durationAllRequests.addAndGet(duration);

        if (minDurationInMs.get() > duration) {
            minDurationInMs.set(duration);
        }

        if (maxDurationInMs.get() < duration) {
            maxDurationInMs.set(duration);
        }

        averageDurationInMs.set(durationAllRequests.get() / counter.get());
    }

    @ManagedAttribute(description = "The number of function calls")
    public long getCounter() {
        return counter.get();
    }

    @ManagedAttribute(description = "The duration of the fastest call in ms")
    public long getMinDurationInMs() {
        return minDurationInMs.get();
    }

    @ManagedAttribute(description = "The duration of the slowest call in ms")
    public long getMaxDurationInMs() {
        return maxDurationInMs.get();
    }

    @ManagedAttribute(description = "The duration of all calls in ms")
    public long getDurationAllRequests() {
        return durationAllRequests.get();
    }

    @ManagedAttribute(description = "The average duration of all calls in ms")
    public long getAverageDurationInMs() {
        return averageDurationInMs.get();
    }

    @ManagedOperation(description = "Resets the statistic data")
    public synchronized void reset() {
        counter.set(ZERO);
        minDurationInMs.set(ZERO);
        maxDurationInMs.set(ZERO);
        durationAllRequests.set(ZERO);
        averageDurationInMs.set(ZERO);
    }

    @Override
    public String toString() {
        return "StatisticData [identifier=" + identifier + ", counter=" + counter + ", minDurationInMs=" + minDurationInMs + ", maxDurationInMs=" + maxDurationInMs + ", durationAllRequests="
                + durationAllRequests + ", averageDurationInMs=" + averageDurationInMs + "]";
    }

}
