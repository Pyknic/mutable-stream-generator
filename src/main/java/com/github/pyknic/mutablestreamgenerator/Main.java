package com.github.pyknic.mutablestreamgenerator;

import com.speedment.common.mutablestream.MutableIntStream;
import com.speedment.common.mutablestream.action.FlatMapIntAction;
import com.speedment.common.mutablestream.action.FlatMapToIntAction;
import com.speedment.common.mutablestream.action.IntFilterAction;
import com.speedment.common.mutablestream.action.MapIntAction;
import com.speedment.common.mutablestream.action.MapIntToIntAction;
import com.speedment.common.mutablestream.action.MapToIntAction;
import com.speedment.common.mutablestream.internal.action.FlatMapIntActionImpl;
import com.speedment.common.mutablestream.internal.action.FlatMapToIntActionImpl;
import com.speedment.common.mutablestream.internal.action.IntFilterActionImpl;
import com.speedment.common.mutablestream.internal.action.MapIntActionImpl;
import com.speedment.common.mutablestream.internal.action.MapIntToIntActionImpl;
import com.speedment.common.mutablestream.internal.action.MapToIntActionImpl;
import com.speedment.common.mutablestream.internal.terminate.AllMatchIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.AnyMatchIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.CollectIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.FindAnyIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.FindFirstIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.ForEachIntOrderedTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.ForEachIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.IntIteratorTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.IntSpliteratorTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.IntSummaryStatisticsTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.MaxIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.MinIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.NoneMatchIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.ReduceIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.SumIntTerminatorImpl;
import com.speedment.common.mutablestream.internal.terminate.ToIntArrayTerminatorImpl;
import com.speedment.common.mutablestream.terminate.AllMatchIntTerminator;
import com.speedment.common.mutablestream.terminate.AnyMatchIntTerminator;
import com.speedment.common.mutablestream.terminate.CollectIntTerminator;
import com.speedment.common.mutablestream.terminate.FindAnyIntTerminator;
import com.speedment.common.mutablestream.terminate.FindFirstIntTerminator;
import com.speedment.common.mutablestream.terminate.ForEachIntOrderedTerminator;
import com.speedment.common.mutablestream.terminate.ForEachIntTerminator;
import com.speedment.common.mutablestream.terminate.IntIteratorTerminator;
import com.speedment.common.mutablestream.terminate.IntSpliteratorTerminator;
import com.speedment.common.mutablestream.terminate.IntSummaryStatisticsTerminator;
import com.speedment.common.mutablestream.terminate.MaxIntTerminator;
import com.speedment.common.mutablestream.terminate.MinIntTerminator;
import com.speedment.common.mutablestream.terminate.NoneMatchIntTerminator;
import com.speedment.common.mutablestream.terminate.ReduceIntTerminator;
import com.speedment.common.mutablestream.terminate.SumIntTerminator;
import com.speedment.common.mutablestream.terminate.ToIntArrayTerminator;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.OptionalDouble;
import java.util.OptionalInt;
import java.util.OptionalLong;
import java.util.function.DoubleConsumer;
import java.util.function.IntConsumer;
import java.util.function.LongConsumer;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;

/**
 *
 * @author Emil Forslund
 */
public final class Main {
    
    private final static Path SRC = Paths.get(
        System.getProperty("user.home"), 
        "Documents",
        "GitHub", 
        "mutable-stream", 
        "src", 
        "main", 
        "java"
    );
    
