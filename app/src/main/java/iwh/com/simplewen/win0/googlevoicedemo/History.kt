package iwh.com.simplewen.win0.googlevoicedemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_history.*

class History : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)
        supportActionBar?.title = "History"
        val mySql =MySql(this@History, "QUES", 1)
        mySql.queryHistory(mySql.writableDatabase)
        val historyData = mySql.hData
        if(historyData.size == 0){
            Toast.makeText(this@History,"it is empty！",Toast.LENGTH_SHORT).show()
        }
        historyList.apply {
            layoutManager   = LinearLayoutManager(this@History)
            adapter = RecycleAdapter(historyData)
        }
    }
}
