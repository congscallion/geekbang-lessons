package slydm.geektimes.training.test;


import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.AnnotationInfoList;
import io.github.classgraph.MethodParameterInfo;
import io.github.classgraph.MethodTypeSignature;
import java.lang.annotation.Repeatable;
import java.lang.reflect.Modifier;

/**
 * @author wangcymy@gmail.com(wangcong) 2021/3/30 10:36
 */
public class MyMethodInfo {

  /**
   * Defining class name.
   */
  private String declaringClassName;

  /**
   * Method name.
   */
  private String name;

  /**
   * Method modifiers.
   */
  private int modifiers;

  /**
   * Method annotations.
   */
  AnnotationInfoList annotationInfo;

  /**
   * The JVM-internal type descriptor (missing type parameters, but including types for synthetic and mandated
   * method parameters).
   */
  private String typeDescriptorStr;

  /**
   * The parsed type descriptor.
   */
  private transient MethodTypeSignature typeDescriptor;

  /**
   * The type signature (may have type parameter information included, if present and available). Method parameter
   * types are unaligned.
   */
  private String typeSignatureStr;

  /**
   * The parsed type signature (or null if none). Method parameter types are unaligned.
   */
  private transient MethodTypeSignature typeSignature;

  /**
   * Unaligned parameter names. These are only produced in JDK8+, and only if the commandline switch `-parameters`
   * is provided at compiletime.
   */
  private String[] parameterNames;

  /**
   * Unaligned parameter modifiers. These are only produced in JDK8+, and only if the commandline switch
   * `-parameters` is provided at compiletime.
   */
  private int[] parameterModifiers;

  /**
   * Unaligned parameter annotations.
   */
  AnnotationInfo[][] parameterAnnotationInfo;

  /**
   * Aligned method parameter info.
   */
  private transient MethodParameterInfo[] parameterInfo;

  /**
   * True if this method has a body.
   */
  private boolean hasBody;

  private boolean isDefault;
  private String modifiersStr;
  private Class<?>[] parameterClasses;

  // -------------------------------------------------------------------------------------------------------------

  /**
   * Default constructor for deserialization.
   */
  public MyMethodInfo() {
    super();
  }

  // -------------------------------------------------------------------------------------------------------------

  /**
   * Returns the name of the method. Note that constructors are named {@code "<init>"}, and private static class
   * initializer blocks are named {@code "<clinit>"}.
   *
   * @return The name of the method.
   */
  public String getName() {
    return name;
  }

  /**
   * Returns the modifier bits for the method.
   *
   * @return The modifier bits for the method.
   */
  public int getModifiers() {
    return modifiers;
  }

  /**
   * Get the method modifiers as a String, e.g. "public static final". For the modifier bits, call
   * {@link #getModifiers()}.
   *
   * @return The modifiers for the method, as a String.
   */
  public String getModifiersStr() {
    return modifiersStr;
  }


  /**
   * Returns the parsed type descriptor for the method, which will not include type parameters. If you need
   * generic type parameters, call {@link #getTypeSignature()} instead.
   *
   * @return The parsed type descriptor for the method.
   */
  public MethodTypeSignature getTypeDescriptor() {
    return typeDescriptor;
  }

  /**
   * Returns the type descriptor string for the method, which will not include type parameters. If you need
   * generic type parameters, call {@link #getTypeSignatureStr()} instead.
   *
   * @return The type descriptor string for the method.
   */
  public String getTypeDescriptorStr() {
    return typeDescriptorStr;
  }

  /**
   * Returns the parsed type signature for the method, possibly including type parameters. If this returns null,
   * indicating that no type signature information is available for this method, call {@link #getTypeDescriptor()}
   * instead.
   *
   * @return The parsed type signature for the method, or null if not available.
   * @throws IllegalArgumentException if the method type signature cannot be parsed (this should only be thrown in the case of
   * classfile corruption, or a compiler bug that causes an invalid type signature to be written to
   * the classfile).
   */
  public MethodTypeSignature getTypeSignature() {
    return typeSignature;
  }