    private final static Class<?>[] PROTOTYPES = {
        
        // Intermediate Actions
        FlatMapIntAction.class,
        FlatMapToIntAction.class,
        IntFilterAction.class,
        MapIntAction.class,
        MapToIntAction.class,
        MapIntToIntAction.class,
        
        // Intermediate Action Implementations
        FlatMapIntActionImpl.class,
        FlatMapToIntActionImpl.class,
        IntFilterActionImpl.class,
        MapIntActionImpl.class,
        MapToIntActionImpl.class,
        MapIntToIntActionImpl.class,
        
        // Terminating Actions
        AllMatchIntTerminator.class,
        AnyMatchIntTerminator.class,
        CollectIntTerminator.class,
        FindAnyIntTerminator.class,
        FindFirstIntTerminator.class,
        ForEachIntTerminator.class,
        ForEachIntOrderedTerminator.class,
        IntIteratorTerminator.class,
        IntSpliteratorTerminator.class,
        IntSummaryStatisticsTerminator.class,
        MaxIntTerminator.class,
        MinIntTerminator.class,
        NoneMatchIntTerminator.class,
        ReduceIntTerminator.class,
        SumIntTerminator.class,
        ToIntArrayTerminator.class,
        
        // Terminating Action Implementations
        AllMatchIntTerminatorImpl.class,
        AnyMatchIntTerminatorImpl.class,
        CollectIntTerminatorImpl.class,
        FindAnyIntTerminatorImpl.class,
        FindFirstIntTerminatorImpl.class,
        ForEachIntTerminatorImpl.class,
        ForEachIntOrderedTerminatorImpl.class,
        IntIteratorTerminatorImpl.class,
        IntSpliteratorTerminatorImpl.class,
        IntSummaryStatisticsTerminatorImpl.class,
        MaxIntTerminatorImpl.class,
        MinIntTerminatorImpl.class,
        NoneMatchIntTerminatorImpl.class,
        ReduceIntTerminatorImpl.class,
        SumIntTerminatorImpl.class,
        ToIntArrayTerminatorImpl.class,
        
        // Stream Specialization
//        MutableIntStream.class
    };
    
    private enum Primitive {
        INT    (int.class,    Integer.class, IntStream.class,    IntConsumer.class,    OptionalInt.class,    "Int"),
        LONG   (long.class,   Long.class,    LongStream.class,   LongConsumer.class,   OptionalLong.class,   "Long"),
        DOUBLE (double.class, Double.class,  DoubleStream.class, DoubleConsumer.class, OptionalDouble.class, "Double");
        
        private final Class<?> primitive;
        private final Class<?> wrapper;
        private final Class<?> stream;
        private final Class<?> consumer;
        private final Class<?> optional;
        private final String name;

        private Primitive(
                Class<?> primitive,
                Class<?> wrapper, 
                Class<?> stream, 
                Class<?> consumer, 
                Class<?> optional,
                String name) {
            
            this.primitive = primitive;
            this.wrapper   = wrapper;
            this.stream    = stream;
            this.consumer  = consumer;
            this.optional  = optional;
            this.name      = name;
        }
    }
    
    private final static Primitive[] PRIMITIVES = {Primitive.LONG, Primitive.DOUBLE};
    
    public static void main(String... param) {
        System.out.println("Generating files...");
        
        long counter = 0;
        for (final Class<?> prototype : PROTOTYPES) {
            
            // Determine the Path to the source file.
            Path file = SRC;
            final String[] path = prototype.getName().split("\\.");
            path[path.length - 1] += ".java";
            for (final String name : path) {
                file = file.resolve(name);
            }
            
            // Make sure it exists.
            if (!Files.exists(file)) {
                throw new RuntimeException("Expected source file '" + file + "' to exist.");
            }
            
            // Read the entire content of the file into a string.
            final String original;
            try {
                original = new String(Files.readAllBytes(file), StandardCharsets.UTF_8);
            } catch (final IOException ex) {
                throw new RuntimeException("Error reading code from file '" + file + "'.", ex);
            }
            
            // Do the appropriate replacements.
            for (final Primitive primitive : PRIMITIVES) {
                final String result = original
                    .replace("interface", "$$_1")
                    .replace("internal", "$$_2")
                    .replace(Primitive.INT.stream.getSimpleName(),    primitive.stream.getSimpleName())
                    .replace(Primitive.INT.consumer.getSimpleName(),  primitive.consumer.getSimpleName())
                    .replace(Primitive.INT.optional.getSimpleName(),  primitive.optional.getSimpleName())
                    .replace(Primitive.INT.wrapper.getSimpleName(),   primitive.wrapper.getSimpleName())
                    .replace(Primitive.INT.primitive.getSimpleName(), primitive.primitive.getSimpleName())
                    .replace(Primitive.INT.name,                      primitive.name)
                    .replace("$$_2", "internal")
                    .replace("$$_1", "interface");
                
                // Determine the new file name
                final Path newFile = Paths.get(file.toString().replace(
                    prototype.getSimpleName(), 
                    prototype.getSimpleName()
                        .replace(Primitive.INT.name, primitive.name)
                ));
                
                // Write the result to that location.
                try {
                    System.out.println("Creating '" + newFile + "'.");
                    Files.write(newFile, result.getBytes(StandardCharsets.UTF_8));
                    counter++;
                } catch (final IOException ex) {
                    throw new RuntimeException("Error writing to file '" + newFile + "'.", ex);
                }
            }
        }
        
        System.out.println("All " + counter + " files have been created.");
    }
}