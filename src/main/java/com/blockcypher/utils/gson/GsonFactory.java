package com.blockcypher.utils.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Gson Factory Helper
 * @author <a href="mailto:seb.auvray@gmail.com">Sebastien Auvray</a>
 */
public final class GsonFactory {

    private static final Gson GSON;

    private GsonFactory() {
    }

    static {
        GsonBuilder builder = new GsonBuilder();
        //builder.registerTypeAdapter(DateTime.class, new DateTimeTypeConverter());
        builder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES);
        GSON = builder.create();
    }

    /**
     * Get the Gson instance
     *
     * @return the Gson instance
     */
    public static Gson getGson() {
        return GSON;
    }

}
