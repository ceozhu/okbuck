package com.uber.okbuck.composer

import com.uber.okbuck.core.model.AndroidLibTarget
import com.uber.okbuck.core.model.AndroidTarget
import com.uber.okbuck.core.model.Target
import com.uber.okbuck.core.util.RobolectricUtil
import com.uber.okbuck.generator.RetroLambdaGenerator
import com.uber.okbuck.rule.AndroidTestRule

final class AndroidTestRuleComposer extends AndroidBuckRuleComposer {

    private AndroidTestRuleComposer() {
        // no instance
    }

    static AndroidTestRule compose(
            AndroidLibTarget target,
            List<String> deps,
            List<String> aptDeps,
            List<String> aidlRuleNames,
            String appClass) {

        List<String> testDeps = new ArrayList<>(deps);
        List<String> testAptDeps = new ArrayList<>(aptDeps);
        List<String> testAidlRuleNames = new ArrayList<>(aidlRuleNames);
        Set<String> providedDeps = []

        testDeps.add(":${src(target)}")
        testDeps.addAll(external(target.test.externalDeps))
        testDeps.addAll(targets(target.test.targetDeps))

        providedDeps.addAll(external(target.apt.externalDeps))
        providedDeps.addAll(targets(target.apt.targetDeps))
        providedDeps.removeAll(testDeps)

        target.test.targetDeps.each { Target targetDep ->
            if (targetDep instanceof AndroidTarget) {
                targetDep.resources.each { AndroidTarget.ResBundle bundle ->
                    testDeps.add(res(targetDep as AndroidTarget, bundle))
                }
            }
        }

        List<String> postprocessClassesCommands = []
        if (target.retrolambda) {
            postprocessClassesCommands.add(RetroLambdaGenerator.generate(target))
        }

        return new AndroidTestRule(
                test(target),
                ["PUBLIC"],
                testDeps,
                [] as Set,
                target.test.sources,
                target.manifest,
                target.annotationProcessors as List,
                testAptDeps,
                providedDeps,
                testAidlRuleNames,
                appClass,
                target.sourceCompatibility,
                target.targetCompatibility,
                postprocessClassesCommands,
                target.test.jvmArgs,
                target.test.resourcesDir,
                RobolectricUtil.ROBOLECTRIC_CACHE)
    }
}
