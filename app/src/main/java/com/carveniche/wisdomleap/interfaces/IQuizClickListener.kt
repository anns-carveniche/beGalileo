package com.carveniche.wisdomleap.interfaces

import com.carveniche.wisdomleap.model.RecentPractice

interface IQuizClickListener {
    fun onQuizItemClick(recentQuiz : RecentPractice)
}