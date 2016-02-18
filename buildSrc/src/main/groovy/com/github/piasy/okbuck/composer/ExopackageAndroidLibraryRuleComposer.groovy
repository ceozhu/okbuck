/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Piasy
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.github.piasy.okbuck.composer

import com.github.piasy.okbuck.dependency.Dependency
import com.github.piasy.okbuck.rules.ExopackageAndroidLibraryRule

public final class ExopackageAndroidLibraryRuleComposer {

    private ExopackageAndroidLibraryRuleComposer() {
        // no instance
    }

    public static ExopackageAndroidLibraryRule compose(
            List<Dependency> exopackageRuleDependencies, String flavor, String variant
    ) {
        List<String> deps = new ArrayList<>()
        for (Dependency dependency : exopackageRuleDependencies) {
            deps.add(dependency.srcCanonicalName)
        }
        deps.add(":build_config_${flavor}_${variant}")
        return new ExopackageAndroidLibraryRule("app_lib_${flavor}_${variant}",
                Arrays.asList("PUBLIC"), deps)
    }

    public static ExopackageAndroidLibraryRule composeWithoutFlavor(
            List<Dependency> exopackageRuleDependencies, String variant
    ) {
        List<String> deps = new ArrayList<>()
        for (Dependency dependency : exopackageRuleDependencies) {
            deps.add(dependency.srcCanonicalName)
        }
        deps.add(":build_config_${variant}")
        return new ExopackageAndroidLibraryRule("app_lib_${variant}",
                Arrays.asList("PUBLIC"), deps)
    }
}