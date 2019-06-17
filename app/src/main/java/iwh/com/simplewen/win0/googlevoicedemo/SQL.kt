package iwh.com.simplewen.win0.googlevoicedemo

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Handler
import android.util.Log
import android.widget.Toast
import java.lang.Exception

/**
 * SQL Utils
 */
class MySql(context: Context, name: String, version: Int) : SQLiteOpenHelper(context, name, null, version) {
    var mContext = context
    var sData = arrayListOf<String>()
    var hData = arrayListOf<Map<String,Any>>()


    private val initDataBase = "CREATE TABLE questions (\n" +
            "  `id` int(3) NOT NULL,\n" +
            "  `content` varchar(100)   NOT NULL ,\n" +
            "  PRIMARY KEY (`id`)\n" +
            ")"//SQL create
    private val initHistory = "CREATE TABLE history (\n" +
            "  `id` integer primary Key autoincrement,\n" +
            "  `Q` varchar(100)   NOT NULL ,\n" +
            "  `A` varchar(100)   NOT NULL \n" +
            ")"//SQL create
    private val questionsData = arrayListOf(
        "INSERT INTO `questions` VALUES ('1','Q:how are you ? \n\n');",
        "INSERT INTO `questions` VALUES ('2','Q:How is the appetite recently ?\n\n');",
        "INSERT INTO `questions` VALUES ('3','Q:is the wound still hurting ?\n\n');",
        "INSERT INTO `questions` VALUES ('4','Q:do you have a good sleep then ?\n\n');",
        "INSERT INTO `questions` VALUES ('5','Q:is the pain always there ?\n\n');",
        "INSERT INTO `questions` VALUES ('6','Q:remember to take your medicineon time ,byebye \n\n');",
        "INSERT INTO `questions` VALUES ('7','Q:maybe reduce anti-inflammatory drugs\n\n');",
        "INSERT INTO `questions` VALUES ('8','Q:do you feel that you are in a bad body condition ?\n\n');",
        "INSERT INTO `questions` VALUES ('9','Q:contact the doctor\n\n');",
        "INSERT INTO `questions` VALUES ('10','Q:that is reasonable do not worry\n\n');",
        "INSERT INTO `questions` VALUES ('11','Q:take it easy then,exercise more,believe in your yourself\n\n');"

    )


    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit

    override fun onCreate(db: SQLiteDatabase?) {
        try {
            db!!.disableWriteAheadLogging()
            db.execSQL(initDataBase)
            db.execSQL(initHistory)
            for (i in questionsData) {
                db.execSQL(i)
            }
        } catch (e: NullPointerException) {
            Log.d("@@@error:", e.toString())
        }
    }

    /*query method*/
    /**@param db database
     * @param handler
     * @param table_name
     **/
    fun query(
        db: SQLiteDatabase,
        handler: Handler,
        table_name: String = "questions"
    ): Boolean {
        db.disableWriteAheadLogging()
        this.sData.clear()


        val cursor = db.query(table_name, null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            try {

                do {

                    sData.add(cursor.getString(cursor.getColumnIndex("content")))
                    //    Log.d("@@@temMap:", "$sData")
                } while (cursor.moveToNext())
            } catch (e: Exception) {

                Log.d("@@error:query:", e.toString())
                return false
            } finally {

                Log.d("@@@look_sj", sData.toString())
                cursor.close()
                db.close()
            }


        }
        return true
    }
    //add item
    fun addHistory(
        db: SQLiteDatabase,
        Q:String,
        A:String
    ){
        db.execSQL("INSERT INTO history(Q,A) values('$Q','$A')")
        this.queryHistory(db)
    }
    //query History
    fun queryHistory( db: SQLiteDatabase,table_name: String = "history"){
        db.disableWriteAheadLogging()
        this.hData.clear()


        val cursor = db.query(table_name, null, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            try {

                do {
                    val hashMap = mapOf(
                        "Q" to cursor.getString(cursor.getColumnIndex("Q")),
                        "A" to cursor.getString(cursor.getColumnIndex("A") ))
                    hData.add(hashMap)
                    //    Log.d("@@@temMap:", "$sData")
                } while (cursor.moveToNext())
            } catch (e: Exception) {

                Log.d("@@error:query:", e.toString())

            } finally {

                Log.d("@@@look_hData", hData.toString())
                cursor.close()
                db.close()
            }


        }
    }
}


