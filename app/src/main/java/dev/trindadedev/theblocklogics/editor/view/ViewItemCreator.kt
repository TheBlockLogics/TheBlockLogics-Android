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
import dev.trindadedev.theblocklogics.editor.view.type.ViewType

/** Main View Creator of ViewEditor */
object ViewItemCreator {

  fun createItemView(context: Context, data: ViewData): ViewItem {
    return handleLayoutViews(context, data);
  }

  private fun handleLayoutViews(context: Context, data: ViewData): ViewItem {
    return when (data.type) {
      ViewType.TYPE_LINEAR_LAYOUT,
      ViewType.TYPE_RADIO_GROUP,
      ViewType.TYPE_RELATIVE_LAYOUT -> LinearLayoutItem(context, data)
      ViewType.TYPE_FRAME_LAYOUT -> FrameLayoutItem(context, data)
      ViewType.TYPE_VSCROLL_VIEW -> ScrollVItem(context, data)
      ViewType.TYPE_HSCROLL_VIEW -> ScrollHItem(context, data)
      else -> handleNormalViews(context, data)
    }
  }

  private fun handleNormalViews(context: Context, data: ViewData): ViewItem {
    return when (data.type) {
      ViewType.TYPE_IMAGE_VIEW,
      ViewType.TYPE_CHECK_BOX,
      ViewType.TYPE_RADIO_BUTTON,
      ViewType.TYPE_SWITCH,
      ViewType.TYPE_SEEK_BAR,
      ViewType.TYPE_PROGRESS_BAR -> LinearLayoutItem(context, data)
      else -> handleTextViews(context, data)
    }
  }

  private fun handleTextViews(context: Context, data: ViewData): ViewItem {
    return when (data.type) {
      ViewType.TYPE_TEXT_VIEW -> TextViewItem(context, data)
      ViewType.TYPE_EDIT_TEXT -> EditTextItem(context, data)
      ViewType.TYPE_BUTTON -> ButtonItem(context, data)
      else -> getDefaultView(context, data)
    }
  }

  private fun getDefaultView(context: Context, data: ViewData): ViewItem {
    return LinearLayoutItem(context, data)
  }
}