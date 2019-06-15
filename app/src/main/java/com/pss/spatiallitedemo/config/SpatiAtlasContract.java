package com.pss.spatiallitedemo.config;

import android.net.Uri;
import android.provider.BaseColumns;

public class SpatiAtlasContract {

    public static final String CONTENT_AUTHORITY = "com.pss.spatiallitedemo.config";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    private static final String PATH_SRS = "srs";
    private static final String PATH_SPATIAL_INDEX = "spatialindex";
    private static final String PATH_GEOMTABLE = "geomtable";
    private static final String PATH_TOWNS = "towns";

    private static final String PATH_SYSTEM_INFO = "sysinfo";

    public enum GeometryType {
        POINT(1),
        LINESTRING(2),
        POLYGON(3),
        MULTIPOINT(4),
        MULTILINESTRING(5),
        MULTIPOLYGON(6),
        GEOMETRYCOLLECTION(7);

        private final int mType;

        GeometryType(int type) {
            mType = type;
        }

        public static GeometryType fromInt(int value) {
            if (value < 1 || value > 7)
                throw new IndexOutOfBoundsException("Invalid geometry type: " + value + ". Valid types: [1..7]");
            return GeometryType.values()[value - 1];
        }
    }

    public interface GeometryColumn {
        String GEOMETRY = "geometry";
    }

    public interface TownColumns {
        //String TOWN_ID = "id";
        String TOWN_NAME = "name";
        //String TOWN_GEOM = "geometry"
    }

    public interface SpatialIndexColumns {
        String ROWID = "rowid";
    }

    public interface SpatialRefSysColumns {
        String SRID = "srid";
        String AUTH_NAME = "auth_name";
        String AUTH_SRID = "auth_srid";
        String REF_SYS_NAME = "ref_sys_name";
        String PROJ4_TEXT = "proj4text";
        String SRTEXT = "SRTEXT";
    }

    public interface VectorLayersMetadataColumns {
        String LAYER_TYPE = "layer_type";
        String TABLE_NAME = "table_name";
        String GEOMETRY_COLUMN = "geometry_column";
        String GEOMETRY_TYPE = "geometry_type";
        String COORD_DIMENSION = "coord_dimension";
        String SRID = "srid";
        String SPATIAL_INDEX_ENABLED = "spatial_index_enabled";
    }

    public interface SystemInfoColumns {
        String SPATIALITE_VERSION = "spatialite_version";
        String PROJ4_VERSION = "proj4_version";
        String GEOS_VERSION = "geos_version";
        String HAS_PROJ = "hasProj";
        String HAS_GEOS = "hasGeos";
        String HAS_GEOS_ADVANCED = "hasGeosAdvanced";
        String HAS_GEOS_TRUNK = "hasGeosTrunk";
        String HAS_LWGEOM = "hasLwgeom";
        String HAS_GEOCALLBACKS = "hasGeocallbacks";
        String HAS_MATHSQL = "hasMathsql";
        String HAS_LIBXML2 = "hasLibxml2";
        String HAS_EPSG = "hasEpsg";
        String HAS_ICONV = "hasIconv";
        String HAS_FREEXL = "hasFreexl";
    }

    public interface Methods {
        interface AttachDb {
            String METHOD = "AttachDatabaseAs";
            String ARG_ALIAS = "Alias";
            String PARAM_FILEPATH = "FilePath";
        }

        interface DetachDb {
            String METHOD = "DetachDatabaseAs";
            String ARG_ALIAS = "Alias";
        }
    }

    public static class GeometryTable implements GeometryColumn, BaseColumns {
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_GEOMTABLE).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.atlas.geometry";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.atlas.geometry";

        // TODO: Remove limit parameter and the client should append it himself to the uri
        public static final Uri buildUri(String table, int srid, double simplificationTolerance, int limit) {
            final Uri.Builder b = GeometryTable.CONTENT_URI.buildUpon()
                    .appendPath(table);

            if (srid > 0)
                b.appendQueryParameter("srid", String.valueOf(srid));

            if (simplificationTolerance > 0)
                b.appendQueryParameter("simplify", String.valueOf(simplificationTolerance));

            if (limit > 0)
                b.appendQueryParameter("limit", String.valueOf(limit));

            return b.build();
        }

        public static String getTableName(Uri uri) {
            return uri.getLastPathSegment();
        }
    }

    public static class Towns extends GeometryTable implements TownColumns, BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_TOWNS).build();

        public static final String TABLE = "towns";

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.atlas.town";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.atlas.town";

        public static String getTownId(Uri uri) {
            return uri.getPathSegments().get(1);
        }
    }

    public static class SpatialIndex implements SpatialIndexColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SPATIAL_INDEX).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.atlas.rowid";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.atlas.rowid";

        public static Uri buildQueryUri(String table, double xmin, double ymin, double xmax, double ymax, int srid) {
            return CONTENT_URI.buildUpon()
                    .appendQueryParameter(QueryParams.TABLE, table)
                    .appendQueryParameter(QueryParams.XMIN, String.valueOf(xmin))
                    .appendQueryParameter(QueryParams.YMIN, String.valueOf(ymin))
                    .appendQueryParameter(QueryParams.XMAX, String.valueOf(xmax))
                    .appendQueryParameter(QueryParams.YMAX, String.valueOf(ymax))
                    .appendQueryParameter(QueryParams.SRID, String.valueOf(srid))
                    .build();
        }

        public interface QueryParams {
            String TABLE = "table";
            String XMIN = "xmin";
            String YMIN = "ymin";
            String XMAX = "xmax";
            String YMAX = "ymax";
            String SRID = "srid";
        }
    }


    public static class SystemInfo implements SystemInfoColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_SYSTEM_INFO).build();

        public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.atlas.sysinfo";
        public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.atlas.sysinfo";
    }
}
