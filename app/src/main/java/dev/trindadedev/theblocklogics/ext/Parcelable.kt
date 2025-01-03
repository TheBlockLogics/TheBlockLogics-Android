package dev.trindadedev.theblocklogics.ext

import android.os.Build
import android.os.Bundle
import android.os.Parcelable

/** Returns a safe parcelable with SDK verification. avoid crashes in older devices. */
inline fun <reified T : Parcelable> Bundle.getSafeParcelable(key: String): T? {
  return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    getParcelable(key, T::class.java)
  } else {
    getParcelable(key)
  }
}
