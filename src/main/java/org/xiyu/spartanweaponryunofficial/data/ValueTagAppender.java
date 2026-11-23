package org.xiyu.spartanweaponryunofficial.data;

import net.minecraft.data.tags.TagAppender;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;

import java.util.function.Function;

/**
 * Tag appender that takes registry values instead of resource keys.
 */
public final class ValueTagAppender<T> {
    private final TagAppender<T> appender;
    private final Function<T, ResourceKey<T>> keyFunction;

    public ValueTagAppender(TagAppender<T> appender, Function<T, ResourceKey<T>> keyFunction) {
        this.appender = appender;
        this.keyFunction = keyFunction;
    }

    @SafeVarargs
    public final ValueTagAppender<T> add(T... values) {
        for (T value : values) {
            this.appender.add(this.keyFunction.apply(value));
        }
        return this;
    }

    public ValueTagAppender<T> addTag(TagKey<T> tag) {
        this.appender.addTag(tag);
        return this;
    }

    @SafeVarargs
    public final ValueTagAppender<T> addTags(TagKey<T>... tags) {
        this.appender.addTags(tags);
        return this;
    }

    public ValueTagAppender<T> addOptionalTag(TagKey<T> tag) {
        this.appender.addOptionalTag(tag);
        return this;
    }
}
