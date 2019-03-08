package com.example.ashleighwilson.booksearch.data;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.convert.Convert;

import java.lang.reflect.Type;
import java.lang.annotation.Annotation;
import androidx.annotation.Nullable;
import okhttp3.ResponseBody;
import okhttp3.RequestBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class SimpleXmlConverterFactory extends Converter.Factory {

    public static SimpleXmlConverterFactory create() {
        return create(new Persister());
    }

    /** Create an instance using {@code serializer} for conversion. */
    public static SimpleXmlConverterFactory create(Serializer serializer) {
        return new SimpleXmlConverterFactory(serializer, false);
    }

    /** Create an instance using a default {@link Persister} instance for non-strict conversion. */
    public static SimpleXmlConverterFactory createNonStrict() {
        return createNonStrict(new Persister());
    }

    /** Create an instance using {@code serializer} for non-strict conversion. */
    @SuppressWarnings("ConstantConditions") // Guarding public API nullability.
    public static SimpleXmlConverterFactory createNonStrict(Serializer serializer) {
        if (serializer == null) throw new NullPointerException("serializer == null");
        return new SimpleXmlConverterFactory(serializer, false);
    }

    private final Serializer serializer;
    private final boolean strict;

    private SimpleXmlConverterFactory(Serializer serializer, boolean strict) {
        this.serializer = serializer;
        this.strict = strict;
    }

    public boolean isStrict() {
        return strict;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        if (!(type instanceof Class)) {
            return null;
        }
        Class<?> cls = (Class<?>) type;
        return new SimpleXmlResponseBodyConverter<>(cls, serializer, false);
    }

    @Override public @Nullable
    Converter<?, RequestBody> requestBodyConverter(Type type,
                                                   Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        if (!(type instanceof Class)) {
            return null;
        }
        return new SimpleXmlRequestBodyConverter<>(serializer);
    }
}
