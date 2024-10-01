# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# kakao-sdk
-keep class com.kakao.sdk.**.model.* { <fields>; }
-keep class * extends com.google.gson.TypeAdapter
-keep interface com.kakao.sdk.**.*Api

# Proto DataStore 관련된 클래스는 난독화하지 않도록 설정
-keepclassmembers class * extends com.google.protobuf.GeneratedMessageLite {
    <fields>;
    <methods>;
}

-keep class com.google.protobuf.** { *; }
-keep class kotlinx.coroutines.flow.FlowKt__BuildersKt {
    public <methods>;
}

# OkHttp
-keepattributes Signature
-keepattributes *Annotation*
-keep class com.squareup.okhttp.** { *; }
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**
-dontwarn okio.**
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }
# OkHttp 관련 설정 (로깅 인터셉터 등 사용 시)
-keep class okhttp3.** { *; }
-keep interface okhttp3.** { *; }

# Hilt 관련 설정
-keep class dagger.hilt.** { *; }
-keep interface dagger.hilt.** { *; }
-keep class javax.inject.** { *; }

# Dagger 관련 설정 (Hilt는 Dagger 기반)
-keep class dagger.** { *; }
-keep interface dagger.** { *; }

# Room 관련 설정
-keep class androidx.room.** { *; }
-keep interface androidx.room.** { *; }


-keep class com.google.firebase.** { *; }
-keep interface com.google.firebase.** { *; }

# Kotlin 관련 설정
-keepclassmembers class * {
    @kotlin.Metadata <fields>;
}

-keep class com.startup.histour.data.** { *; }
# 데이터 클래스의 getter와 setter 유지
-keepclassmembers class * {
    *** get*();
    void set*(***);
}

-keepattributes Signature, InnerClasses, EnclosingMethod

# Retrofit does reflection on method and parameter annotations.
-keepattributes RuntimeVisibleAnnotations, RuntimeVisibleParameterAnnotations

# Retain service method parameters when optimizing.
-keepclassmembers,allowshrinking,allowobfuscation interface * {
    @retrofit2.http.* <methods>;
}
-keepattributes Signature
-keepattributes *Annotation*
# With R8 full mode generic signatures are stripped for classes that are not
# kept. Suspend functions are wrapped in continuations where the type argument
# is used.
-keep,allowobfuscation,allowshrinking class kotlin.coroutines.Continuation

# R8 full mode strips generic signatures from return types if not kept.
-if interface * { @retrofit2.http.* public *** *(...); }
-keep,allowoptimization,allowshrinking,allowobfuscation class <3>

# With R8 full mode generic signatures are stripped for classes that are not kept.
-keep,allowobfuscation,allowshrinking class retrofit2.Response

