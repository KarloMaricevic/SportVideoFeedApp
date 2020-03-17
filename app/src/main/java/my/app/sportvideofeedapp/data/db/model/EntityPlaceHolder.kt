package my.app.sportvideofeedapp.data.db.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "placeHolder")
data class EntityPlaceHolder (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int
)
