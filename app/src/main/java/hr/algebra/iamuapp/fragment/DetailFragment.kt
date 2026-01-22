package hr.algebra.iamuapp.fragment

import android.content.ContentUris
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import hr.algebra.iamuapp.NEWS_PROVIDER_CONTENT_URI
import hr.algebra.iamuapp.R
import hr.algebra.iamuapp.databinding.FragmentDetailBinding
import hr.algebra.iamuapp.framework.fetchItem
import hr.algebra.iamuapp.model.Item
import java.io.File

const val ITEM_ID = "item_id"

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val itemId = arguments?.getLong(ITEM_ID) ?: return

        if (itemId == -1L) return

        displayItem(itemId)
        markAsRead(itemId)
    }

    private fun markAsRead(itemId: Long) {
        val values = ContentValues().apply {
            put(Item::read.name, 1)
        }

        requireContext().contentResolver.update(
            ContentUris.withAppendedId(NEWS_PROVIDER_CONTENT_URI, itemId),
            values,
            null,
            null
        )
    }


    private fun displayItem(itemId: Long) {
        val item = requireContext().fetchItem(itemId) ?: return

        binding.tvTitle.text = item.title
        binding.tvDescription.text = item.description
        binding.tvDate.text = item.date

        Picasso.get()
            .load(File(item.picturePath))
            .error(R.drawable.news_icon)
            .into(binding.ivDetail)
    }
}