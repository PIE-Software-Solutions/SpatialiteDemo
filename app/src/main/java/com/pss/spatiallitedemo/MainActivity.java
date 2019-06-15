package com.pss.spatiallitedemo;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.pss.spatiallitedemo.config.SpatiAtlasContract;
import com.pss.spatiallitedemo.config.SpatiAtlasDatabase;
import com.pss.spatiallitedemo.util.SQLiteCursorLoader;
import com.pss.spatiallitedemo.util.Utils;

import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.GEOS_VERSION;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_EPSG;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_FREEXL;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_GEOCALLBACKS;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_GEOS_ADVANCED;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_GEOS_TRUNK;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_ICONV;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_LIBXML2;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_LWGEOM;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.HAS_MATHSQL;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.PROJ4_VERSION;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.SystemInfoColumns.SPATIALITE_VERSION;
import static com.pss.spatiallitedemo.config.SpatiAtlasContract.TownColumns.TOWN_NAME;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Uri uri = SpatiAtlasContract.SystemInfo.CONTENT_URI;
        Cursor c = getContentResolver().query(uri, null, null, null, null);
        if (c == null || !c.moveToFirst()) {
            new AlertDialog.Builder(this)
                    .setTitle("Error")
                    .setMessage("Cannot get system info!")
                    .setPositiveButton(android.R.string.ok, null)
                    .create()
                    .show();
            Utils.closeSilently(c);
        }
        String spliteVer = "Spatialite ver: " + c.getString(c.getColumnIndexOrThrow(SPATIALITE_VERSION));
        String proj4Ver = "PROJ.4 ver: " + c.getString(c.getColumnIndexOrThrow(PROJ4_VERSION));
        String geosVer = "GEOS ver: " + c.getString(c.getColumnIndexOrThrow(GEOS_VERSION));
        String hasGeosAdv = "HasGeosAdvanced: " + c.getString(c.getColumnIndexOrThrow(HAS_GEOS_ADVANCED));
        String hasGeosTrunk = "HasGeosTrunk: " + c.getString(c.getColumnIndexOrThrow(HAS_GEOS_TRUNK));
        String hasLwgeom = "HasLwgeom: " + c.getString(c.getColumnIndexOrThrow(HAS_LWGEOM));
        String hasGeoCallbacks = "HasGeoCallbacks: " + c.getString(c.getColumnIndexOrThrow(HAS_GEOCALLBACKS));
        String hasMathSql = "HasMathSQL: " + c.getString(c.getColumnIndexOrThrow(HAS_MATHSQL));
        String hasLibXml = "HasLibXML2: " + c.getString(c.getColumnIndexOrThrow(HAS_LIBXML2));
        String hasEpsg = "HasEPSG: " + c.getString(c.getColumnIndexOrThrow(HAS_EPSG));
        String hasIconv = "HasIconv: " + c.getString(c.getColumnIndexOrThrow(HAS_ICONV));
        String hasFreexl = "HasFreexl: " + c.getString(c.getColumnIndexOrThrow(HAS_FREEXL));
        Utils.closeSilently(c);

        uri = SpatiAtlasContract.Towns.CONTENT_URI;
        c = getContentResolver().query(uri, new String[]{"geometry"}, null, null, null);
        if(c!=null) {
            while (c.moveToNext()) {
                String townName = "Spatialite ver: " + c.getString(c.getColumnIndexOrThrow(SpatiAtlasContract.GeometryColumn.GEOMETRY));
                Log.d("AAAAAAAAAAAAAAA : ", townName);
            }
        }

        Utils.closeSilently(c);

    }
}
