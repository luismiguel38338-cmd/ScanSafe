package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.HazardLevel
import com.example.model.ItemCategory
import com.example.model.ScanResultItem

@Entity(tableName = "saved_scans")
data class SavedScanEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val scientificOrChemicalName: String,
    val categoryName: String,
    val hazardLevelName: String,
    val toxicityScore: Int,
    val isHarmful: Boolean,
    val summary: String,
    val humanHazard: String,
    val petHazard: String,
    val activeToxinsJoined: String,
    val commonSymptomsJoined: String,
    val firstAid: String,
    val safeHandlingTips: String,
    val commonUses: String,
    val imageResOrUri: String?,
    val scannedAt: Long,
    val isFavorite: Boolean
) {
    fun toDomainModel(): ScanResultItem {
        return ScanResultItem(
            id = id,
            name = name,
            scientificOrChemicalName = scientificOrChemicalName,
            category = try { ItemCategory.valueOf(categoryName) } catch (_: Exception) { ItemCategory.OTHER },
            hazardLevel = try { HazardLevel.valueOf(hazardLevelName) } catch (_: Exception) { HazardLevel.SAFE },
            toxicityScore = toxicityScore,
            isHarmful = isHarmful,
            summary = summary,
            humanHazard = humanHazard,
            petHazard = petHazard,
            activeToxins = if (activeToxinsJoined.isBlank()) emptyList() else activeToxinsJoined.split("||"),
            commonSymptoms = if (commonSymptomsJoined.isBlank()) emptyList() else commonSymptomsJoined.split("||"),
            firstAid = firstAid,
            safeHandlingTips = safeHandlingTips,
            commonUses = commonUses,
            imageResOrUri = imageResOrUri,
            scannedAt = scannedAt,
            isFavorite = isFavorite
        )
    }

    companion object {
        fun fromDomainModel(item: ScanResultItem): SavedScanEntity {
            return SavedScanEntity(
                id = item.id,
                name = item.name,
                scientificOrChemicalName = item.scientificOrChemicalName,
                categoryName = item.category.name,
                hazardLevelName = item.hazardLevel.name,
                toxicityScore = item.toxicityScore,
                isHarmful = item.isHarmful,
                summary = item.summary,
                humanHazard = item.humanHazard,
                petHazard = item.petHazard,
                activeToxinsJoined = item.activeToxins.joinToString("||"),
                commonSymptomsJoined = item.commonSymptoms.joinToString("||"),
                firstAid = item.firstAid,
                safeHandlingTips = item.safeHandlingTips,
                commonUses = item.commonUses,
                imageResOrUri = item.imageResOrUri,
                scannedAt = item.scannedAt,
                isFavorite = item.isFavorite
            )
        }
    }
}