  /**
   * Returns the type signature string for the method, possibly including type parameters. If this returns null,
   * indicating that no type signature information is available for this method, call
   * {@link #getTypeDescriptorStr()} instead.
   *
   * @return The type signature string for the method, or null if not available.
   */
  public String getTypeSignatureStr() {
    return typeSignatureStr;
  }

  /**
   * Returns the parsed type signature for the method, possibly including type parameters. If the type signature
   * string is null, indicating that no type signature information is available for this method, returns the
   * parsed type descriptor instead.
   *
   * @return The parsed type signature for the method, or if not available, the parsed type descriptor for the
   * method.
   */
  public MethodTypeSignature getTypeSignatureOrTypeDescriptor() {
    return getTypeDescriptor();
  }

  /**
   * Returns the type signature string for the method, possibly including type parameters. If the type signature
   * string is null, indicating that no type signature information is available for this method, returns the type
   * descriptor string instead.
   *
   * @return The type signature string for the method, or if not available, the type descriptor string for the
   * method.
   */
  public String getTypeSignatureOrTypeDescriptorStr() {
    if (typeSignatureStr != null) {
      return typeSignatureStr;
    } else {
      return typeDescriptorStr;
    }
  }

  // -------------------------------------------------------------------------------------------------------------

  /**
   * Returns true if this method is a constructor. Constructors have the method name {@code
   * "<init>"}. This returns false for private static class initializer blocks, which are named
   * {@code "<clinit>"}.
   *
   * @return True if this method is a constructor.
   */
  public boolean isConstructor() {
    return "<init>".equals(name);
  }

  /**
   * Returns true if this method is public.
   *
   * @return True if this method is public.
   */
  public boolean isPublic() {
    return Modifier.isPublic(modifiers);
  }

  /**
   * Returns true if this method is static.
   *
   * @return True if this method is static.
   */
  public boolean isStatic() {
    return Modifier.isStatic(modifiers);
  }

  /**
   * Returns true if this method is final.
   *
   * @return True if this method is final.
   */
  public boolean isFinal() {
    return Modifier.isFinal(modifiers);
  }

  /**
   * Returns true if this method is synchronized.
   *
   * @return True if this method is synchronized.
   */
  public boolean isSynchronized() {
    return Modifier.isSynchronized(modifiers);
  }

  /**
   * Returns true if this method is a bridge method.
   *
   * @return True if this is a bridge method.
   */
  public boolean isBridge() {
    return (modifiers & 0x0040) != 0;
  }

  /**
   * Returns true if this method is synthetic.
   *
   * @return True if this is synthetic.
   */
  public boolean isSynthetic() {
    return (modifiers & 0x1000) != 0;
  }

  /**
   * Returns true if this method is a varargs method.
   *
   * @return True if this is a varargs method.
   */
  public boolean isVarArgs() {
    return (modifiers & 0x0080) != 0;
  }

  /**
   * Returns true if this method is a native method.
   *
   * @return True if this method is native.
   */
  public boolean isNative() {
    return Modifier.isNative(modifiers);
  }

  /**
   * Returns true if this method has a body (i.e. has an implementation in the containing class).
   *
   * @return True if this method has a body.
   */
  public boolean hasBody() {
    return hasBody;
  }

  /**
   * Returns true if this is a default method (i.e. if this is a method in an interface and the method has a
   * body).
   *
   * @return True if this is a default method.
   */
  public boolean isDefault() {
    return isDefault;
  }

  // -------------------------------------------------------------------------------------------------------------

  /**
   * Get the available information on method parameters.
   *
   * @return The {@link MethodParameterInfo} objects for the method parameters, one per parameter.
   */
  public MethodParameterInfo[] getParameterInfo() {
    return parameterInfo;
  }

  // -------------------------------------------------------------------------------------------------------------

  /**
   * Get a list of annotations on this method, along with any annotation parameter values.
   *
   * @return a list of annotations on this method, along with any annotation parameter values, wrapped in
   * {@link AnnotationInfo} objects, or the empty list if none.
   */
  public AnnotationInfoList getAnnotationInfo() {
    return annotationInfo;
  }

