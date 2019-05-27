package com.carveniche.begalileo.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerGameStatusModel : ViewModel() {
    private val gameScore = MutableLiveData<Int>()
    fun getGameScore() : LiveData<Int>
    {
        gameScore.value = 0
        return gameScore
    }
    fun updateGameScore(value : Int)
    {
        gameScore.value = value
    }
}