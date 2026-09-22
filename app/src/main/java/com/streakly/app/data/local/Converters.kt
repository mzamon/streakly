package com.streakly.app.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter fun boolToInt(b: Boolean): Int = if (b) 1 else 0
    @TypeConverter fun intToBool(i: Int): Boolean = i != 0
}
