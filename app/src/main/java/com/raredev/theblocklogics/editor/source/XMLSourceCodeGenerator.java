package com.raredev.theblocklogics.editor.source;

import android.text.TextUtils;
import android.view.Gravity;
import com.raredev.theblocklogics.editor.view.data.ViewData;
import com.raredev.theblocklogics.editor.view.utils.Attributes;
import com.raredev.theblocklogics.editor.view.utils.ViewEditorUtils;
import com.raredev.theblocklogics.utils.Constants;
import dev.trindadedev.theblocklogics.editor.view.type.ViewType;
import java.util.List;

public class XMLSourceCodeGenerator {

  public static final String TAB = "    ";
  private StringBuilder code;

  public String generate(List<ViewData> viewsData) {
    this.code = new StringBuilder();
    addXmlDeclaration();
    addRootHeader(!viewsData.isEmpty());

    if (viewsData.isEmpty()) {
      return code.toString().trim();
    }

    for (ViewData viewData : viewsData) {
      if (viewData.parentId.equals(Constants.ROOT_ID)) {
        addView(viewData, viewsData, 1);
      }
    }
    addRootTail();
    return code.toString().trim();
  }

  private void addView(ViewData viewData, List<ViewData> viewsData, int depth) {
    var tag = ViewEditorUtils.getTagForType(viewData.type);
    addTagHeadOpen(depth, tag);

    int attrDepth = depth + 1;

    addAttribute(attrDepth, Attributes.View.Id, "@+id/" + viewData.id);
    addAttribute(
        attrDepth, Attributes.View.Width, ViewEditorUtils.layoutParamsToString(viewData.width));
    addAttribute(
        attrDepth, Attributes.View.Height, ViewEditorUtils.layoutParamsToString(viewData.height));

    if (viewData.layout.layoutGravity != Gravity.NO_GRAVITY) {
      addAttribute(
          attrDepth,
          Attributes.View.LayoutGravity,
          ViewEditorUtils.gravityToString(viewData.layout.layoutGravity));
    }

    if (viewData.layout.gravity != Gravity.NO_GRAVITY) {
      addAttribute(
          attrDepth,
          Attributes.View.Gravity,
          ViewEditorUtils.gravityToString(viewData.layout.gravity));
    }

    addPaddingAttributes(attrDepth, viewData);

    switch (viewData.type) {
      case ViewType.TYPE_LINEAR_LAYOUT:
        addLinearLayoutAttributes(attrDepth, viewData);
        break;
      case ViewType.TYPE_BUTTON:
      case ViewType.TYPE_EDIT_TEXT:
      case ViewType.TYPE_TEXT_VIEW:
        addTextViewAttributes(attrDepth, viewData);
    }
    code.deleteCharAt(code.length() - 1);
    if (viewData.type == ViewType.TYPE_LINEAR_LAYOUT
        || viewData.type == ViewType.TYPE_VSCROLL_VIEW
        || viewData.type == ViewType.TYPE_HSCROLL_VIEW
        || viewData.type == ViewType.TYPE_FRAME_LAYOUT) {
      addTagHeadCloser(true);
      for (ViewData child : viewsData) {
        if (child.parentId.equals(viewData.id)) {
          addView(child, viewsData, depth + 1);
        }
      }
      addTagTail(depth, tag);
    } else {
      addTagHeadCloser(false);
    }
  }

  private void addXmlDeclaration() {
    code.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
  }

  private void addRootHeader(boolean hasChild) {
    addTagHeadOpen(0, "LinearLayout");
    addAttribute(1, "xmlns:android", "http://schemas.android.com/apk/res/android");
    addAttribute(1, Attributes.View.Id, "@+id/root");
    addAttribute(1, Attributes.View.Width, Constants.MATCH_PARENT);
    addAttribute(1, Attributes.View.Height, Constants.MATCH_PARENT);
    addAttribute(1, Attributes.LinearLayout.Orientation, Constants.VERTICAL);
    code.deleteCharAt(code.length() - 1);
    addTagHeadCloser(hasChild);
  }

  private void addRootTail() {
    addTagTail(0, "LinearLayout");
  }

  private void addLinearLayoutAttributes(int depth, ViewData viewData) {
    addAttribute(
        depth,
        Attributes.LinearLayout.Orientation,
        ViewEditorUtils.orientationToString(viewData.layout.orientation));
  }

  private void addTextViewAttributes(int depth, ViewData viewData) {
    addAttribute(depth, Attributes.TextView.TextSize, "" + viewData.text.textSize + "sp");

    var text = viewData.text.text;
    if (!TextUtils.isEmpty(text)) {
      addAttribute(depth, Attributes.TextView.Text, text);
    }

    var hint = viewData.text.hint;
    if (!TextUtils.isEmpty(hint)) {
      addAttribute(depth, Attributes.TextView.Hint, hint);
    }
  }

  private void addPaddingAttributes(int depth, ViewData viewData) {
    int left = viewData.paddingLeft;
    int top = viewData.paddingTop;
    int right = viewData.paddingRight;
    int bottom = viewData.paddingBottom;

    if (left == right && top == bottom && left > 0) {
      addAttribute(depth, Attributes.View.Padding, ViewEditorUtils.layoutParamsToString(left));
      return;
    }

    if (left > 0) {
      addAttribute(depth, Attributes.View.PaddingLeft, ViewEditorUtils.layoutParamsToString(left));
    }

    if (top > 0) {
      addAttribute(depth, Attributes.View.PaddingTop, ViewEditorUtils.layoutParamsToString(top));
    }

    if (right > 0) {
      addAttribute(
          depth, Attributes.View.PaddingRight, ViewEditorUtils.layoutParamsToString(right));
    }

    if (bottom > 0) {
      addAttribute(
          depth, Attributes.View.PaddingBottom, ViewEditorUtils.layoutParamsToString(bottom));
    }
  }

  private void addTagHeadOpen(int depth, String tag) {
    code.append(getIndent(depth) + "<" + tag + "\n");
  }

  private void addTagHeadCloser(boolean hasChild) {
    if (hasChild) {
      code.append(">\n\n");
      return;
    }
    code.append(" />\n\n");
  }

  private void addTagTail(int depth, String tag) {
    code.append(getIndent(depth) + "</" + tag + ">\n\n");
  }

  private void addAttribute(int depth, String attribute, String value) {
    code.append(getIndent(depth) + attribute + "=\"" + value + "\"\n");
  }

  private String getIndent(int depth) {
    return TAB.repeat(depth);
  }
}
