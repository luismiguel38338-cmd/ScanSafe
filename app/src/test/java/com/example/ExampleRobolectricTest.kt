package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.ScanKnowledgeBase
import com.example.model.HazardLevel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

  @Test
  fun `read string from context`() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    val appName = context.getString(R.string.app_name)
    assertEquals("ScanSafe", appName)
  }

  @Test
  fun `verify knowledge base contains toxic items`() {
    val adelfa = ScanKnowledgeBase.search("adelfa").firstOrNull()
    assertNotNull(adelfa)
    assertEquals(HazardLevel.CRITICAL, adelfa?.hazardLevel)
    assertTrue(adelfa?.isHarmful == true)
  }

  @Test
  fun `verify bleach formula detection`() {
    val result = ScanKnowledgeBase.analyzeIngredientsText("Lejía con cloro")
    assertTrue(result.isHarmful)
    assertEquals(HazardLevel.CRITICAL, result.hazardLevel)
  }
}
