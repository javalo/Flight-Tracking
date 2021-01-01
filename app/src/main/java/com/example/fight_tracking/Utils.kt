package com.example.fight_tracking

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonParser
import org.apache.commons.io.IOUtils
import org.json.JSONArray
import java.io.*
import java.nio.charset.Charset


const val TAG: String = "Utils"

class Utils private constructor() {

    companion object {

        fun generateAirportList(): List<Airport>{
            val airportList = ArrayList<Airport>()

            for (airportObject in getAirportsListJson()) {
                airportList.add(Gson().fromJson(airportObject.asJsonObject, Airport::class.java))
            }
            return airportList
        }

        fun getAirportsListJson() : JsonArray{
            var input: InputStream? = null
            input = FlightApplication.appAssetManager.open("airports.json")
            val parser = JsonParser()
            val jsonElement = parser.parse(getTextFromStream(input))
            return jsonElement.asJsonArray
        }


        fun getText(filename: String): String? {
            val f = File(getRootDirectory(), filename)

            return getTextFromfile(f)
        }

        fun getTextFromfile(f: File): String? {
            var content: String? = null
            val filename = f.name

            if (f.exists()) {
                try {
                    val inputStream = FileInputStream(f)

                    content = getTextFromStream(inputStream)
                } catch (e: Exception) {
                    Log.e(TAG, "Error while opening to open text file in cache :$filename", e)
                }

            } else {
                Log.e(TAG, "Text file does not exist in cache :$filename")
            }

            return content;
        }

        fun getTextFromStream(inputStream: InputStream): String? {
            return try {
                val writer = StringWriter()
                IOUtils.copy(inputStream, writer, Charset.forName("UTF-8"))
                writer.toString()
            } catch (e: Exception) {
                Log.e(TAG, "Can't read text from stream", e)
                null
            }

        }

        private fun getRootDirectory(): File {
            return FlightApplication.appContext!!.getDir("appCache", Context.MODE_PRIVATE);
        }

        fun _saveData(data: ByteArray, filename: String): Boolean {
            try {
                val f = File(getRootDirectory(), "tempsJson")

                val fos = FileOutputStream(f)
                fos.write(data)
                fos.close()
                Log.i(TAG, "File written to disk (${f.getAbsolutePath()}")

                return true
            } catch (e: Exception) {
                Log.e(TAG, "Impossible to save  $filename", e)
                return false
            }
        }


        fun getFlightListFromString(arrayAsString: String): List<FlightModel>{
            val flightJsonArray = convertStringToJsonArray(arrayAsString)
            val flightModelList = ArrayList<FlightModel>()
            for(flightJson in flightJsonArray){
                flightModelList.add(Gson().fromJson(flightJson.asJsonObject, FlightModel::class.java))
            }
            return flightModelList
        }

        private fun convertStringToJsonArray(arrayAsString: String): JsonArray{
            val jsonElement = JsonParser.parseString(arrayAsString)
            return jsonElement.asJsonArray
        }




        fun _makeJsonAirportLight() {
            var input: InputStream? = null

            input = FlightApplication.appAssetManager.open("airports.json")

            val filteredAirports = JSONArray()

            val jsonArray = JSONArray(getTextFromStream(input))
            for (i in 0 until jsonArray.length()) {
                val airportJson = jsonArray.getJSONObject(i)
                if (airportJson.optInt("direct_flights", 0) > 100) {
                    filteredAirports.put(airportJson)
                }
            }

            _saveData(filteredAirports.toString().toByteArray(), "tempJson")
        }
    }
}
