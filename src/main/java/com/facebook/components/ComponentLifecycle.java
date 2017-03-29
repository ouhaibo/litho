/**
 * Copyright (c) 2014-present, Facebook, Inc.
 * All rights reserved.
 *
 * This source code is licensed under the BSD-style license found in the
 * LICENSE file in the root directory of this source tree. An additional grant
 * of patent rights can be found in the PATENTS file in the same directory.
 */

package com.facebook.litho;

import com.facebook.yoga.YogaAlign;

import com.facebook.yoga.YogaFlexDirection;

import java.util.concurrent.atomic.AtomicInteger;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.AttrRes;
import android.support.annotation.StyleRes;
import android.support.v4.util.Pools;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.widget.ExploreByTouchHelper;
import android.view.View;

import com.facebook.litho.annotations.OnCreateTreeProp;
import com.facebook.yoga.YogaBaselineFunction;
import com.facebook.yoga.YogaMeasureMode;
import com.facebook.yoga.YogaMeasureFunction;
import com.facebook.yoga.YogaNodeAPI;
import com.facebook.yoga.YogaMeasureOutput;

/**
 * {@link ComponentLifecycle} is a stateless singleton object that defines how {@link Component}
 * instances calculate their layout bounds and mount elements, among other things. This is the
 * base class from which all new component types inherit.
 *
 * In most cases, the {@link ComponentLifecycle} class will be automatically generated by the
 * annotation processor at build-time based on your spec class and you won't have to deal with
 * it directly when implementing new component types.
 */
public abstract class ComponentLifecycle implements EventDispatcher {
  private static final AtomicInteger sComponentId = new AtomicInteger();
  private static final int DEFAULT_MAX_PREALLOCATION = 15;

  private boolean mPreallocationDone;

  public enum MountType {
    NONE,
    DRAWABLE,
    VIEW,
  }

  public interface StateContainer {}

