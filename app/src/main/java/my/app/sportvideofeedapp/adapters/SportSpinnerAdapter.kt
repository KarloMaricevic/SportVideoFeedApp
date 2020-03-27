package my.app.sportvideofeedapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import my.app.sportvideofeedapp.R
import my.app.sportvideofeedapp.data.entities.Sport
import my.app.sportvideofeedapp.di.qualifiers.ActivityContext
import javax.inject.Inject

class SportSpinnerAdapter @Inject constructor(
    @ActivityContext context: Context
) : ArrayAdapter<Sport>(context, 0) {

    private val glide = Glide.with(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = createView(convertView, parent)
        bindView(view, getItem(position)!!)
        return view
    }

    @Suppress("MaxLineLength")
    // wanted to make onClickListener to solve initialOnItemSelected but when I set listener spinner stays expanded on itemClick

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = createDropDownView(position, convertView, parent)
        bindView(view, getItem(position)!!)
        return view
    }

    private fun createView(convertView: View?, parent: ViewGroup): View {
        return convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_selected_sport,
            parent,
            false
        )
    }

    private fun createDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return convertView ?: LayoutInflater.from(context).inflate(
            R.layout.item_option_sport,
            parent,
            false
        ).also {
            when (position) {
                0 -> {
                    it.background = context.getDrawable(R.drawable.shape_top_option_sport_spinner)
                }
                count - 1 -> {
                    it.background = context.getDrawable(R.drawable.shape_bottom_option_sport_spinner)
                }
                else -> {
                    it.background = context.getDrawable(R.drawable.shape_middle_option_sport_spinner)
                }
            }
        }
    }

    private fun bindView(view: View, sport: Sport) {
        view.also {
            it.findViewById<TextView>(R.id.item_title).text = sport.name
            glide
                .load(sport.iconUrl)
                .error(R.drawable.ic_glide_error_24dp)
                .fitCenter()
                .into(it.findViewById(R.id.sport_image_image_view))
        }
    }
}
