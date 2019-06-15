package com.pss.spatiallitedemo.config;

import android.content.Context;
import android.net.Uri;

public class SpatiAtlasDatabase extends SpatialiteFileDbHelper {

    private static final String DATABASE_NAME = "geodb.sqlite";

    public SpatiAtlasDatabase(Context context, String dbName) {
        super(context, dbName);
    }

    public SpatiAtlasDatabase(Context context, String assetName, String dbName) {
        super(context, assetName, dbName);
    }

    public SpatiAtlasDatabase(Context context, Uri dbUri, String dbName) {
        super(context, dbUri, dbName);
    }

    public static void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    interface Tables {
        String SPATIAL_REF_SYS = "spatial_ref_sys";
        String VECTOR_LAYERS = "vector_layers";
        String VECTOR_LAYERS_STATS = "vector_layers_statistics";

        String SPATIAL_INDEX = "SpatialIndex";

        String TOWNS = "towns";
    }

    private interface Triggers {
    }

    /**
     * Fully-qualified field names.
     */
    private interface Qualified {
    }

    /**
     * {@code REFERENCES} clauses.
     */
    private interface References {
    }
}