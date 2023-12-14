package com.smileidentity.react.viewmanagers

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableArray
import com.facebook.react.module.annotations.ReactModule
import com.facebook.react.uimanager.SimpleViewManager
import com.facebook.react.uimanager.ThemedReactContext
import com.smileidentity.react.utils.getBoolOrDefault
import com.smileidentity.react.utils.getMapOrDefault
import com.smileidentity.react.utils.getStringOrDefault
import com.smileidentity.react.utils.toMap
import com.smileidentity.react.views.SmileIDSmartSelfieEnrollmentView


@ReactModule(name = SmileIDSmartSelfieEnrollmentViewManager.NAME)
class SmileIDSmartSelfieEnrollmentViewManager(private val reactApplicationContext: ReactApplicationContext) :
  SimpleViewManager<SmileIDSmartSelfieEnrollmentView>() {
  override fun getName(): String {
    return NAME
  }

  override fun getExportedCustomBubblingEventTypeConstants(): Map<String, Any> {
    return mapOf(
      "onSmileResult" to mapOf(
        "phasedRegistrationNames" to mapOf(
          "bubbled" to "onResult"
        )
      )
    )
  }

  override fun getCommandsMap(): Map<String, Int> {
    return mapOf("setParams" to COMMAND_SET_PARAMS)
  }

  override fun receiveCommand(
    view: SmileIDSmartSelfieEnrollmentView,
    commandId: String?,
    args: ReadableArray?
  ) {
    super.receiveCommand(view, commandId, args)
    when (commandId?.toInt()) {
      COMMAND_SET_PARAMS -> {
        // Extract params from args and apply to view
        val params = args?.getMap(0)
        params?.let {
          view.extraPartnerParams = params.getMapOrDefault("extraPartnerParams", null)?.toMap()
          view.userId = params.getStringOrDefault("userId",null)
          view.jobId = params.getStringOrDefault("jobId",null)
          view.allowAgentMode = params.getBoolOrDefault("allowAgentMode",false)
          view.showAttribution = params.getBoolOrDefault("showAttribution",true)
          view.showInstructions = params.getBoolOrDefault("showInstructions",true)
          view.renderContent()
        }
      }
    }
  }

  override fun createViewInstance(p0: ThemedReactContext): SmileIDSmartSelfieEnrollmentView {
    return SmileIDSmartSelfieEnrollmentView(reactApplicationContext)
  }

  companion object {
    const val NAME = "SmileIDSmartSelfieEnrollmentView"
    const val COMMAND_SET_PARAMS = 1
  }

}