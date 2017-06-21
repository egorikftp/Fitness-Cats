package com.egoriku.catsrunning.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.animation.LinearOutSlowInInterpolator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import com.egoriku.catsrunning.R
import com.egoriku.catsrunning.activities.FitActivity
import com.egoriku.catsrunning.activities.TrackOnMapsActivity
import com.egoriku.catsrunning.activities.TracksActivity
import com.egoriku.catsrunning.helpers.TypeFit
import com.egoriku.catsrunning.models.Constants
import com.egoriku.catsrunning.data.TracksModel
import com.egoriku.catsrunning.ui.adapter.TracksAdapter
import com.egoriku.catsrunning.ui.commons.TracksDataManager
import com.egoriku.catsrunning.ui.commons.UIListener
import com.egoriku.catsrunning.util.inflate
import kotlinx.android.synthetic.main.fragment_tracks.*


class TracksFragment : Fragment(), TracksAdapter.onViewSelectedListener, UIListener {

    private lateinit var tracksAdapter: TracksAdapter
    private val tracksDataManager = TracksDataManager.instance
    private var anim: TranslateAnimation = TranslateAnimation(0f, 0f, 40f, 0f)

    init {
        anim.apply {
            duration = 200
            interpolator = LinearOutSlowInInterpolator()
            fillAfter = true
        }
    }

    override fun handleSuccess(data: List<TracksModel>) {
        tracksAdapter.setItems(data)
    }

    override fun handleError() {
    }

    override fun onFavoriteClick(item: TracksModel) {
    }

    override fun onLongClick(item: TracksModel) {
    }

    override fun onClickItem(item: TracksModel) {
        if (item.time == 0L) {
            val intent = Intent(context, FitActivity::class.java)
            intent.putExtra(Constants.Extras.KEY_TYPE_FIT, item.typeFit)
            startActivity(intent)
        } else {
            val intent = Intent(context, TrackOnMapsActivity::class.java)
            intent.putExtra(Constants.Extras.EXTRA_TRACK_ON_MAPS, item)
            startActivity(intent)
        }
    }

    companion object {
        fun newInstance(): TracksFragment {
            return TracksFragment()
        }
    }

    override fun onStart() {
        super.onStart()
        (activity as TracksActivity).onFragmentStart(R.string.navigation_drawer_main_activity_new)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(R.layout.fragment_tracks)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        tracks_recyclerview.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
        }
        initAdapter()

        tracksDataManager.addListener(this)
        tracksDataManager.loadTracks(TypeFit.WALKING)

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_walking -> consumeEvent(TypeFit.WALKING, item.itemId)
                R.id.action_running -> consumeEvent(TypeFit.RUNNING, item.itemId)
                R.id.action_cycling -> consumeEvent(TypeFit.CYCLING, item.itemId)
                else -> false
            }
        }
    }

    inline private fun consumeEvent(typeFit: Int, itemId: Int): Boolean {
        if (bottomNavigationView.selectedItemId != itemId) {
            tracks_recyclerview.animation = anim
            anim.start()
        }
        tracksDataManager.loadTracks(typeFit)
        return true
    }

    private fun initAdapter() {
        if (tracks_recyclerview.adapter == null) {
            tracksAdapter = TracksAdapter(this)
            tracks_recyclerview.adapter = tracksAdapter
        }
    }

    override fun onStop() {
        super.onStop()
        tracksDataManager.removeUIListener()
    }
}