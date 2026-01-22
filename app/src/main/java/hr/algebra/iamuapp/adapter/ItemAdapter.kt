package hr.algebra.iamuapp.adapter

import android.content.ContentUris
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import hr.algebra.iamuapp.NEWS_PROVIDER_CONTENT_URI
import hr.algebra.iamuapp.R
import hr.algebra.iamuapp.fragment.ITEM_ID
import hr.algebra.iamuapp.model.Item
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation
import java.io.File

class ItemAdapter(
    private val context: Context,
    private val items: MutableList<Item>
) : RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(
                    R.layout.item,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(items[position])

        holder.itemView.setOnClickListener {
            val currentPosition = holder.bindingAdapterPosition
            if (currentPosition != RecyclerView.NO_POSITION) {
                val item = items[currentPosition]

                val bundle = Bundle().apply {
                    putLong(ITEM_ID, item._id!!)
                }
                it.findNavController().navigate(R.id.action_items_to_details, bundle)
            }
        }
    }

    fun deleteItem(position: Int) {
        val item = items[position]
        context.contentResolver.delete(
            ContentUris.withAppendedId(NEWS_PROVIDER_CONTENT_URI, item._id!!),
            null,
            null
        )
        File(item.picturePath).delete()
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun getItemCount() = items.count()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val ivItem = itemView.findViewById<ImageView>(R.id.ivItem)
        private val tvItem = itemView.findViewById<TextView>(R.id.tvItem)
        private val ivReadStatus = itemView.findViewById<ImageView>(R.id.ivReadStatus)

        fun bind(item: Item) {
            tvItem.text = item.title

            val flagResource = if (item.read) {
                R.drawable.green_flag
            } else {
                R.drawable.red_flag
            }

            ivReadStatus.setImageResource(flagResource)

            Picasso.get()
                .load(File(item.picturePath))
                .error(R.drawable.news_icon)
                .transform(RoundedCornersTransformation(50, 50))
                .into(ivItem)
        }
    }
}