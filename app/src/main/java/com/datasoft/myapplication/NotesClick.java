package com.datasoft.myapplication;

import androidx.cardview.widget.CardView;


import com.datasoft.myapplication.Model.Notes;

public interface NotesClick {
    void onClick(Notes notes);
    void onLongClick(Notes notes, CardView cardView);
}
