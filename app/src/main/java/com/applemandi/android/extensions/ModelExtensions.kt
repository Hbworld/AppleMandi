package com.applemandi.android.extensions

import com.applemandi.android.data.local.entity.VillageEntity
import com.applemandi.android.data.model.Village


fun VillageEntity.toDomain(): Village = Village(
    name = this.name,
    rate = this.rate
)


fun Village.toEntity(): VillageEntity = VillageEntity(
    name = this.name,
    rate = this.rate
)
