package com.hardrelice.ticking.ui.arrange

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import com.hardrelice.ticking.databinding.FragmentArrangeBinding
import com.hardrelice.ticking.util.GraphicUtil

class ArrangeFragment : Fragment() {


    private lateinit var arrangeViewModel: ArrangeViewModel
    private var _binding: FragmentArrangeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arrangeViewModel =
            ViewModelProvider(this).get(ArrangeViewModel::class.java)

        _binding = FragmentArrangeBinding.inflate(inflater, container, false)
        val root: View = binding.root
        val handler = Handler(Looper.getMainLooper())
        val height = GraphicUtil.getStatusBarHeight(resources)
        var touchStartTop = 0
        var offsetMove = 0
        var offsetDown = 0
        var animating = false
        binding.arrangeCalendarMonth.isEnabled = false
        binding.dragBar.setOnTouchListener { v, event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    animating = false
                    binding.arrangeCalendarMonth.isEnabled = false
                    touchStartTop = event.rawY.toInt()
                    offsetDown = binding.arrangeContent.top - event.rawY.toInt() + height
                }
                MotionEvent.ACTION_MOVE -> {
                    (event.rawY.toInt() - height + offsetDown).also {
                        if (it >= 0 && it <= binding.arrangeCalendarMonth.height) {
                            binding.arrangeContent.top = it
                        }
                    }
                }
                MotionEvent.ACTION_UP -> {
                    animating = true
                    binding.arrangeCalendarMonth.isEnabled = false
                    val up = touchStartTop - event.rawY.toInt() > 0
                    val down = touchStartTop - event.rawY.toInt() < 0
                    val stay = !up && !down
                    val upGo =
                        event.rawY.toInt() - height < binding.arrangeCalendarMonth.height.toFloat() * 5f / 6f
                    val downGo =
                        event.rawY.toInt() - height > binding.arrangeCalendarMonth.height.toFloat() / 6f
                    println("$up $upGo $down $downGo ")
                    val moveUp = (up && upGo) ||
                            (down && !downGo) ||
                            (stay && event.rawY.toInt() <=
                                    binding.arrangeCalendarMonth.height / 2)
                    val moveDown = (up && !upGo) ||
                            (down && downGo) ||
                            (stay && event.rawY.toInt() >
                                    binding.arrangeCalendarMonth.height / 2)
                    if (moveUp) {
                        val step = binding.arrangeContent.top / 20
                        fun goUp() {
                            (binding.arrangeContent.top - step).also {
                                if (it <= 0) binding.arrangeContent.top = 0
                                else binding.arrangeContent.top = it
                            }
                            if (_binding != null && binding.arrangeContent.top > 0 && animating)
                                handler.postDelayed({ goUp() }, 15)
                        }
                        goUp()
                    } else if (moveDown) {
                        val step =
                            (binding.arrangeCalendarMonth.height - binding.arrangeContent.top) / 20

                        fun goDown() {
                            (binding.arrangeContent.top + step).also {
                                if (it >= binding.arrangeCalendarMonth.height) binding.arrangeContent.top =
                                    binding.arrangeCalendarMonth.height
                                else binding.arrangeContent.top = it
                            }
                            if (_binding != null && binding.arrangeContent.top < binding.arrangeCalendarMonth.height && animating)
                                handler.postDelayed({ goDown() }, 15)
                        }
                        goDown()
                    }
                }
            }
            true
        }
        binding.arrangeCalendarMonth.setOnDateChangeListener { view, year, month, dayOfMonth ->

        }
        binding.arrangeContentRecyclerView.adapter =
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}