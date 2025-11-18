package com.github.f1monkey.translit;

import org.openjdk.jmh.annotations.*;

import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Warmup(iterations = 3)
@Measurement(iterations = 5)
@Fork(2)
@State(Scope.Thread)
public class TranslitBenchmark {
    @Benchmark
    public String benchmarkSingleWord() {
        return TranslitProcessor.normalize("экстеншен");
    }

    @Benchmark
    public String benchmarkEnglish() {
        return TranslitProcessor.normalize("extension");
    }
}
