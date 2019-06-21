This repo is created to reproduce bug in Kotlin Compiler 

* Details:
 
    Zulu OpenJdk contains backport of https://bugs.openjdk.java.net/browse/JDK-8029012. You can check it in openjdk_181\lib\tools.jar\com\sun\tools\javac\comp\Lower*.class (search by incrementParamTypeAnnoIndexes keyword).
    Kotlin compiler crashes on analyzing annotation (compiler/frontend.java/src/org/jetbrains/kotlin/load/java/structure/impl/classFiles/Annotations.kt:99), because compiler expects to get param_index = 0 on type annotation.  
    
* Kotlin: 1.3.40
* tested on jdk: Zulu OpenJdk 1.8.0_181 (zulu8.31.0.1-jdk8.0.181-win_x64.zip) and Zulu OpenJdk 1.8.0_212 (jdk8.0.212-win_i686.zip) 

* javap listing
     * openjdk:
        ````
            Signature: #38                          // (Ljava/lang/String;)V
            RuntimeVisibleTypeAnnotations:
              0: #40(): METHOD_FORMAL_PARAMETER, param_index=2
            RuntimeVisibleParameterAnnotations:
              parameter 0:
                0: #42()
        ````
    * oraclejdk:
        ````
             Signature: #38                          // (Ljava/lang/String;)V
              RuntimeVisibleTypeAnnotations:
                0: #40(): METHOD_FORMAL_PARAMETER, param_index=0
              RuntimeVisibleParameterAnnotations:
                parameter 0:
                  0: #42()
        ````

