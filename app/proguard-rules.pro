# ---- Streakly release rules ----
-keepattributes Signature, InnerClasses, EnclosingMethod, RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations, *Annotation*
-keepattributes SourceFile, LineNumberTable

# Gson / Retrofit models (serialised by reflection) and Room entities
-keep class com.streakly.app.data.local.entity.** { *; }
-keep class com.streakly.app.data.remote.dto.** { *; }
-keepclassmembers class * { @com.google.gson.annotations.SerializedName <fields>; }

# Retrofit with Kotlin suspend functions
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation
-keep,allowobfuscation,allowshrinking interface retrofit2.Call
-keep,allowobfuscation,allowshrinking class retrofit2.Response
-keep interface com.streakly.app.data.remote.ApiService { *; }
-dontwarn org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
-dontwarn javax.annotation.**
-dontwarn kotlin.Unit

# Custom views inflated from XML
-keep class com.streakly.app.ui.widget.** { *; }
