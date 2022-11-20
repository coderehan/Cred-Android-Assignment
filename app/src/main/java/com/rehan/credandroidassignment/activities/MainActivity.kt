package com.rehan.credandroidassignment.activities

import android.content.ClipData
import android.content.ClipDescription
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.DragEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.rehan.credandroidassignment.R
import com.rehan.credandroidassignment.databinding.ActivityMainBinding
import com.rehan.credandroidassignment.viewmodels.CredViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var credViewModel: CredViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        credViewModel = ViewModelProvider(this)[CredViewModel::class.java]

        animationSetUp()

        binding.bottomContainer.setOnDragListener(dragListener)

        binding.ivCredLogo.setOnLongClickListener{
            val clipText = "This is our clip data text"
            val item = ClipData.Item(clipText)
            val mimeTypes = arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN)
            val data = ClipData(clipText, mimeTypes, item)

            val dragShadowBuilder = View.DragShadowBuilder(it)
            it.startDragAndDrop(data, dragShadowBuilder, it, 0)
            it.visibility = View.INVISIBLE
            true
        }
    }

    private val dragListener = View.OnDragListener{ view, event ->
        when(event.action){
            DragEvent.ACTION_DRAG_STARTED -> {
                event.clipDescription.hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)
            }
            DragEvent.ACTION_DRAG_ENTERED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DRAG_LOCATION -> true
            DragEvent.ACTION_DRAG_EXITED -> {
                view.invalidate()
                true
            }
            DragEvent.ACTION_DROP -> {
                observeCredResponseLiveData()
                animationSetUp()
                binding.progressBar.visibility = View.GONE
                binding.ivFounder.visibility = View.VISIBLE
                binding.tvName.visibility = View.VISIBLE
                binding.tvFounder.visibility = View.VISIBLE
                binding.tvSuccess.visibility = View.VISIBLE

                view.invalidate()
                val v = event.localState as View
                val owner = v.parent as ViewGroup
                owner.removeView(v)
                val destination = view as LinearLayout
                destination.addView(v)
                v.visibility = View.VISIBLE
                true
            }
            DragEvent.ACTION_DRAG_ENDED -> {
                view.invalidate()
                true
            }
            else -> false
        }
    }

    private fun animationSetUp() {
        val slideAnimation = AnimationUtils.loadAnimation(this, R.anim.animation_slide)
        binding.ivFounder.startAnimation(slideAnimation)
        binding.tvName.startAnimation(slideAnimation)
        binding.tvFounder.startAnimation(slideAnimation)
        binding.tvSuccess.startAnimation(slideAnimation)
    }

    private fun observeCredResponseLiveData() {
        credViewModel.credLiveData.observe(this, Observer {
            animationSetUp()
            binding.progressBar.visibility = View.GONE
            binding.ivFounder.visibility = View.VISIBLE
            binding.tvName.visibility = View.VISIBLE
            binding.tvFounder.visibility = View.VISIBLE
            binding.tvSuccess.visibility = View.VISIBLE
        })
    }
}