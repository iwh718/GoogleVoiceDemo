package iwh.com.simplewen.win0.googlevoicedemo

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.speech.RecognizerIntent
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {
    //history
    private val historyData = arrayListOf<Map<String, Any>>()
    private var questionsData = arrayListOf<String>()
    private lateinit var handler: Handler
    private var requestCode: Int = 0
    private lateinit var mySql:MySql

    private fun voice() {
        val intent = Intent(
            RecognizerIntent.ACTION_RECOGNIZE_SPEECH
        )
        //mode
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        //tips text
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Please start your voice")
        //check
        try {
            startActivityForResult(intent, requestCode)
        } catch (a: ActivityNotFoundException) {
            Toast.makeText(
                this@MainActivity,
                "Opps! Your device doesn't support Speech to Text",
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        handler = Handler()

        //init SQL
        mySql = MySql(this@MainActivity, "QUES", 1)
        mySql.query(mySql.writableDatabase, handler)
        questionsData = mySql.sData

        questionText.text = "Q:how are you ? \n\n"
        //begin Answer
        fab.setOnClickListener {
            //start voice
            voice()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.history -> {
                startActivity(Intent(this@MainActivity,History::class.java))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            0 -> {
                Log.d("@@into 0.","----")
                if (resultCode == RESULT_OK && data != null) {
                   val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[1]}"
                    Log.d("@@data:", questionsData[requestCode])
                    this.requestCode = 1
                    mySql.addHistory(mySql.writableDatabase,questionsData[0],text[0])
                }
            }
            1 -> {
                //Q:How is the appetite recently ?
                Log.d("@@into 1.","----")
                if (resultCode == RESULT_OK && data != null) {
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if(text[0] == "good" || text[0] == "Good"){
                        this.requestCode = 2
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[2]}"
                        mySql.addHistory(mySql.writableDatabase,questionsData[2],text[0])
                    }else if(text[0] == "bad" || text[0] == "Bad"){
                        this.requestCode = 3
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[3]}"
                        mySql.addHistory(mySql.writableDatabase,questionsData[3],text[0])
                    }else{
                        Toast.makeText(this@MainActivity,"Please repeat it again！",Toast.LENGTH_SHORT).show()
                    }

                }
            }
            2 -> {
                Log.d("@@into 2.","----")
                //is the wound still hurting ?
                if (resultCode == RESULT_OK && data != null) {
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if(text[0] == "Yes" || text[0] == "yes"){
                        this.requestCode = 4
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[4]}"
                        mySql.addHistory(mySql.writableDatabase,questionsData[4],text[0])
                    }else if(text[0] == "no" || text[0] == "No"){
                        mySql.addHistory(mySql.writableDatabase,questionsData[5],text[0])
                        this.requestCode = 5
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[5]}"
                    }else{
                        Toast.makeText(this@MainActivity,"Please repeat it again！",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            3 -> {
                Log.d("@@into 3.","----")
                //do you have a good sleep then ?
                if (resultCode == RESULT_OK && data != null) {
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if(text[0] == "good" || text[0] == "Good"){
                        this.requestCode = 4
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[6]}"
                        mySql.addHistory(mySql.writableDatabase,questionsData[6],text[0])
                        this.requestCode = 5
                    }else if(text[0] == "bad" || text[0] == "Bad"){
                        this.requestCode = 7
                        mySql.addHistory(mySql.writableDatabase,questionsData[7],text[0])
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[7]}"
                    }else{
                        Toast.makeText(this@MainActivity,"Please repeat it again！",Toast.LENGTH_SHORT).show()
                    }
                }
            }
            //is the pain always there ?
            4 -> {
                Log.d("@@into 4.","----")
                if (resultCode == RESULT_OK && data != null) {
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if(text[0] == "yes" || text[0] == "Yes"){
                        this.requestCode = 5
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[8]}"
                        mySql.addHistory(mySql.writableDatabase,questionsData[8],text[0])
                    }else if(text[0] == "no" || text[0] == "No"){
                        this.requestCode = 5
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[9]}"
                        mySql.addHistory(mySql.writableDatabase,questionsData[9],text[0])
                    }else{
                        Toast.makeText(this@MainActivity,"Please repeat it again！",Toast.LENGTH_SHORT).show()
                    }
                }

            }
            //question over！
            5 ->{
                Log.d("@@into 5.","----")
                questionText.text = "${questionText.text}A: Over!"
                AlertDialog.Builder(this@MainActivity).setTitle("Question is over")
                    .setMessage("again？").setPositiveButton("Yes"){
                            _,_ ->
                        this.requestCode = 0
                        questionText.text = "Q:how are you ?\n\n"
                        Toast.makeText(this@MainActivity,"Clear！",Toast.LENGTH_SHORT).show()
                    }.setNegativeButton("Cancel",null).show()
            }
            7 ->{
                //do you feel
                if (resultCode == RESULT_OK && data != null) {
                    val text = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
                    if(text[0] == "yes" || text[0] == "Yes"){
                        this.requestCode = 5
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[8]}"
                        mySql.addHistory(mySql.writableDatabase,questionsData[8],text[0])
                    }else if(text[0] == "no" || text[0] == "No"){
                        this.requestCode = 5
                        questionText.text = "${questionText.text}A:${text[0]}\n\n ${questionsData[10]}"
                        mySql.addHistory(mySql.writableDatabase,questionsData[10],text[0])
                    }else{
                        Toast.makeText(this@MainActivity,"Please repeat it again！",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }


}