  /**
   * Get a the named non-{@link Repeatable} annotation on this method, or null if the method does not have the
   * named annotation. (Use {@link #getAnnotationInfoRepeatable(String)} for {@link Repeatable} annotations.)
   *
   * @param annotationName The annotation name.
   * @return An {@link AnnotationInfo} object representing the named annotation on this method, or null if the
   * method does not have the named annotation.
   */
  public AnnotationInfo getAnnotationInfo(final String annotationName) {
    return getAnnotationInfo().get(annotationName);
  }

  /**
   * Get a the named {@link Repeatable} annotation on this method, or the empty list if the method does not have
   * the named annotation.
   *
   * @param annotationName The annotation name.
   * @return An {@link AnnotationInfoList} containing all instances of the named annotation on this method, or the
   * empty list if the method does not have the named annotation.
   */
  public AnnotationInfoList getAnnotationInfoRepeatable(final String annotationName) {
    return getAnnotationInfo().getRepeatable(annotationName);
  }

  /**
   * Check if this method has the named annotation.
   *
   * @param annotationName The name of an annotation.
   * @return true if this method has the named annotation.
   */
  public boolean hasAnnotation(final String annotationName) {
    return getAnnotationInfo().containsName(annotationName);
  }

  /**
   * Check if this method has a parameter with the named annotation.
   *
   * @param annotationName The name of a method parameter annotation.
   * @return true if this method has a parameter with the named annotation.
   */
  public boolean hasParameterAnnotation(final String annotationName) {
    for (final MethodParameterInfo methodParameterInfo : getParameterInfo()) {
      if (methodParameterInfo.hasAnnotation(annotationName)) {
        return true;
      }
    }
    return false;
  }

  // -------------------------------------------------------------------------------------------------------------

  /**
   * Load and return the classes of each of the method parameters.
   *
   * @return An array of the {@link Class} references for each method parameter.
   */
  private Class<?>[] loadParameterClasses() {
    return parameterClasses;
  }

  // -------------------------------------------------------------------------------------------------------------

  // -------------------------------------------------------------------------------------------------------------

  // -------------------------------------------------------------------------------------------------------------


  public String getDeclaringClassName() {
    return declaringClassName;
  }

  public void setDeclaringClassName(String declaringClassName) {
    this.declaringClassName = declaringClassName;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setModifiers(int modifiers) {
    this.modifiers = modifiers;
  }

  public void setAnnotationInfo(AnnotationInfoList annotationInfo) {
    this.annotationInfo = annotationInfo;
  }

  public void setTypeDescriptorStr(String typeDescriptorStr) {
    this.typeDescriptorStr = typeDescriptorStr;
  }

  public void setTypeDescriptor(MethodTypeSignature typeDescriptor) {
    this.typeDescriptor = typeDescriptor;
  }

  public void setTypeSignatureStr(String typeSignatureStr) {
    this.typeSignatureStr = typeSignatureStr;
  }

  public void setTypeSignature(MethodTypeSignature typeSignature) {
    this.typeSignature = typeSignature;
  }

  public String[] getParameterNames() {
    return parameterNames;
  }

  public void setParameterNames(String[] parameterNames) {
    this.parameterNames = parameterNames;
  }

  public int[] getParameterModifiers() {
    return parameterModifiers;
  }

  public void setParameterModifiers(int[] parameterModifiers) {
    this.parameterModifiers = parameterModifiers;
  }

  public AnnotationInfo[][] getParameterAnnotationInfo() {
    return parameterAnnotationInfo;
  }

  public void setParameterAnnotationInfo(AnnotationInfo[][] parameterAnnotationInfo) {
    this.parameterAnnotationInfo = parameterAnnotationInfo;
  }

  public void setParameterInfo(MethodParameterInfo[] parameterInfo) {
    this.parameterInfo = parameterInfo;
  }

  public boolean isHasBody() {
    return hasBody;
  }

  public void setHasBody(boolean hasBody) {
    this.hasBody = hasBody;
  }

  public void setDefault(boolean aDefault) {
    isDefault = aDefault;
  }

  public void setModifiersStr(String modifiersStr) {
    this.modifiersStr = modifiersStr;
  }

  public Class<?>[] getParameterClasses() {
    return parameterClasses;
  }

  public void setParameterClasses(Class<?>[] parameterClasses) {
    this.parameterClasses = parameterClasses;
  }
}
