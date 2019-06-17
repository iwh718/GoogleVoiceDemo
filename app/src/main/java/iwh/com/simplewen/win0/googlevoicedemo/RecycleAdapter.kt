package iwh.com.simplewen.win0.googlevoicedemo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecycleAdapter(private val data:ArrayList<Map<String,Any>>): RecyclerView.Adapter<RecycleAdapter.MyViewHolder>() {
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
       var hQ = view.findViewById<TextView>(R.id.hQ)
       var hA = view.findViewById<TextView>(R.id.hA)
    }
    override fun onBindViewHolder(parent: MyViewHolder, position: Int) {
        parent.hA.text = "A:${data[position]["A"].toString()}"
        parent.hQ.text = data[position]["Q"].toString()

    }
    override fun onCreateViewHolder(p0: ViewGroup, viewType: Int): MyViewHolder  = MyViewHolder(LayoutInflater.from(p0.context).inflate(R.layout.item, p0, false))
    override fun getItemCount(): Int {
        return data.size
    }
}