package dev.trindadedev.theblocklogics.editor.view

import android.content.Context
import com.raredev.theblocklogics.editor.view.data.ViewData
import com.raredev.theblocklogics.editor.view.views.ViewItem
import com.raredev.theblocklogics.editor.view.views.layout.LinearLayoutItem
import com.raredev.theblocklogics.editor.view.views.layout.FrameLayoutItem
import com.raredev.theblocklogics.editor.view.views.layout.ScrollHItem
import com.raredev.theblocklogics.editor.view.views.layout.ScrollVItem
import com.raredev.theblocklogics.editor.view.views.text.ButtonItem
import com.raredev.theblocklogics.editor.view.views.text.EditTextItem
import com.raredev.theblocklogics.editor.view.views.text.TextViewItem

/** Main View Creator of ViewEditor */
object ViewItemCreator {

  fun createItemView(context: Context, data: ViewData): ViewItem {
    return handleLayoutViews();
  }

  private fun handleLayoutViews(context: Context, data: ViewData): ViewItem {
    return when (data.type) {
      TYPE_LINEAR_LAYOUT,
      TYPE_RADIO_GROUP,
      TYPE_RELATIVE_LAYOUT -> LinearLayoutItem(context, data)
      TYPE_FRAME_LAYOUT -> FrameLayoutItem(context, data)
      TYPE_VSCROLL_VIEW -> ScrollVItem(context, data)
      TYPE_HSCROLL_VIEW -> ScrollHItem(context, data)
      else -> handleNormalViews(context, data)
    }
  }

  private fun handleNormalViews(context: Context, data: ViewData): ViewItem {
    return when (data.type) {
      TYPE_IMAGE_VIEW,
      TYPE_CHECK_BOX,
      TYPE_RADIO_BUTTON,
      TYPE_SWITCH,
      TYPE_SEEK_BAR,
      TYPE_PROGRESS_BAR -> LinearLayoutItem(context, data)
      else -> handleTextViews(context, data)
    }
  }

  private fun handleTextViews(context: Context, data: ViewData): ViewItem {
    return when (data.type) {
      TYPE_TEXT_VIEW -> TextViewItem(context, data)
      TYPE_EDIT_TEXT -> EditTextItem(context, data)
      TYPE_BUTTON -> ButttonItem(context, data)
      else -> getDefaultView(context, data)
    }
  }

  private fun getDefaultView(context: Context, data: ViewData): ViewItem {
    return LinearLayoutItem(context, data)
  }
}