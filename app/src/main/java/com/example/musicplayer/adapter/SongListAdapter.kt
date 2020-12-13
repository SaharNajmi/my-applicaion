package com.example.musicplayer.adapter

import android.content.Context
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.musicplayer.R
import com.example.musicplayer.model.SongModel

class SongListAdapter(private val context: Context, private val songList: ArrayList<SongModel>) :
    RecyclerView.Adapter<SongListAdapter.Holder>() {

    val mp: MediaPlayer? = null

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.music_row, parent, false)
        return Holder(view)
    }

    override fun getItemCount(): Int {
        return songList.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val model = songList[position]
        val songName = model.songName
        val songDuration = model.songDuration
        val view = holder.itemView

        // view.txt_song_name.text=songName
        view.setOnClickListener {
          
        }
    }
}