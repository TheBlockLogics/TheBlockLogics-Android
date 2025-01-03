package com.raredev.theblocklogics.activities;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.color.MaterialColors;
import com.raredev.theblocklogics.R;
import com.raredev.theblocklogics.dialogs.ProgressDialogBuilder;
import java.util.ArrayList;

public abstract class BaseActivity extends AppCompatActivity {

  private ProgressDialogBuilder progressDlgBuilder;
  private AlertDialog progressDlg;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().setStatusBarColor(getStatusBarColor());
    getWindow().setNavigationBarColor(getNavigationBarColor());
    setContentView(bindLayout());

    progressDlgBuilder = new ProgressDialogBuilder(this);
    progressDlgBuilder.setMessage(R.string.please_wait);
    progressDlg = progressDlgBuilder.create();
  }

  public void showNonCancelableProgress() {
    if (!progressDlg.isShowing()) {
      progressDlg.setCancelable(false);
      progressDlg.show();
    }
  }

  public void dismissProgress() {
    if (progressDlg.isShowing()) {
      progressDlg.dismiss();
    }
  }

  protected abstract View bindLayout();

  public int getStatusBarColor() {
    return MaterialColors.getColor(this, com.google.android.material.R.attr.colorOnSurfaceInverse, 0);
  }

  public int getNavigationBarColor() {
    return MaterialColors.getColor(this, com.google.android.material.R.attr.colorOnSurfaceInverse, 0);
  }

  public AlertDialog getProgressDialog() {
    return progressDlg;
  }
  
  /**
   * Returns a parcelable based in Android SDK to avoid crashes and deprecated uses.
   *
   * @param key: The key of parcelable
   * @param clazz: The class of parcelable
   */
  protected <T extends Parcelable> T getParcelable(final String key, final Class<T> clazz) {
    var extras = getIntent().getExtras();
    if (extras == null) return null;
    if (!extras.containsKey(key)) return null;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      return extras.getParcelable(key, clazz);
    } else {
      return extras.getParcelable(key);
    }
  }
  
  /**
   * Returns a parcelable ArrayList based in Android SDK to avoid crashes and deprecated uses.
   *
   * @param key: The key of parcelable
   * @param clazz: The class of parcelable
   */
  protected <T extends Parcelable> ArrayList<T> getParcelableArrayList(final String key, final Class<T> clazz) {
    var extras = getIntent().getExtras();
    if (extras == null) return null;
    if (!extras.containsKey(key)) return null;

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
      return extras.getParcelableArrayList(key, clazz);
    } else {
      return extras.getParcelableArrayList(key);
    }
  }
}
