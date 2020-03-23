package my.app.sportvideofeedapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.di.qualifiers.ActivityContext
import my.app.sportvideofeedapp.ui.feedFragment.FeedFragmentCallback
import javax.inject.Inject

class SportSpinnerAdapter @Inject constructor(
    @ActivityContext context: Context,
    private val feedFragmentCallback: FeedFragmentCallback
) : ArrayAdapter<Sport>(context, android.R.layout.simple_spinner_item) {

    private val glide = Glide.with(context)

    private var chosenSport: Sport? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val item: View

        if (chosenSport == null) {
            item = LayoutInflater.from(context)
                .inflate(android.R.layout.simple_spinner_item, parent, false)
            item.findViewById<TextView>(android.R.id.text1).text =
                context.getText(R.string.choose_sport_spinner_title)
        } else {
            item = LayoutInflater.from(context).inflate(R.layout.item_sport, parent, false)
            bindSportToView(chosenSport!!, item)
        }
        return item
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        var listItem = convertView
        if (listItem == null) {
            listItem = LayoutInflater.from(context).inflate(R.layout.item_sport, parent, false)
        }
        val sport = getItem(position)
        bindSportToView(sport!!, listItem!!)
        return listItem
    }

    private fun bindSportToView(sport: Sport, listItem: View) {
        val onClickListener = View.OnClickListener {
            feedFragmentCallback.chosenSportCallback(sport)
        }

        listItem.findViewById<ConstraintLayout>(R.id.item_spinner_constraint_layout)
            .setOnClickListener(onClickListener)
        listItem.findViewById<TextView>(R.id.item_title)?.text = sport.name
        glide
            .load(sport.iconUrl)
            .into(listItem.findViewById(R.id.sport_image_image_view)!!)
    }

    fun setChosenSport(sport: Sport) {
        chosenSport = sport
        notifyDataSetChanged()
    }
}
