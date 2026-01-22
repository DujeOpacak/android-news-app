package hr.algebra.iamuapp.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import hr.algebra.iamuapp.R
import hr.algebra.iamuapp.adapter.ItemAdapter
import hr.algebra.iamuapp.databinding.FragmentItemsBinding
import hr.algebra.iamuapp.framework.fetchItems
import hr.algebra.iamuapp.model.Item

class ItemsFragment : Fragment() {
    private lateinit var binding: FragmentItemsBinding
    private lateinit var items: MutableList<Item>
    private lateinit var adapter: ItemAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentItemsBinding.inflate(
            inflater,
            container,
            false
        )
        items = requireContext().fetchItems()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = ItemAdapter(requireContext(), items)

        binding.rvItems.layoutManager = LinearLayoutManager(requireContext())
        binding.rvItems.adapter = adapter

        setupSwipeToDelete()
    }

    private fun setupSwipeToDelete() {
        val deleteIcon = ContextCompat.getDrawable(requireContext(), R.drawable.delete)!!

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                t: RecyclerView.ViewHolder
            ) = false

            override fun onSwiped(vh: RecyclerView.ViewHolder, dir: Int) {
                val position = vh.bindingAdapterPosition

                AlertDialog.Builder(requireContext()).apply {
                    setTitle(getString(R.string.delete))
                    setMessage(getString(R.string.delete_confirm))
                    setIcon(R.drawable.delete)
                    setPositiveButton("OK") { _, _ ->
                        adapter.deleteItem(position)
                    }
                    setNegativeButton(getString(R.string.cancel)) { _, _ ->
                        adapter.notifyItemChanged(position)
                    }
                    setOnCancelListener {
                        adapter.notifyItemChanged(position)
                    }
                    show()
                }
            }

            override fun onChildDraw(
                c: Canvas,
                rv: RecyclerView,
                vh: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                action: Int,
                active: Boolean
            ) {
                R.color.accent.toDrawable().apply {
                    val itemView = vh.itemView

                    val iconMargin = (itemView.height - deleteIcon.intrinsicHeight) / 2
                    val iconTop = itemView.top + iconMargin
                    val iconBottom = iconTop + deleteIcon.intrinsicHeight
                    val iconRight = itemView.right - iconMargin
                    val iconLeft = iconRight - deleteIcon.intrinsicWidth

                    deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                    deleteIcon.draw(c)

                    super.onChildDraw(c, rv, vh, dX, dY, action, active)
                }
            }
        }).attachToRecyclerView(binding.rvItems)
    }

    override fun onResume() {
        super.onResume()
        refreshItems()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun refreshItems() {
        val freshItems = requireContext().fetchItems()
        items.clear()
        items.addAll(freshItems)
        adapter.notifyDataSetChanged()
    }
}