* Stacktrace:
    ````
    ERROR: Exception while analyzing expression at (9,27) in C:/Users/home/IdeaProjects/typeAnnotationBug/src/test/kotlin/ru/hiruma/HelloTest.kt
    org.jetbrains.kotlin.utils.KotlinExceptionWithAttachments: Exception while analyzing expression at (9,27) in C:/Users/home/IdeaProjects/typeAnnotationBug/src/test/kotlin/ru/hiruma/HelloTest.kt
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.logOrThrowException(ExpressionTypingVisitorDispatcher.java:234)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.lambda$getTypeInfo$0(ExpressionTypingVisitorDispatcher.java:212)
        at org.jetbrains.kotlin.util.PerformanceCounter.time(PerformanceCounter.kt:91)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.getTypeInfo(ExpressionTypingVisitorDispatcher.java:162)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.getTypeInfo(ExpressionTypingVisitorDispatcher.java:133)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingServices.getTypeInfo(ExpressionTypingServices.java:123)
        at org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver.getArgumentTypeInfo(ArgumentTypeResolver.java:228)
        at org.jetbrains.kotlin.resolve.calls.ArgumentTypeResolver.analyzeArgumentsAndRecordTypes(ArgumentTypeResolver.java:395)
        at org.jetbrains.kotlin.resolve.calls.CallResolver.doResolveCall(CallResolver.java:664)
        at org.jetbrains.kotlin.resolve.calls.CallResolver.doResolveCallOrGetCachedResults(CallResolver.java:592)
        at org.jetbrains.kotlin.resolve.calls.CallResolver.lambda$computeTasksAndResolveCall$0(CallResolver.java:208)
        at org.jetbrains.kotlin.util.PerformanceCounter.time(PerformanceCounter.kt:91)
        at org.jetbrains.kotlin.resolve.calls.CallResolver.computeTasksAndResolveCall(CallResolver.java:206)
        at org.jetbrains.kotlin.resolve.calls.CallResolver.computeTasksAndResolveCall(CallResolver.java:196)
        at org.jetbrains.kotlin.resolve.calls.CallResolver.resolveFunctionCall(CallResolver.java:322)
        at org.jetbrains.kotlin.resolve.calls.CallExpressionResolver.getResolvedCallForFunction(CallExpressionResolver.kt:95)
        at org.jetbrains.kotlin.resolve.calls.CallExpressionResolver.getCallExpressionTypeInfoWithoutFinalTypeCheck(CallExpressionResolver.kt:217)
        at org.jetbrains.kotlin.resolve.calls.CallExpressionResolver.getCallExpressionTypeInfo(CallExpressionResolver.kt:194)
        at org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor.visitCallExpression(BasicExpressionTypingVisitor.java:721)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.visitCallExpression(ExpressionTypingVisitorDispatcher.java:376)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher$ForBlock.visitCallExpression(ExpressionTypingVisitorDispatcher.java:58)
        at org.jetbrains.kotlin.psi.KtCallExpression.accept(KtCallExpression.java:35)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.lambda$getTypeInfo$0(ExpressionTypingVisitorDispatcher.java:173)
        at org.jetbrains.kotlin.util.PerformanceCounter.time(PerformanceCounter.kt:91)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.getTypeInfo(ExpressionTypingVisitorDispatcher.java:162)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.getTypeInfo(ExpressionTypingVisitorDispatcher.java:133)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorForStatements.visitExpression(ExpressionTypingVisitorForStatements.java:373)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorForStatements.visitExpression(ExpressionTypingVisitorForStatements.java:62)
        at org.jetbrains.kotlin.psi.KtVisitor.visitReferenceExpression(KtVisitor.java:198)
        at org.jetbrains.kotlin.psi.KtVisitor.visitCallExpression(KtVisitor.java:278)
        at org.jetbrains.kotlin.psi.KtCallExpression.accept(KtCallExpression.java:35)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.lambda$getTypeInfo$0(ExpressionTypingVisitorDispatcher.java:173)
        at org.jetbrains.kotlin.util.PerformanceCounter.time(PerformanceCounter.kt:91)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.getTypeInfo(ExpressionTypingVisitorDispatcher.java:162)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.getTypeInfo(ExpressionTypingVisitorDispatcher.java:146)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingServices.getTypeOfLastExpressionInBlock(ExpressionTypingServices.java:351)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingServices.getBlockReturnedTypeWithWritableScope(ExpressionTypingServices.java:277)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingServices.getBlockReturnedType(ExpressionTypingServices.java:199)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingServices.getBlockReturnedType(ExpressionTypingServices.java:176)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorForStatements.visitBlockExpression(ExpressionTypingVisitorForStatements.java:416)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorForStatements.visitBlockExpression(ExpressionTypingVisitorForStatements.java:62)
        at org.jetbrains.kotlin.psi.KtBlockExpression.accept(KtBlockExpression.java:78)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.lambda$getTypeInfo$0(ExpressionTypingVisitorDispatcher.java:173)
        at org.jetbrains.kotlin.util.PerformanceCounter.time(PerformanceCounter.kt:91)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.getTypeInfo(ExpressionTypingVisitorDispatcher.java:162)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.getTypeInfo(ExpressionTypingVisitorDispatcher.java:146)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingServices.checkFunctionReturnType(ExpressionTypingServices.java:171)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingServices.checkFunctionReturnType(ExpressionTypingServices.java:154)
        at org.jetbrains.kotlin.resolve.BodyResolver.resolveFunctionBody(BodyResolver.java:974)
        at org.jetbrains.kotlin.resolve.BodyResolver.resolveFunctionBody(BodyResolver.java:923)
        at org.jetbrains.kotlin.resolve.BodyResolver.resolveFunctionBodies(BodyResolver.java:909)
        at org.jetbrains.kotlin.resolve.BodyResolver.resolveBehaviorDeclarationBodies(BodyResolver.java:124)
        at org.jetbrains.kotlin.resolve.BodyResolver.resolveBodies(BodyResolver.java:243)
        at org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer.analyzeDeclarations(LazyTopDownAnalyzer.kt:225)
        at org.jetbrains.kotlin.resolve.LazyTopDownAnalyzer.analyzeDeclarations$default(LazyTopDownAnalyzer.kt:60)
        at org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration(TopDownAnalyzerFacadeForJVM.kt:110)
        at org.jetbrains.kotlin.cli.jvm.compiler.TopDownAnalyzerFacadeForJVM.analyzeFilesWithJavaIntegration$default(TopDownAnalyzerFacadeForJVM.kt:80)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler$analyze$1.invoke(KotlinToJVMBytecodeCompiler.kt:395)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler$analyze$1.invoke(KotlinToJVMBytecodeCompiler.kt:65)
        at org.jetbrains.kotlin.cli.common.messages.AnalyzerWithCompilerReport.analyzeAndReport(AnalyzerWithCompilerReport.kt:107)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.analyze(KotlinToJVMBytecodeCompiler.kt:386)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinToJVMBytecodeCompiler.compileModules$cli(KotlinToJVMBytecodeCompiler.kt:118)
        at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:166)
        at org.jetbrains.kotlin.cli.jvm.K2JVMCompiler.doExecute(K2JVMCompiler.kt:56)
        at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:84)
        at org.jetbrains.kotlin.cli.common.CLICompiler.execImpl(CLICompiler.kt:42)
        at org.jetbrains.kotlin.cli.common.CLITool.exec(CLITool.kt:104)
        at org.jetbrains.kotlin.maven.KotlinCompileMojoBase.execCompiler(KotlinCompileMojoBase.java:237)
        at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execCompiler(K2JVMCompileMojo.java:237)
        at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execCompiler(K2JVMCompileMojo.java:55)
        at org.jetbrains.kotlin.maven.KotlinCompileMojoBase.execute(KotlinCompileMojoBase.java:220)
        at org.jetbrains.kotlin.maven.K2JVMCompileMojo.execute(K2JVMCompileMojo.java:222)
        at org.jetbrains.kotlin.maven.KotlinTestCompileMojo.execute(KotlinTestCompileMojo.java:84)
        at org.apache.maven.plugin.DefaultBuildPluginManager.executeMojo(DefaultBuildPluginManager.java:134)
        at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:207)
        at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:153)
        at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:145)
        at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:116)
        at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:80)
        at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build(SingleThreadedBuilder.java:51)
        at org.apache.maven.lifecycle.internal.LifecycleStarter.execute(LifecycleStarter.java:128)
        at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:307)
        at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:193)
        at org.apache.maven.DefaultMaven.execute(DefaultMaven.java:106)
        at org.apache.maven.cli.MavenCli.execute(MavenCli.java:863)
        at org.apache.maven.cli.MavenCli.doMain(MavenCli.java:288)
        at org.apache.maven.cli.MavenCli.main(MavenCli.java:199)
        at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
        at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
        at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
        at java.lang.reflect.Method.invoke(Method.java:498)
        at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced(Launcher.java:289)
        at org.codehaus.plexus.classworlds.launcher.Launcher.launch(Launcher.java:229)
        at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode(Launcher.java:415)
        at org.codehaus.plexus.classworlds.launcher.Launcher.main(Launcher.java:356)
        at org.codehaus.classworlds.Launcher.main(Launcher.java:47)
    Caused by: java.lang.IndexOutOfBoundsException: Index: 2, Size: 1
        at java.util.ArrayList.rangeCheck(ArrayList.java:657)
        at java.util.ArrayList.get(ArrayList.java:433)
        at org.jetbrains.kotlin.load.java.structure.impl.classFiles.AnnotationsAndParameterCollectorMethodVisitor.visitTypeAnnotation(Annotations.kt:99)
        at org.jetbrains.org.objectweb.asm.ClassReader.readMethod(ClassReader.java:1214)
        at org.jetbrains.org.objectweb.asm.ClassReader.accept(ClassReader.java:680)
        at org.jetbrains.org.objectweb.asm.ClassReader.accept(ClassReader.java:392)
        at org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaClass.<init>(BinaryJavaClass.kt:76)
        at org.jetbrains.kotlin.load.java.structure.impl.classFiles.BinaryJavaClass.findInnerClass(BinaryJavaClass.kt:210)
        at org.jetbrains.kotlin.cli.jvm.compiler.KotlinCliJavaFileManagerImpl.findClass(KotlinCliJavaFileManagerImpl.kt:104)
        at org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade$CliFinder.findClass(KotlinJavaPsiFacade.java:361)
        at org.jetbrains.kotlin.resolve.jvm.KotlinJavaPsiFacade.findClass(KotlinJavaPsiFacade.java:123)
        at org.jetbrains.kotlin.load.java.JavaClassFinderImpl.findClass(JavaClassFinderImpl.kt:36)
        at org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassMemberScope$nestedClasses$1.invoke(LazyJavaClassMemberScope.kt:706)
        at org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassMemberScope$nestedClasses$1.invoke(LazyJavaClassMemberScope.kt:66)
        at org.jetbrains.kotlin.storage.LockBasedStorageManager$MapBasedMemoizedFunction.invoke(LockBasedStorageManager.java:440)
        at org.jetbrains.kotlin.load.java.lazy.descriptors.LazyJavaClassMemberScope.getContributedClassifier(LazyJavaClassMemberScope.kt:723)
        at org.jetbrains.kotlin.resolve.scopes.InnerClassesScopeWrapper.getContributedClassifier(InnerClassesScopeWrapper.kt:29)
        at org.jetbrains.kotlin.resolve.QualifiedExpressionResolver.getContributedClassifier(QualifiedExpressionResolver.kt:520)
        at org.jetbrains.kotlin.resolve.QualifiedExpressionResolver.resolveToPackageOrClassPrefix(QualifiedExpressionResolver.kt:488)
        at org.jetbrains.kotlin.resolve.QualifiedExpressionResolver.resolveQualifierInExpressionAndUnroll(QualifiedExpressionResolver.kt:593)
        at org.jetbrains.kotlin.resolve.calls.CallExpressionResolver.elementChain(CallExpressionResolver.kt:303)
        at org.jetbrains.kotlin.resolve.calls.CallExpressionResolver.getQualifiedExpressionTypeInfo(CallExpressionResolver.kt:423)
        at org.jetbrains.kotlin.types.expressions.BasicExpressionTypingVisitor.visitQualifiedExpression(BasicExpressionTypingVisitor.java:715)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.visitQualifiedExpression(ExpressionTypingVisitorDispatcher.java:371)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher$ForDeclarations.visitQualifiedExpression(ExpressionTypingVisitorDispatcher.java:45)
        at org.jetbrains.kotlin.psi.KtVisitor.visitDotQualifiedExpression(KtVisitor.java:302)
        at org.jetbrains.kotlin.psi.KtDotQualifiedExpression.accept(KtDotQualifiedExpression.kt:31)
        at org.jetbrains.kotlin.types.expressions.ExpressionTypingVisitorDispatcher.lambda$getTypeInfo$0(ExpressionTypingVisitorDispatcher.java:173)
        ... 94 more
        
    ````