package com.bc.wps.utilities;

import org.junit.runner.*;
import org.junit.runner.notification.*;
import org.junit.runners.*;
import org.junit.runners.model.*;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Run Tests with this runner to prevent unintended execution of time-consuming tests.
 * E.G. in case of some time consuming tests which not always should be run.
 * Set the VM Option defined by {@link RunIfProfileIsActivated} to true to explicitly run this time consuming test.
 */
public class ProfileTestRunner extends BlockJUnit4ClassRunner {

    private final Class<?> testJavaClass;
    private boolean ignoreAllTestsInClass;

    public ProfileTestRunner(Class<?> klass) throws InitializationError {
        super(klass);

        this.testJavaClass = klass;
        ignoreAllTestsInClass = false;
        final RunIfProfileIsActivated profileForTestClass = testJavaClass.getAnnotation(RunIfProfileIsActivated.class);
        if (profileForTestClass != null) {
            final String vmOption = profileForTestClass.value();
            ignoreAllTestsInClass = !Boolean.getBoolean(vmOption);
            if (ignoreAllTestsInClass) {
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                System.out.println("!!  All tests in class \"" + testJavaClass.getName() + "\" ignored");
                System.out.println("!!  To run this ignored tests set VM option:");
                System.out.println("!!   -D" + vmOption + "=true");
                System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            }
        }
    }

    @Override
    protected void runChild(FrameworkMethod method, RunNotifier notifier) {
        boolean ignoreThisTest = false;
        final RunIfProfileIsActivated profileForMethod = method.getAnnotation(RunIfProfileIsActivated.class);
        String vmOption = "";
        if (profileForMethod != null) {
            vmOption = profileForMethod.value();
            ignoreThisTest = !Boolean.getBoolean(vmOption);
        }
        final Description description = describeChild(method);
        if (ignoreThisTest) {
            final String methName = method.getName();
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            System.out.println("!!  Disabled Test \"" + methName + "\"");
            System.out.println("!!  of test class \"" + getTestClass().getName());
            System.out.println("!!  To run this test set VM option:");
            System.out.println("!!   -D" + vmOption + "=true.");
            System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            notifier.fireTestIgnored(description);
        } else if (ignoreAllTestsInClass) {
            notifier.fireTestIgnored(description);
        } else {
            super.runChild(method, notifier);
        }
    }

    /**
     * Use this annotation to define the vm option key whicht must be set to true
     * to execute annotated test methods or classes.
     * This annotation can be used to annotate classes or methods
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.TYPE})
    public @interface RunIfProfileIsActivated {

        String value();
    }
}

